package at.syntaxerror.ini;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class IniUtil {
	
	static {
		System.loadLibrary("ini");
	}
	
	/**
	 * Set the value to be to be assigned to implicit keys<br>
	 * <br>
	 * <b>Warning:</b> 	This function changes the value of one or more global variables. In
						order to be thread-safe this function should be used only once at
						beginning of execution, or otherwise a mutex logic must be
						introduced.
	 * 
	 * @param implicit_value	The string to be used as implicit value
								(usually <code>YES</code>, <code>TRUE</code>, or <code>ON</code>, or
								any other string; it can be <code>null</code>)
	 * @param implicit_v_len	The length of @p implicit_value (use 
	   							<code>0</code> for both an empty string and <code>null</code>)
	   							
	   @deprecated work in progress
	 */
	@Deprecated
	public static void setImplicitValue(String implicit_value) {
		set_implicit_value0(implicit_value,implicit_value==null?0:implicit_value.length());
	}
	
	private static native void set_implicit_value0(String implicit_value,int implicit_v_len);
	
	/**
	 * @param path 			The Path to the INI file
	 * @param format		The {@link IniFormat} used to parse
	 * @param dispatcher	The {@link Consumer}&lt;{@link IniDispatch}&gt; called whenever a node is processed  
	 * @return an {@link IniInterruptCode}, {@link IniInterruptCode#SUCCESS} if everything went well
	 */
	public static IniInterruptCode loadIniPath(String path,IniFormat format,Function<IniDispatch,Integer>dispatcher) {
		return IniInterruptCode.fromCode(load_ini_path0(path,format,dispatcher));
	}
	
	/**
	 * 
	 * The <code>bean</code> should contain fields of the corresponding types.<br>
	 * Each field should be named as follows:<br>
	 * <ul>
	 * 	<li><code>name</code> or</li>
	 * 	<li><code>section$name</code> or</li>
	 * </ul>
	 * Every character in the that matches <code>[^\w\d]</code> should be replaced with an underscore (<code>'_'</code>)<br>
	 * Fields where the name starts with a digit (<code>0-9</code>) should start with a dollar-sign (<code>'$'</code>)<br><br>
	 * Example INI:
	 * <pre>
	 * my_string=Hello World
	 * 
	 * 1st_number=42
	 * 
	 * [my_section]
	 * my_int=123
	 * my_bool=yes
	 * my.other.string=INIs are cool
	 * </pre>
	 * And the Bean-Class:
	 * <pre>
	 * class Bean {
	 *     
	 *     public String my_string;                  // "Hello World"
	 *     public int $1st_number;                   // 42
	 *     public int my_section$my_int;             // 123
	 *     public boolean my_section$my_bool;        // true
	 *     public String my_section$my_other_string; // "INIs are cool"
	 *     
	 * }
	 * </pre>
	 * <br>
	 * Valid types for the <code>Bean</code> are:
	 * <ul>
	 * 	<li><code>int</code> - integers</li>
	 * 	<li><code>double</code> - floating point numbers</li>
	 * 	<li><code>boolean</code> - boolean values</li>
	 * 	<li><code>{@link java.lang.String}</code> - strings</li>
	 * 	<li><code>{@link java.lang.Object}[]</code> - arrays</li>
	 * </ul>
	 * 
	 * @param path		The Path to the INI file
	 * @param format	The {@link IniFormat} used to parse
	 * @param bean		The {@link Object} to store the INI values.
	 * @param required	If <code>true</code>, a {@link IllegalArgumentException} is thrown if <code>bean</code> does not have an appropriate field for a key
	 * 
	 * @see #loadIniPath(String, IniFormat, Consumer)
	 * 
	 * @return an {@link IniInterruptCode}, {@link IniInterruptCode#SUCCESS} if everything went well
	 */
	public static IniInterruptCode loadIniPathToBean(String path,IniFormat format,Object bean,boolean required) {
		final Class<?>beanclass=bean.getClass();
		
		return loadIniPath(path,format,dispatch->{
			if(dispatch.getNodeType()!=IniNodeType.KEY)
				return 0;
			
			final String name=dispatch.data.replaceAll("[^\\w\\d]","_");
			final String append=dispatch.append_to.replaceAll("[^\\w\\d]","_");
			
			String field=(append.isEmpty()?"":append+"$")+name;
			
			if(field.matches("^\\d.*"))field="$"+field;
			
			try {
				Field f=beanclass.getField(field);
				
				Class<?>type=f.getType();
				
				if(type==int.class)type=Integer.class;
				if(type==double.class)type=Double.class;
				if(type==boolean.class)type=Boolean.class;
				
				Object parsed=parse(dispatch.value);
				
				if(type!=parsed.getClass()) {
					if(type==Integer.class&&parsed.getClass()==Double.class)
						parsed=(Integer)parsed;
					else throw new IllegalArgumentException("Types do not match: "+type.getSimpleName()+" and "+parsed.getClass().getSimpleName());
				}
				
				f.set(bean,parsed);
			} catch (Exception e) {
				if(required)
					throw new RuntimeException(e);
			}
			
			return 0;
		});
	}

	/**
	 * Parses the following types:
	 * <ul>
	 * <li>{@link Integer}: <code>/[+-]?\d+/</code> -- parsed via {@link Integer#valueOf(String)}</li>
	 * <li>{@link Double}: <code>/^[+-]?((\.\d+)|(\d+\.)|(\d+\.\d+)|\d+)([eE][+-]?\d+)?$/</code> -- parsed via {@link Double#valueOf(String)}</li>
	 * <li>{@link Boolean}: <code>/true|false|yes|no|on|off/</code> -- <code>true</code>; <code>yes</code> and <code>on</code> are considered {@link Boolean#TRUE}</li>
	 * <li>{@link Object}[]: <code>/\[.*\]/</code> -- a comma-separated list of {@link Integer}s, {@link Double}s, {@link Boolean}s, {@link String}s and {@link Object}[]s (<i>recursive</i>)</li>
	 * <li>{@link String}: everything else (returns the parameter <code>str</code>)</li>
	 * </ul>
	 * 
	 * @param str the String to be parsed
	 * @return the parsed object
	 */
	public static Object parse(String str) {
		if(str.matches("[+-]?\\d+"))
			return Integer.valueOf(str);
		if(str.matches("^[+-]?((\\.\\d+)|(\\d+\\.)|(\\d+\\.\\d+)|\\d+)([eE][+-]?\\d+)?$"))
			return Double.valueOf(str);
		if(str.matches("true|false|yes|no|on|off"))
			return str.matches("true|yes|on");
		if(str.matches("\\[.*\\]")) {
			List<Object>list=new ArrayList<>();
			
			try(StringReader sr=new StringReader(str.substring(1,str.length()-1).trim())) {
				String next="";
				
				int open=0;
				
				int chr;
				while((chr=sr.read())>=0) {
					
					if(chr==',') {
						if(open==0) {
							list.add(parse(next.trim()));
							next="";
							continue;
						}
					}
					else if(chr=='[')
						++open;
					else if(chr==']') {
						if(--open<0)
							throw new RuntimeException("Invalid ']'");
					}
					
					next+=(char)chr;
				}
				
				if(!next.isBlank())
					list.add(parse(next.trim()));
			} catch(Exception e) {
				throw new RuntimeException(e);
			}
			
			return list.toArray();
		}
		return str;
	}
	
	/** {@link #loadIniPathToBean(String, IniFormat, Object, boolean)} with <code>required = true</code> 
	 * 
	 * @see #loadIniPathToBean(String, IniFormat, Object, boolean)
	 * 
	 * @return an {@link IniInterruptCode}, {@link IniInterruptCode#SUCCESS} if everything went well
	 */
	public static IniInterruptCode loadIniPathToBean(String path,IniFormat format,Object bean) {
		return loadIniPathToBean(path,format,bean,true);
	}
	
	private static native int load_ini_path0(String path,IniFormat format,Function<IniDispatch,Integer>dispatcher);
	
	/** @deprecated internal use only! */
	@Deprecated
	public static native void enable_debug();
	
}
