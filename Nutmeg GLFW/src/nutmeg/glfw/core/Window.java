package nutmeg.glfw.core;

import org.lwjgl.opengl.GL;

public class Window {

	private long ID;

	public long GetID() {
		return ID;
	}
	
	public Window(long ID) {
		this.ID = ID;
	}
	
	public static Window Open(String title) {
		if(!GLFW_API.Init()) System.err.println("Unable To Init GLFW!");;
		long ID = GLFW_API.CreateWindow(1080, 720, title);
		Window temp = new Window(ID);
		GLFW_API.RegisterWindow(temp);
		temp.SetCurrent();
		GL.createCapabilities();
		temp.Show();
		
		System.err.println("Created Window #"+temp.GetID());
		
		return temp;
	}
	
	public boolean IsActive() {
		return !GLFW_API.WindowShouldClose(this);
	}
	
	public void Update() {
		GLFW_API.UpdateState(this);
	}
	
	public void Destroy() {
		GLFW_API.DestroyWindow(this);
	}
	
	public void Show() {
		GLFW_API.ShowWindow(this);
	}
	
	public void Hide() {
		GLFW_API.HideWindow(this);
	} 
	
	public void SetCurrent() {
		GLFW_API.SetOpenGLContext(this);
	}
	
}
