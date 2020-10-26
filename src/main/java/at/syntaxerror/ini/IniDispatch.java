package at.syntaxerror.ini;

/** Dispatch of a single INI node */
public class IniDispatch {
	
	public final IniFormat format;
	public final int type;
	public final String data,
						value,
						append_to;
	public final int dispatch_id;
	
	public final IniNodeType node_type;
	
	public IniDispatch(IniFormat format,int type,String data,String value,String append_to,int dispatch_id) {
		this.format=format;
		this.type=type;
		this.data=data;
		this.value=value;
		this.append_to=append_to;
		this.dispatch_id=dispatch_id;
		
		node_type=IniNodeType.fromValue(type);
	}
	
	public IniFormat getFormat() {
		return format;
	}
	
	public long getRawType() {
		return type;
	}
	
	public IniNodeType getNodeType() {
		return node_type;
	}
	
	public String getData() {
		return data;
	}
	
	public String getValue() {
		return value;
	}
	
	public String getAppendTo() {
		return append_to;
	}
	
	public int getDispatchID() {
		return dispatch_id;
	}
	
	@Override
	public String toString() {
		return"IniDispatch[format = "+format+", type = "+type+", data = "+data+", value = "+value+", append_to = "+append_to+", dispatch_id = "+dispatch_id+"]";
	}
	
}
