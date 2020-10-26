package at.syntaxerror.ini;

import java.util.HashMap;
import java.util.Map;

/** Possible values of {@link IniFormat#semicolon_marker} and {@link IniFormat#hash_marker} (i.e., meaning of <code>/\s+;/</code> and <code>/\s+#/</code> in respect to a format)
**/
public enum IniCommentMarker {
	
	/** This marker opens a comment or a disabled entry */
	INI_DISABLED_OR_COMMENT(0),
	/** This marker opens a comment */
	INI_ONLY_COMMENT(1),
	/** This marker opens a comment that has been marked for deletion and must not be dispatched or counted */
	INI_IGNORE(2),
	/** This is not a marker at all, but a normal character instead */
	INI_IS_NOT_A_MARKER(3)
	
	;
	
	public static final IniCommentMarker DEFAULT=INI_DISABLED_OR_COMMENT;
	
	private static final Map<Integer,IniCommentMarker>MAP;
	
	static {
		MAP=new HashMap<>();
		
		for(IniCommentMarker iniconst:values())
			MAP.put(iniconst.value,iniconst);
	}
	
	public static IniCommentMarker fromValue(int value) {
		return MAP.get(value);
	}
	
	private int value;
	
	private IniCommentMarker(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
}
