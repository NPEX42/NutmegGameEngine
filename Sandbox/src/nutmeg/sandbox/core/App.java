package nutmeg.sandbox.core;

import java.awt.Color;
import java.util.Date;

import javax.swing.JFileChooser;

import nutmeg.al.OpenAL;
import nutmeg.core.Logger;
import nutmeg.core.Nutmeg;
import nutmeg.game.engine.core.Application;
import nutmeg.game.engine.ecs.OrthoCamera;
import nutmeg.game.engine.ecs.Transform;
import nutmeg.game.engine.ui.Bool;
import nutmeg.game.engine.ui.UI;
import nutmeg.gl.core.*;

public class App extends Application {
	
	public float[] xPos = new float[1];
	public float[] yPos = new float[1];
	
	public float[] viewPortScale = new float[1];
	
	public int[] squareCount = new int[1];
	
	int fps;
	
	Texture2D albedo;
	Texture2D shading;
	
	Bool flip;
	
	float[] fpsHistory = new float[600];
	int idx;
	
	@Override
	public void OnUserCreate() {
		
		SetRenderTarget(Framebuffer.Create(width, height));
		viewPortScale[0] = 1;
		//Nutmeg.EnableDebugMode();
		albedo = Texture2D.LoadJar("nutmeg/game/engine/res/textures/white.png");
		shading = Texture2D.LoadJar("nutmeg/game/engine/res/textures/white.png");
		flip = new Bool();
		//Nutmeg.SetOutputLog("Output-"+new Date(System.currentTimeMillis()).toString()+".txt");
		Logger.Log("SANDBOX", "App", "OpenGL Version: "+OpenGL.GetVersion());
		Logger.Log("SANDBOX", "App", "GLSL Version : "+OpenGL.GetShadingLanguageVersion());
		Logger.Log("SANDBOX", "App", "OpenGL Renderer: "+OpenGL.GetRenderer());
		Logger.Log("SANDBOX", "App", "Max OpenGL VAO Attribs: "+OpenGL.GetMaxVertexArrayAttributes());
		Logger.Log("SANDBOX", "App", "Max Textures: "+OpenGL.GetMaxTextureImageUnits());
		
		Logger.Log("SANDBOX", "App", "OpenAL Version: "+OpenAL.GetVersion());
		Logger.Log("SANDBOX", "App", "OpenAL Renderer: "+OpenAL.GetRenderer());
		Logger.Log("SANDBOX", "App", "OpenAL Vendor: "+OpenAL.GetVendor());
		Logger.Log("SANDBOX", "App", "OpenAL EXT: "+OpenAL.GetExtenstions());
	}

	@Override
	public void OnUserUpdate(float fElapsedTime) {
		Background(Color.BLUE);
		for(int i = 0; i < squareCount[0]; i++)
			DrawImageShaded(xPos[0], yPos[0], albedo, shading);
		
		fps = (int) (1 / fElapsedTime);
		
		fpsHistory[idx++] = fps;
		idx %= fpsHistory.length;
		
		if(fps < 50) {
			Logger.Warn("Sandbox", "App", "FPS is Dropping! ("+fps+")");
		}
	}

	@Override
	public void OnUserDestroy() {
		
	}
	
	@Override
	public void OnUIRender() {
		
		UI.NewFrame();
		UI.ShowDemoWindow();
		UI.Begin("Controls / Info");
		UI.SliderFloat("Position X", xPos, -1, 1);
		UI.SliderFloat("Position Y", yPos, -1, 1);
		UI.SliderInt("Num Of Squares", squareCount, 0, 1000);
		UI.SliderFloat("Viewport Scale", viewPortScale, 0, 5);
		UI.Text("Objects Rendered Last Frame: "+GetLastDrawCallLength());
		UI.Text("Time to Complete Rendering: "+GetLastDrawCallTime());
		UI.Text("Vertices: "+GetVertexCount());
		UI.Text("FPS: "+fps);
		UI.Checkbox("Flip UV Y?", flip);
		UI.SameLine();
		if(UI.Button("Load Albedo...")) {
			JFileChooser jfc = new JFileChooser();
			jfc.showOpenDialog(null);
			if(jfc.getSelectedFile() != null) {
				albedo.Delete();
				albedo = Texture2D.Load(jfc.getSelectedFile().getAbsolutePath(), flip.GetValue());
			}
		}
		
		
		UI.SameLine();
		UI.Image(albedo, albedo.GetWidth()  * 0.5f, albedo.GetHeight() * 0.5f);
		
		if(UI.Button("Load Shading Map...")) {
			JFileChooser jfc = new JFileChooser();
			jfc.showOpenDialog(null);
			if(jfc.getSelectedFile() != null) {
				shading.Delete();
				shading = Texture2D.Load(jfc.getSelectedFile().getAbsolutePath(), flip.GetValue());
			}
		}
		UI.SameLine();
		UI.Image(shading, shading.GetWidth()  * 0.5f, shading.GetHeight()  * 0.5f);
		UI.HistogramFloat("Framerate History", fpsHistory, 0, "", 0, 60, 300, 60);
		
		if(UI.Button("Reload Shaders...")) {
			Logger.Log("Sandbox", "App", "Reloading Shaders...");
			SetShader(Shader.LoadJarSafe("nutmeg/game/engine/res/shaders/simple.vs", "nutmeg/game/engine/res/shaders/simple.fs"));
		}
		
		UI.Begin("Output Log");
			for(String line : Nutmeg.GetOutputLog()) UI.TextColored(line, Color.WHITE);
		UI.End();
		
		UI.Begin("Error Log");
			for(String line : Nutmeg.GetErrorLog()) UI.TextColored(line,Color.red);
		UI.End();
		
		UI.Begin("Warning Log");
			for(String line : Nutmeg.GetWarningLog()) UI.TextColored(line,Color.yellow);
		UI.End();
		
		UI.ViewPort("Viewport - View 1", GetRenderTexture(), viewPortScale[0]);
		UI.End();
	}

}
