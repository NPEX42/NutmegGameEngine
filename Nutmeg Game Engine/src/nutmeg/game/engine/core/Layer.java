package nutmeg.game.engine.core;

import nutmeg.glfw.callbacks.ICharCallback;
import nutmeg.glfw.callbacks.IKeyCallback;
import nutmeg.glfw.callbacks.IMouseCallback;
import nutmeg.glfw.core.GLFW_API;
import nutmeg.glfw.core.Window;

public abstract class Layer implements IKeyCallback, IMouseCallback, ICharCallback {
	public void SetHostWindow(Window host) {
		System.err.println("Setting Host Window");
		GLFW_API.AddKeyCallback  (host, this);
		GLFW_API.AddMouseCallback(host, this);
		GLFW_API.AddCharCallback(host, this);
	}
	
	public abstract void OnAttach ();
	public abstract void OnRender ();
	public abstract void OnDestroy();
	
	public abstract void OnIMGuiRender();
	
	
}
