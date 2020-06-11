package nutmeg.game.engine.core;

import java.awt.Color;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;


import nutmeg.al.core.AudioManager;
import nutmeg.core.IO;
import nutmeg.core.Logger;
import nutmeg.core.Nutmeg;

import nutmeg.game.engine.ecs.Camera;
import nutmeg.game.engine.ecs.Geometry;
import nutmeg.game.engine.ecs.IdentityCamera;

import nutmeg.game.engine.ecs.Transform;
import nutmeg.game.engine.input.Keyboard;
import nutmeg.game.engine.input.Mouse;

import nutmeg.game.engine.rendering.MeshRenderer;
import nutmeg.game.engine.ui.UI;
import nutmeg.gl.core.Framebuffer;
import nutmeg.gl.core.Renderer;
import nutmeg.gl.core.Shader;
import nutmeg.gl.core.Texture2D;

import static org.lwjgl.opengl.GL46.*;
//@SuppressWarnings("unused")
public abstract class Application implements ApplicationAPI {
	private Window window;
	private Shader shader;
	private Camera camera;
	private Texture2D white;
	private Queue<Geometry> geometryQueue;
	
	public float width, height;
	
	private int lastDCCount;
	private int lastDCTime;
	private int lastVertexCount;
	public Mouse mouse;
	public Keyboard keyboard;
	private Framebuffer frameBuffer = null;
	
	@Override
	public void ConstructWindow(int width, int height, String title) {
		long tp1, tp2;
		this.width = width;
		this.height = height;
		window = Window.Open(width, height, title, true);
		mouse = new Mouse(window);
		keyboard = new Keyboard(window);
		
		UI.Init(this, window);
		
		geometryQueue = new LinkedList<Geometry>();
		camera = new IdentityCamera();
		white = Texture2D.LoadJar("nutmeg/game/engine/res/textures/white.png");
		AudioManager.Init();
		shader = Shader.LoadJarSafe("nutmeg/game/engine/res/shaders/simple.vs", "nutmeg/game/engine/res/shaders/simple.fs");
		OnUserCreate();
		float tpf = 1;
		while(window.IsActive() && !Nutmeg.GetClose()) {
			Background(Color.gray);
			tp1 = System.currentTimeMillis();
			if(frameBuffer != null) { frameBuffer.Bind();}// Logger.Log("NMGE", "Application", "Rendering To FrameBuffer"); }
			OnUserUpdate(tpf);
			SubmitGeometry();
			OnUIRender();
			UI.Render();
			UI.Update(window, mouse, keyboard, 1);
			
			window.Update();
			tp2 = System.currentTimeMillis();
			tpf = (tp2 - tp1) / 1000f;
		}
		OnUserDestroy();
		AudioManager.Destroy();
		window.Destroy();
		Window.CloseGLFW();
		Date d = new Date(System.currentTimeMillis());
		IO.SaveStrings("log.txt", Nutmeg.GetOutputLog());
		IO.SaveStrings("err.txt", Nutmeg.GetErrorLog()) ;
	}
	
	@Override
	public void Background(Color color) {
		Renderer.Background(color);	
	}
	
	@Override
	public void SetRenderTarget(Framebuffer target) {
		frameBuffer = target;
	}
	
	@Override
	public void SetShader(Shader newShader) {
		shader.Delete();
		shader = newShader;
		shader.BindAttrib(0, "a_Position");
		shader.BindAttrib(0, "a_UV");
	}
	
	@Override
	public Texture2D GetRenderTexture() {
		if(frameBuffer != null) return frameBuffer.GetColorData();
		return null;
	}
	
	@Override
	public void SubmitGeometry() {
		long tp1, tp2;
		tp1 = System.currentTimeMillis();
		shader.Bind();
		lastDCCount = geometryQueue.size();
		//Logger.Log("NMGE", "Application", "Rendering "+geometryQueue.size()+" Objects...");
		while(!geometryQueue.isEmpty()) {
			Geometry geom = geometryQueue.poll();
			shader.SetUniformMat4("u_Transform", geom.GetTransform().GetTransformMatrix());
			shader.SetUniformMat4("u_Projection", camera.GetProjection(width, height));
			shader.SetUniformColor("u_TintColor", geom.GetTintColor());
			shader.SetUniformTexture("t_Albedo", geom.GetAlbedo(), 0, GL_TEXTURE0);
			shader.SetUniformTexture("t_Shading", geom.GetShading(), 1, GL_TEXTURE1);
			MeshRenderer.Render(geom.GetMesh());
			lastVertexCount += geom.GetMesh().GetVertexCount();
			geom.GetMesh().Delete();
		}
		if(frameBuffer != null) frameBuffer.Unbind();
		tp2 = System.currentTimeMillis();
		lastDCTime = (int) (tp2 - tp1);
	}
	
	@Override
	public void DrawImage(float x, float y, Texture2D texture) {
		geometryQueue.add(new Geometry(PrimitiveFactory.GenerateQuad(x, y, 1, 1), new Transform(), Color.white, texture, white));
	}
	
	public void DrawImageShaded(float x, float y, Texture2D texture, Texture2D shading) {
		geometryQueue.add(new Geometry(PrimitiveFactory.GenerateQuad(x, y, 1, 1), new Transform(), Color.white, texture, shading));
	}
	
	
	@Override
	public void DrawRect(float x, float y, Color color) {
		geometryQueue.add(new Geometry(PrimitiveFactory.GenerateQuad(x, y, 1, 1), new Transform(), color, white, white));
	}
	
	public int GetLastDrawCallLength() {
		return lastDCCount;
	}
	
	public int GetLastDrawCallTime() {
		return lastDCTime;
	}
	
	public int GetVertexCount() {
		int vc = lastVertexCount;
		lastVertexCount = 0;
		return vc;
	}
	
//	public String GetTitle() {
//		
//	}
}
