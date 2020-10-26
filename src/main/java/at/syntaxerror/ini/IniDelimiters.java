package at.syntaxerror.ini;

import java.util.HashMap;
import java.util.Map;

/** Common array and key-value delimiters (but a delimiter may also be any other ASCII character not present in this list) */
public enum IniDelimiters {
	
	/** In multi-line INIs: <code>/(?:\\(?:\n\r?|\r\n?)|[\t \v\f])+/</code>, in non-multi-line INIs: <code>/[\t \v\f])+/</code> */
	INI_ANY_SPACE('\0'),
	/** Equals character (<code>'='</code>) */
	INI_EQUALS('='),   
	/** Colon character (<code>':'</code>) */
	INI_COLON(':'),
	/** Dot character (<code>'.'</code>) */
	INI_DOT('.'),
	/** Comma character (<code>','</code>) */
	INI_COMMA(',')   
	
	;
	
	public static final IniDelimiters DEFAULT=INI_ANY_SPACE;
	
	private static final Map<Character,IniDelimiters>MAP;
	
	static {
		MAP=new HashMap<>();
		
		for(IniDelimiters iniconst:values())
			MAP.put(iniconst.value,iniconst);
	}
	
	public static IniDelimiters fromValue(char value) {
		return MAP.get(value);
	}
	
	private char value;
	
	private IniDelimiters(char value) {
		this.value = value;
	}
	
	public char getValue() {
		return value;
	}
	
}
