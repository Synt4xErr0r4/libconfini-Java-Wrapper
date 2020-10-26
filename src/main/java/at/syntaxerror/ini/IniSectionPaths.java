package at.syntaxerror.ini;

import java.util.HashMap;
import java.util.Map;

/** Possible values of {@link IniFormat#section_paths} */
public enum IniSectionPaths {
	
	/** Section paths starting with a dot express nesting to the current parent, to root otherwise **/
	INI_ABSOLUTE_AND_RELATIVE(0),
	/** Section paths starting with a dot will be cleaned of their leading dot and appended to root **/
	INI_ABSOLUTE_ONLY(1),
	/** Format supports sections, but the dot does not express nesting and is not a meta-character **/
	INI_ONE_LEVEL_ONLY(2),
	/** Format does <b>not</b> support sections -- <code>/\[[^\]]*\]/g</code>, if any, will be treated as keys! **/
	INI_NO_SECTIONS(3)
	
	;

	public static final IniSectionPaths DEFAULT=INI_ABSOLUTE_AND_RELATIVE;
	
	private static final Map<Integer,IniSectionPaths>MAP;
	
	static {
		MAP=new HashMap<>();
		
		for(IniSectionPaths iniconst:values())
			MAP.put(iniconst.value,iniconst);
	}
	
	public static IniSectionPaths fromValue(int value) {
		return MAP.get(value);
	}
	
	private int value;
	
	private IniSectionPaths(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
}
