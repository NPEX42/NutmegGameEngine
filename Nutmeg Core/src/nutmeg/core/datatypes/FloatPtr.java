package nutmeg.core.datatypes;

public class FloatPtr {
	private float[] value = new float[1];
	
	public float   GetValue() {return value[0];}
	public float[] GetRef  () {return value;   }
	
	public void SetValue(float v    ) {value[0] = v;}
	public void SetRef  (float[] ptr) {value = ptr; }
}
