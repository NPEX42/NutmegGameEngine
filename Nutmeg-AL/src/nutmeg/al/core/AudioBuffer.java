package nutmeg.al.core;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.openal.AL10;
import org.lwjgl.system.MemoryUtil;

import nutmeg.al.data.WaveData;

public class AudioBuffer {
	private int ID, lengthMillis;

	private AudioBuffer(int iD, int len) {
		ID = iD;
		lengthMillis = len;
	}
	
	private AudioBuffer(int iD) {
		ID = iD;
		lengthMillis = 1000;
	}
	
	public static AudioBuffer Create(String filePath) {
		int id = AL10.alGenBuffers();
		WaveData data = WaveData.create(filePath);
		AL10.alBufferData(id, data.format, data.data, data.samplerate);
		data.dispose();
		
		return new AudioBuffer(id, data.lengthMillis);
	}
	
	public static AudioBuffer CreateMono8(byte[] samples, int sampleRate) {
		int id = AL10.alGenBuffers();
		ByteBuffer buffer = MemoryUtil.memAlloc(samples.length);
		buffer.put(samples);
		buffer.flip();
		AL10.alBufferData(id, AL10.AL_FORMAT_MONO8, buffer, sampleRate);
		return new AudioBuffer(id);
	}
	
	public static AudioBuffer CreateMono16(short[] samples, int sampleRate) {
		int id = AL10.alGenBuffers();
		ShortBuffer buffer = MemoryUtil.memAllocShort(samples.length);
		buffer.put(samples);
		buffer.flip();
		AL10.alBufferData(id, AL10.AL_FORMAT_MONO16, buffer, sampleRate);
		return new AudioBuffer(id);
	}
	
	public static AudioBuffer CreateStereo8(byte[] samples, int sampleRate) {
		int id = AL10.alGenBuffers();
		ByteBuffer buffer = MemoryUtil.memAlloc(samples.length);
		buffer.put(samples);
		buffer.flip();
		AL10.alBufferData(id, AL10.AL_FORMAT_STEREO8, buffer, sampleRate);
		return new AudioBuffer(id);
	}
	
	public static AudioBuffer CreateStereo16(short[] samples, int sampleRate) {
		int id = AL10.alGenBuffers();
		ShortBuffer buffer = MemoryUtil.memAllocShort(samples.length);
		buffer.put(samples);
		buffer.flip();
		AL10.alBufferData(id, AL10.AL_FORMAT_STEREO16, buffer, sampleRate);
		return new AudioBuffer(id);
	}
	
	public void Delete() {
		AL10.alDeleteBuffers(ID);
	}
	
	public int GetID() {
		return ID;
	}
	
	public int GetLength() {
		return lengthMillis;
	}
	
}
