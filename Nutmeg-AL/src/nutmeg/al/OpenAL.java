package nutmeg.al;

import org.lwjgl.openal.AL10;

public class OpenAL {
	public static String GetVersion() {
		return AL10.alGetString(AL10.AL_VERSION);
	} 
	
	public static String GetVendor() {
		return AL10.alGetString(AL10.AL_VENDOR);
	}
	
	public static String GetRenderer() {
		return AL10.alGetString(AL10.AL_RENDERER);
	}
	
	public static String[] GetExtenstions() {
		return AL10.alGetString(AL10.AL_EXTENSIONS).split(" ");
	}
	
	
}
