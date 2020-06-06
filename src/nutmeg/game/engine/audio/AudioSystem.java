package nutmeg.game.engine.audio;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import nutmeg.al.core.AudioBuffer;
import nutmeg.al.core.AudioManager;
import nutmeg.al.core.AudioSource;
import nutmeg.core.IO;
import nutmeg.core.Logger;
import nutmeg.core.Nutmeg;

public class AudioSystem {
	
	
	private static HashMap<String, AudioBuffer> buffers;
	private static AudioBuffer playingSound;
	private static Queue<AudioBuffer> audioQueue = new LinkedList<AudioBuffer>();
	private static int sampleRate = 44100;
	public static void Init() {
		AudioManager.Init();
		buffers = new HashMap<String, AudioBuffer>();
	}
	
	public static void QueueSound(String friendlyName) {
		audioQueue.add(buffers.get(friendlyName));
	}
	static float fAccumTime;
	public static void UpdatePlayQueue(float timeStep) {
		if(Nutmeg.bDebugMode) Logger.Log("NMGE", "Audio System", "Updating Audio System - fAccumTime: "+fAccumTime+" - Sounds Queued: "+audioQueue.size());
		if(playingSound == null && !audioQueue.isEmpty()) {
			playingSound = audioQueue.poll();
			AudioSource source = new AudioSource();
			source.Play(playingSound);
		}
		
		if(playingSound != null && fAccumTime > playingSound.GetLength() / 1000f) {
			fAccumTime = playingSound.GetLength();
			playingSound = audioQueue.poll();
			if(playingSound != null) {
				Logger.Log("NMGE", "Audio System", "Playing Sound");
				AudioSource source = new AudioSource();
				source.Play(playingSound);
				fAccumTime = 0;
			}
		}
		if(!audioQueue.isEmpty())fAccumTime += timeStep;
	}
	
	public static void PlaySound(String filePath, boolean loop) {
		Logger.Log("NMGE", "Audio System", "Playing Sound '"+filePath+"' Loop?: "+loop);
		AudioBuffer buffer = AudioBuffer.Create(filePath);
		AudioSource source = new AudioSource();
		source.Play(buffer);
		source.SetLooping(loop);
		buffer.Delete();
		source.Delete();
	}
	
	
	public static void LoadSound(String filePath, String friendlyName) {
		Logger.Log("NMGE", "Audio System", "Loading Sound Assets '"+filePath+"' As '"+friendlyName+"'");
		buffers.put(friendlyName, AudioBuffer.Create(filePath));
	}
	
	public static void PlaySoundAsset(String friendlyName, boolean loop) {
		if(buffers.containsKey(friendlyName)) {
			AudioSource source = new AudioSource();
			source.Play(buffers.get(friendlyName));
			source.SetLooping(loop);
		} else {
			Logger.Assert("NMGE", "Audio System", "Unable To Find Sound Asset '"+friendlyName+"'", false);
		}
	}
	
	public static void PlaySoundRawMono16(short[] data) {
		if(data == null || data.length == 0) return;
		if(Nutmeg.bDebugMode) Logger.Log("NMGE", "Audio System","Playing "+data.length+" Raw Samples...");
		AudioBuffer buffer = AudioBuffer.CreateMono16(data, sampleRate);
		AudioSource source = new AudioSource();
		source.Play(buffer);
		source.SetLooping(false);
		buffer.Delete();
		source.Delete();
	}
	
	public static void LoadAssetsFromFile(String filePath) {
		String[] source = IO.loadStrings(filePath);
		Logger.Assert("NMGE", "Audio System","Unable to Load asset list '"+filePath+"'", source != null);
		for(String line : source) {
			String path = line.split(",")[0];
			String name = line.split(",")[1];
			
			LoadSound(path, name);
		}
	}
	
	public static void SetSampleRate(int freq) {
		sampleRate = freq;
	}
	
	public static void Destroy() {
		Logger.Log("NMGE", "Audio System","Destroying The Audio Manager");
		for(String key : buffers.keySet()) { buffers.get(key).Delete(); }
		AudioManager.Destroy();
	}
}
