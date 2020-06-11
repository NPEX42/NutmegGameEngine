package nutmeg.core.datatypes;

public class ShortPtr {
	private short[] value = new short[1];
	
	public short   GetValue() {return value[0];}
	public short[] GetRef  () {return value;   }
	
	public void SetValue(short v    ) {value[0] = v;}
	public void SetRef  (short[] ptr) {value = ptr; }
}
