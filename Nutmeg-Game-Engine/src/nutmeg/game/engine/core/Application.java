package nutmeg.game.engine.core;

import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;

import org.joml.Vector2f;

import nutmeg.core.IO;
import nutmeg.core.Logger;
import nutmeg.core.Nutmeg;
import nutmeg.game.engine.assets.AssetManager;
import nutmeg.game.engine.ecs.Camera;
import nutmeg.game.engine.ecs.IdentityCamera;
import nutmeg.game.engine.ecs.Mesh;
import nutmeg.game.engine.ecs.Transform;
import nutmeg.game.engine.input.Keyboard;
import nutmeg.game.engine.input.Mouse;
import nutmeg.game.engine.rendering.MeshRenderer;
import nutmeg.game.engine.ui.ToggleButton;
import nutmeg.gl.core.Renderer;
import nutmeg.gl.core.Shader;
import nutmeg.gl.core.Texture2D;

import static org.lwjgl.opengl.GL46.*;

public abstract class Application {
	private HashMap<String, Texture2D> textureAssets = new HashMap<String, Texture2D>();
	
	//Quad Mesh Data ============================
	private float[] pos = {
			+0.5f, +0.5f, 0,
			-0.5f, +0.5f, 0,
			-0.5f, -0.5f, 0,
			+0.5f, -0.5f, 0
	};
	
	private float[] uvs = {
			1.0f,0.0f,
			0.0f,0.0f,
			0.0f,1.0f,
			1.0f,1.0f,	
	};
	
	private int[] tris = {
		0,1,2,
		2,3,0
	};
	//===========================================
	
	public abstract void OnUserCreate ();
	public abstract void OnUserUpdate ();
	public abstract void OnUserDestroy();
	
	public void ProcessInput() {};
	public void OnUIRender() {};
	
	public short[] OnAudioSample(int nSamplesRequested, float nTimeStep) { return null; }
	
	public float fWidth, fHeight, fElapsedTime, fGlobalTime;
	
	public Keyboard keyboard;
	
	private Camera camera;
	
	private Mesh quadMesh;
	private Transform transform;
	private Shader shader;
	
	private Texture2D white, missing;

	public Mouse mouse;
	
	public void ConstructWindow(int _nWidth, int _nHeight, String _sTitle, boolean _bDebug) {
		Window window = Window.Open(_nWidth, _nHeight, _sTitle, _bDebug);
		if(window == null) { Logger.Warn("NMGE", "Application", "Unable To Open A window..."); return; } //Check that the window could be opened
		AssetManager.Init();
		UI.Init(this);
		
		keyboard = new Keyboard(window);
		mouse = new Mouse(window);
		fHeight = _nHeight;
		fWidth = _nWidth;
		
		quadMesh = new Mesh(pos, uvs, tris);
		transform = new Transform();
		try {
			SetShader(Shader.LoadJar("nutmeg/game/engine/res/shaders/simple.vs", "nutmeg/game/engine/res/shaders/simple.fs"));
		} catch (IOException ioex) {
			Logger.Throw("NMGE", "Application", "An IOException Has Occurred", ioex);
		}
		
		white = Texture2D.LoadJar("nutmeg/game/engine/res/textures/white.png");
		
		camera = new IdentityCamera();
		
		OnUserCreate(); //Call User creation code
		long tp1, tp2;
		while(window.IsActive() && !Nutmeg.bClose) { //Every Frame, run the users update code, and update the window
			tp1 = System.currentTimeMillis();
			ProcessInput();
			OnUserUpdate();
			UI.Update();
			short[] audio = new short[0];
			
			
			//TODO: Test Different Audio sampling algorithms
//			if(fNextSampleTimer > 0.1f) {
//				audio = OnAudioSample(audioBuffersPerSecond / 10, 0.001f); 
//				fNextSampleTimer -= 0.1f;
//			}
//			fNextSampleTimer += fElapsedTime;
//			AudioSystem.PlaySoundRawMono16(audio);
			
			
			window.Update();
			
			//
			fWidth = window.getfWidth();
			fHeight = window.getfHeight();
			tp2 = System.currentTimeMillis();
			fElapsedTime = (tp2 - tp1) / 1000f;
			fGlobalTime += fElapsedTime;
			
			AssetManager.UpdatePlayQueue(fElapsedTime);
		}
		Logger.Log("NMGE", "Application", "Delete Quad Mesh");
		quadMesh.Delete();
		Logger.Log("NMGE", "Application", "Delete Texture Assets");
		AssetManager.Destroy();
		Logger.Log("NMGE", "Application", "Running OnUserDestroy()");
		OnUserDestroy(); //Run the users destroy code
		Logger.Log("NMGE", "Application", "Destroying the window");
		window.Destroy(); //Destroy the window
		Logger.Log("NMGE", "Application", "Closing GLFW");
		Window.CloseGLFW(); //Close / clean-up GLFW
	}
	
	//API functions
	public void SetShader(Shader s) {
		shader = s;
		shader.BindAttrib(0, "a_Position");
		shader.BindAttrib(1, "a_UV");
	}
	
	public void SetCamera(Camera cam) {
		camera = cam;
	}
	
	public void DrawQuad(float x, float y, float w, float h) {
		transform.SetPosition(x, y);
		transform.SetScale2D(w, h);
		shader.SetUniformTexture("t_Albedo", white, 0, GL_TEXTURE0);
		shader.SetUniformColor("u_TintColor", Color.white);
		shader.SetUniformMat4("u_Transform", transform.GetTransformMatrix());
		shader.SetUniformMat4("u_Projection", camera.GetProjection(fWidth, fHeight));
		MeshRenderer.Render(quadMesh);
	}
	
	public void DrawQuad(float x, float y, float w, float h, Color color) {
		transform.SetPosition(x, y);
		transform.SetScale2D(w, h);
		shader.SetUniformTexture("t_Albedo", white, 0, GL_TEXTURE0);
		shader.SetUniformColor("u_TintColor", color);
		shader.SetUniformMat4("u_Transform", transform.GetTransformMatrix());
		shader.SetUniformMat4("u_Projection", camera.GetProjection(fWidth, fHeight));
		MeshRenderer.Render(quadMesh);
	}
	
	public void DrawQuad(float x, float y, float w, float h, Texture2D texture) {
		transform.SetPosition(x, y);
		transform.SetScale2D(w, h);
		shader.SetUniformTexture("t_Albedo", texture, 0, GL_TEXTURE0);
		shader.SetUniformColor("u_TintColor", Color.white);
		shader.SetUniformMat4("u_Transform", transform.GetTransformMatrix());
		shader.SetUniformMat4("u_Projection", camera.GetProjection(fWidth, fHeight));
		MeshRenderer.Render(quadMesh);
	}
	
	public void DrawQuad(float x, float y, float w, float h, Texture2D texture, Color tint) {
		transform.SetPosition(x, y);
		transform.SetScale2D(w, h);
		shader.SetUniformTexture("t_Albedo", texture, 0, GL_TEXTURE0);
		shader.SetUniformColor("u_TintColor", tint);
		shader.SetUniformMat4("u_Transform", transform.GetTransformMatrix());
		shader.SetUniformMat4("u_Projection", camera.GetProjection(fWidth, fHeight));
		MeshRenderer.Render(quadMesh);
	}
	
	public void DrawTextureAsset(float x, float y, float w, float h, String name) {
		Texture2D tex = AssetManager.GetTexture2D(name);
		DrawQuad(x, y, w, h, tex);
	}
	
	public void DrawTextureAsset(float x, float y, String name) {
		Texture2D tex = AssetManager.GetTexture2D(name);
		DrawQuad(x, y, tex.GetWidth(), tex.GetHeight(), tex);
	}
	
	public void LoadTextureAsset(String filePath, String friendlyName) {
		AssetManager.LoadTexture(filePath, friendlyName);
	}
	
	public void Background(Color color) {
		Renderer.Background(color);
	}
	
	public void LoadTextureAssets(String filePath) {
		AssetManager.LoadTextures(filePath);
	} 
	
	public void LoadAudioAssets(String filePath) {
		AssetManager.LoadSounds(filePath);
	}
	
	public void LoadAudioAsset(String filePath, String name) {
		AssetManager.LoadSound(filePath, name);
	}
	
	public void QueueSound(String name) {
		AssetManager.QueueSound(name);
	}
	
	public void PlaySound(String name, boolean loop) {
		AssetManager.PlaySound(name, loop);
	}
	
	public void AddToggleButton(String name, float x, float y, float w, float h,  Color activeColor, Color inactiveColor) {
		UI.AddToggleButton(name, new Vector2f(x,y), new Vector2f(w,h), activeColor, inactiveColor);
	}
	
	public ToggleButton GetToggleButton(String name) {
		return UI.GetToggleButton(name);
	}
	
	public void AddPushButton(String name, float x, float y, float w, float h,  Color activeColor, Color inactiveColor) {
		UI.AddPushButton(name, new Vector2f(x,y), new Vector2f(w,h), activeColor, inactiveColor);
	}
	
	public PushButton GetPushButton(String name) {
		return UI.GetPushButton(name);
	}
}
