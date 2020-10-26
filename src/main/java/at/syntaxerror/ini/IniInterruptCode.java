package at.syntaxerror.ini;

import java.util.HashMap;
import java.util.Map;

public enum IniInterruptCode {
	
	/** There have been no interruptions, everything went well */
	SUCCESS(0),
	/** Interrupted by the user during init */
	INIT_INTERRUPION(1),
	/** Interrupted by the user during reading */
	LOOP_INTERRUPTION(2),
	/** File inaccessible */
	FILE_INACESSIBLE(4),
	/** Error allocating virtual memory */
	MEM_ALLOC(5),
	/** Error reading the file */
	FILE_READ(6),
	/** Out-of-range error */
	OUT_OF_RANGE(7),
	/** The stream specified is not seekable */
	BAD_STREAM(8),
	/** File too large */
	FILE_TOO_BIG(9),
	/** Address is read-only */
	ADDR_READONLY(10),
	
	/** An unknown error occured (Java-only) */
	UNKNOWN(-1);
	
	;
	
	private static Map<Integer,IniInterruptCode>CODES=new HashMap<>();
	
	static {
		for(IniInterruptCode code:values())
			CODES.put(code.value,code);
	}
	
	public static IniInterruptCode fromCode(int code) {
		return CODES.getOrDefault(code,UNKNOWN);
	}
	
	private int value;
	
	private IniInterruptCode(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
}
