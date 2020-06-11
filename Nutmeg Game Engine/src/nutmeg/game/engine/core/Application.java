package nutmeg.game.engine.core;

import java.util.ArrayList;

import nutmeg.glfw.core.GLFW_API;
import nutmeg.glfw.core.Window;

public abstract class Application {
	private Window host;
	private ArrayList<Layer> layers;
	public void CreateApp(String title) {
		host = Window.Open(title);
		layers = new ArrayList<Layer>();
		
		Setup();
	}
	
	public void AddLayer(Layer layer) {
		layers.add(layer);
		layer.SetHostWindow(host);
	}
	
	public void Run() {
		for(Layer layer : layers) layer.OnAttach();
		while(host.IsActive()) {
			for(Layer layer : layers) layer.OnRender();
			host.Update();
		}
		for(Layer layer : layers) layer.OnDestroy();
		host.Destroy();
		GLFW_API.Terminate();
	}
	
	public abstract void Setup();
}
