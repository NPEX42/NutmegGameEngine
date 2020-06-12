package nutmeg.game.engine.core;

import java.util.ArrayList;

import nutmeg.glfw.core.GLFW_API;
import nutmeg.glfw.core.Window;
import nutmeg.imgui.core.IMGUI_API;
import nutmeg.imgui.core.UI;

public abstract class Application {
	private Window host;
	private ArrayList<Layer> layers;
	private IMGUI_API imgui_api;
	public static boolean closeRequested;
	public void CreateApp(String title) {
		host = Window.Open(title);
		layers = new ArrayList<Layer>();
		imgui_api = new IMGUI_API();
		imgui_api.Init(1080, 720, host);
		Setup();
	}
	
	public void AddLayer(Layer layer) {
		layers.add(layer);
		layer.SetHostWindow(host);
	}
	
	public void Run() {
		for(Layer layer : layers) layer.OnAttach();
		long tp1, tp2;
		float ts = 1.0f;
		while(host.IsActive() && !closeRequested) {
			tp1 = System.currentTimeMillis();
			for(Layer layer : layers) layer.OnRender(ts);
			for(Layer layer : layers) {
				UI.NewFrame();
				layer.OnIMGuiRender();
				UI.Render();
			}
			UI.Update(host);
			imgui_api.Draw();
			host.Update();
			tp2 = System.currentTimeMillis();
			ts = (tp2 - tp1) / 1000f;
		}
		for(Layer layer : layers) layer.OnDestroy();
		host.Destroy();
		GLFW_API.Terminate();
	}
	
	public abstract void Setup();
}
