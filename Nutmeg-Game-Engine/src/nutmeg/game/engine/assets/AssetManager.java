package nutmeg.game.engine.assets;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import nutmeg.al.core.AudioBuffer;
import nutmeg.al.core.AudioManager;
import nutmeg.al.core.AudioSource;
import nutmeg.core.IO;
import nutmeg.core.Logger;
import nutmeg.core.Nutmeg;
import nutmeg.gl.core.Texture2D;

public class AssetManager {
	private static HashMap<String, Texture2D> textures;
	private static HashMap<String, AudioBuffer> sounds;
	private static Queue<AudioBuffer> audioQueue;
	
	public static void Init() {
		AudioManager.Init();
		textures = new HashMap<String, Texture2D>();
		sounds = new HashMap<String, AudioBuffer>();
		audioQueue =  new LinkedList<AudioBuffer>();
	}
	
	public static void Destroy() {
		for(String name : textures.keySet()) { textures.get(name).Delete(); }
		for(String name : sounds.keySet())   { sounds.get(name).Delete(); }
		AudioManager.Destroy();
	}
	
	public static void LoadSound(String filePath, String friendlyName) {
		if(!sounds.containsKey(friendlyName)) {
			Logger.Log("NMGE", "Audio System", "Loading sound '"+filePath+"' as '"+friendlyName+"'");
			sounds.put(friendlyName, AudioBuffer.Create(filePath));
		}
	}
	
	public static void LoadTexture(String filePath, String friendlyName) {
		if(!textures.containsKey(friendlyName)) {
			textures.put(friendlyName, Texture2D.Load(filePath));
			Logger.Log("NMGE", "Application", "Loaded texture '"+filePath+"' as '"+friendlyName+"'");
		}
	}
	
	public static void LoadTextures(String filePath) {
		String[] source = IO.loadStrings(filePath);
		Logger.Assert("NMGE", "Application","Unable to Load texture list '"+filePath+"'", source != null);
		for(String line : source) {
			String path = line.split(",")[0];
			String name = line.split(",")[1];
			
			LoadTexture(path, name);
		}
		Logger.Log("NMGE", "AssetManager", "Loaded "+source.length+" images");
	}
	
	public static void LoadSounds(String filePath) {
		String[] source = IO.loadStrings(filePath);
		Logger.Assert("NMGE", "Audio System","Unable to Load sound list '"+filePath+"'", source != null);
		for(String line : source) {
			String path = line.split(",")[0];
			String name = line.split(",")[1];
			
			LoadSound(path, name);
		}
		Logger.Log("NMGE", "AssetManager", "Loaded "+source.length+" sounds");
	}
	
	public static void PlaySound(String name, boolean loop) {
		Logger.Log("NMGE", "Audio System", "Playing Sound '"+name+"' Loop?: "+loop);
		AudioBuffer buffer = sounds.get(name);
		AudioSource source = new AudioSource();
		source.Play(buffer);
		source.SetLooping(loop);
		source.Delete();
	}
	
	public static void QueueSound(String name) {
		audioQueue.add(sounds.get(name));
	}
	
	static float fAccumTime;
	private static AudioBuffer playingSound;
	public static void UpdatePlayQueue(float timeStep) {
		if(Nutmeg.GetDebug()) Logger.Log("NMGE", "Audio System", "Updating Audio System - fAccumTime: "+fAccumTime+" - Sounds Queued: "+audioQueue.size());
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
	
	public static Texture2D GetTexture2D(String name) {
		return textures.get(name);
	}
	
	
}
