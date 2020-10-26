package at.syntaxerror.ini;

import java.util.HashMap;
import java.util.Map;

/** Possible values of {@link IniFormat#multiline_nodes} */
public enum IniMultiline {
	
	/** Comments, section paths and keys -- disabled or not -- are allowed to be multi-line **/
	INI_MULTILINE_EVERYWHERE(0),
	/** Only section paths and keys -- disabled or not -- are allowed to be multi-line **/
	INI_BUT_COMMENTS(1),
	/** Only active section paths and active keys are allowed to be multi-line **/
	INI_BUT_DISABLED_AND_COMMENTS(2),  
	/** Multi-line escape sequences are disabled **/
	INI_NO_MULTILINE(3)
	
	;
	
	public static final IniMultiline DEFAULT=INI_MULTILINE_EVERYWHERE;
	
	private static final Map<Integer,IniMultiline>MAP;
	
	static {
		MAP=new HashMap<>();
		
		for(IniMultiline iniconst:values())
			MAP.put(iniconst.value,iniconst);
	}
	
	public static IniMultiline fromValue(int value) {
		return MAP.get(value);
	}
	
	private int value;
	
	private IniMultiline(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
}
