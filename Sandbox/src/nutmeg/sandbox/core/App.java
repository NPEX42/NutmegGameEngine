package nutmeg.sandbox.core;

import java.awt.Color;

import nutmeg.al.OpenAL;
import nutmeg.core.Logger;
import nutmeg.core.Nutmeg;
import nutmeg.game.engine.core.Application;
import nutmeg.game.engine.ecs.Mesh;
import nutmeg.game.engine.ecs.OrthoCamera;
import nutmeg.game.engine.ecs.Transform;
import nutmeg.game.engine.rendering.MeshRenderer;
import nutmeg.gl.core.*;

public class App extends Application {
	@Override
	public void OnUserCreate() {
		Nutmeg.DisableDebugMode();
		
		Logger.Log("SANDBOX", "App", "OpenGL Version: "+OpenGL.GetVersion());
		Logger.Log("SANDBOX", "App", "GLSL Version : "+OpenGL.GetShadingLanguageVersion());
		Logger.Log("SANDBOX", "App", "OpenGL Renderer: "+OpenGL.GetRenderer());
		Logger.Log("SANDBOX", "App", "Max OpenGL VAO Attribs: "+OpenGL.GetMaxVertexArrayAttributes());
		Logger.Log("SANDBOX", "App", "Max Textures: "+OpenGL.GetMaxTextureImageUnits());
		
		Logger.Log("SANDBOX", "App", "OpenAL Version: "+OpenAL.GetVersion());
		Logger.Log("SANDBOX", "App", "OpenAL Renderer: "+OpenAL.GetRenderer());
		Logger.Log("SANDBOX", "App", "OpenAL Vendor: "+OpenAL.GetVendor());
		Logger.Log("SANDBOX", "App", "OpenAL EXT: "+OpenAL.GetExtenstions());
		
		LoadTextureAsset("UV_Test.png", "testImage");
		SetCamera(new OrthoCamera(1, -1, new Transform()));
	}

	@Override
	public void OnUserUpdate() {
		Background(Color.BLUE);
		DrawTextureAsset(fWidth / 2, fHeight / 2, 512, -512, "testImage");
		if(keyboard.isKeyReleased('1')) Nutmeg.Close();
	}

	@Override
	public void OnUserDestroy() {
	}

}
