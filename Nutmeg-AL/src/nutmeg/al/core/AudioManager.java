package nutmeg.al.core;

import java.nio.ByteBuffer;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;
public class AudioManager {
	private static long device, context;
	public static void Init() {
//		ALC.create();
		device = ALC10.alcOpenDevice((ByteBuffer) null);
		ALCCapabilities caps = ALC.createCapabilities(device);
		
		context = ALC10.alcCreateContext(device, new int[1]);
		ALC10.alcMakeContextCurrent(context);
		AL.createCapabilities(caps);
	}
	
	public static void Destroy() {
		ALC10.alcCloseDevice(device);
	}
	
}
