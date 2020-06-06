package nutmeg.al.core;

import org.lwjgl.openal.AL10;

public class AudioSource {
	private int ID;

	public AudioSource() {
		super();
		ID = AL10.alGenSources();
		
		AL10.alSourcef(ID, AL10.AL_GAIN, 1);
		AL10.alSourcef(ID, AL10.AL_PITCH, 1);
		
		AL10.alSource3f(ID, AL10.AL_POSITION, 0, 0, 0);
	}
	
	public void Delete() {
		AL10.alDeleteBuffers(ID);
	}
	
	public void Play(AudioBuffer buffer) {
		AL10.alSourcei(ID, AL10.AL_BUFFER, buffer.GetID());
		AL10.alSourcePlay(ID);
	}

	public void SetLooping(boolean loop) {
		AL10.alSourcei(ID, AL10.AL_LOOPING, (loop) ? 1 : 0);
	}
	
}
