package nutmeg.core.datatypes;

public class LongPtr {
	private long[] value = new long[1];
	
	public long   GetValue() {return value[0];}
	public long[] GetRef  () {return value;   }
	
	public void SetValue(long v    ) {value[0] = v;}
	public void SetRef  (long[] ptr) {value = ptr; }
}
