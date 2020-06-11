package nutmeg.core.datatypes;

public class BoolPtr {
	private boolean[] value = new boolean[1];
	
	public boolean   GetValue() {return value[0];}
	public boolean[] GetRef  () {return value;   }
	
	public void SetValue(boolean v    ) {value[0] = v;}
	public void SetRef  (boolean[] ptr) {value = ptr; }
}
