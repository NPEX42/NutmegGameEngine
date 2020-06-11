package nutmeg.core;


public class ArrayUtils {
	public static <T> T[] concatenate(T[] a, T[] b) {
	    int aLen = a.length;
	    int bLen = b.length;

	    @SuppressWarnings("unchecked")
	    T[] c = (T[]) new Object[aLen + bLen];
	    System.arraycopy(a, 0, c, 0, aLen);
	    System.arraycopy(b, 0, c, aLen, bLen);

	    return c;
	}
	
	public static float[] concatenate(float[] a, float[] b) {
	    int aLen = a.length;
	    int bLen = b.length;
	    
	    float[] c = new float[aLen + bLen];
	    System.arraycopy(a, 0, c, 0, aLen);
	    System.arraycopy(b, 0, c, aLen, bLen);

	    return c;
	}
}
