package nutmeg.game.engine.core;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;
import nutmeg.core.Logger;
import nutmeg.core.Nutmeg;
public class Window {
	private long nWindowID;
	private float fWidth, fHeight;
	private boolean bIsFocused = true;
	public Window(float _fWidth, float _fHeight, long _nWindowID) {
		nWindowID = _nWindowID;
		fWidth = _fWidth;
		fHeight = _fHeight;
		glfwSetWindowSizeCallback(nWindowID, this::OnResize);
		glfwSetWindowFocusCallback(nWindowID, this::OnFocusChanged);
	}
	public static Window Open(int _nWidth, int _nHeight, String _sTitle, boolean _bEnableDebug) {
		if(!glfwInit()) return null;
		long _nID = glfwCreateWindow(_nWidth, _nHeight, _sTitle, 0, 0);
		glfwMakeContextCurrent(_nID);
		GL.createCapabilities();
		glfwShowWindow(_nID);
		glfwSwapInterval((_bEnableDebug ? 1 : 0));
		return new Window(_nWidth, _nHeight, _nID);
	}
	
	public void MakeCurrent() {
		glfwMakeContextCurrent(nWindowID);
	}
	
	public boolean IsActive() {
		return !glfwWindowShouldClose(nWindowID);
	}
	
	public boolean IsFocused() {
		return bIsFocused;
	}
	
	public void RequestClose() {
		glfwSetWindowShouldClose(nWindowID, true);
	}
	
	public void Update() {
		if(Nutmeg.bDebugMode) GLUtil.setupDebugMessageCallback();
		glfwPollEvents();
		glfwSwapBuffers(nWindowID);
	}
	
	public void Destroy() {
		glfwDestroyWindow(nWindowID);
	}
	
	public void SetTitle(String _sTitle) {
		glfwSetWindowTitle(nWindowID, _sTitle);
	}
	
	public static void CloseGLFW() {
		glfwTerminate();
	}
	public void OnResize(long _nWindowID, int _nWidth, int _nHeight) {
		Logger.Log("NMGE", "Window", "Window Resized to "+_nWidth+"x"+_nHeight);
		
		fWidth = _nWidth;
		fHeight = _nHeight;
		
		glViewport(0, 0, _nWidth, _nHeight);
	}
	
	public void OnFocusChanged(long _nWindowID, boolean _bFocused) {
		Logger.Log("NMGE", "Window", "Window Focus Changed To: "+_bFocused);
		bIsFocused = _bFocused;
	}
	public float getfWidth() {
		return fWidth;
	}
	public float getfHeight() {
		return fHeight;
	}
	public long GetWindowID() {
		return nWindowID;
	}
	
	
}
