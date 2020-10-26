package at.syntaxerror.ini;

import java.util.HashMap;
import java.util.Map;

public enum IniNodeType {
	
	/** This is a node impossible to categorize */
	UNKNOWN(0),
	/** Not used by <i>libconfini</i> (values are dispatched together with keys) -- but available for user's implementations */
	VALUE(1),
	/** This is a key */
	KEY(2),
	/** This is a section path */
	SECTION(3),
	/** This is a comment */
	COMMENT(4),
	/** This is an inline comment */
	INLINE_COMMENT(5),
	/** This is a disabled key */
	DISABLED_KEY(6),
	/** This is a disabled section path */
	DISABLED_SECTION(7),
	
	;
	
	private static final Map<Integer,IniNodeType>MAP;
	
	static {
		MAP=new HashMap<>();
		
		for(IniNodeType iniconst:values())
			MAP.put(iniconst.value,iniconst);
	}
	
	public static IniNodeType fromValue(int value) {
		return MAP.get(value);
	}
	
	private int value;
	
	private IniNodeType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
}
