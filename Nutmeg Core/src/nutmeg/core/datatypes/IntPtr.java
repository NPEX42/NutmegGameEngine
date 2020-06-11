package nutmeg.core.datatypes;

public class IntPtr {
	private int[] value = new int[1];
	
	public int   GetValue() {return value[0];}
	public int[] GetRef  () {return value;   }
	
	public void SetValue(int v    ) {value[0] = v;}
	public void SetRef  (int[] ptr) {value = ptr; }
}
