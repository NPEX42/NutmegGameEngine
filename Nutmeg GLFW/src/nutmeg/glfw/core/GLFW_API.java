package nutmeg.glfw.core;
import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;

import nutmeg.core.Pair;
import nutmeg.glfw.callbacks.ICloseCallback;
import nutmeg.glfw.callbacks.IKeyCallback;
import nutmeg.glfw.callbacks.IMouseCallback;
import nutmeg.glfw.callbacks.IResizeCallback;
public class GLFW_API {
	
	//Callback Managers
	private static ArrayList<Pair<Integer, IResizeCallback>> resizeCallbacks; 
	private static ArrayList<Pair<Integer, ICloseCallback>>  closeCallbacks; 
	private static ArrayList<Pair<Integer, IMouseCallback>>  mouseCallbacks; 
	private static ArrayList<Pair<Integer, IKeyCallback>>    keyCallbacks;
	
	public static boolean Init() {
		resizeCallbacks = new ArrayList<Pair<Integer,IResizeCallback>>();
		closeCallbacks  = new ArrayList<Pair<Integer,ICloseCallback>> ();
		keyCallbacks    = new ArrayList<Pair<Integer,IKeyCallback>>   ();
		mouseCallbacks  = new ArrayList<Pair<Integer,IMouseCallback>> ();
		return glfwInit();
	}
	
	public static void Terminate() {
		glfwTerminate();
	}
	
	public static void RegisterWindow(Window window) {
		RegisterWindowResize(window);
		RegisterKeyEvent    (window);
		RegisterMouseEvent  (window);
		RegisterWindowClose (window);
	}
	
	public static void RegisterWindowResize(Window window) {
		glfwSetWindowSizeCallback(window.GetID(), GLFW_API::DispatchResizeEvent);
	}
	
	public static void RegisterWindowClose(Window window) {
		glfwSetWindowCloseCallback(window.GetID(), GLFW_API::DispatchCloseEvent);
	}
	
	public static void RegisterKeyEvent(Window window) {
		glfwSetKeyCallback(window.GetID(), GLFW_API::DispatchKeyEvent);
	}
	
	public static void RegisterMouseEvent(Window window) {
		glfwSetCursorPosCallback(window.GetID(), GLFW_API::DispatchMouseMovedEvent);
	}
	
	public static void AddResizeCallback(Window window, IResizeCallback callback) {
		resizeCallbacks.add(new Pair<Integer, IResizeCallback>(window.GetID(), callback));
	}
	
	public static void AddCloseCallback(Window window, ICloseCallback callback) {
		closeCallbacks.add(new Pair<Integer, ICloseCallback>(window.GetID(), callback));
	}
	
	public static void AddMouseCallback(Window window, IMouseCallback callback) {
		mouseCallbacks.add(new Pair<Integer, IMouseCallback>(window.GetID(), callback));
	}
	
	public static void AddKeyCallback(Window window, IKeyCallback callback) {
		keyCallbacks.add(new Pair<Integer, IKeyCallback>(window.GetID(), callback));
	}
	
	public static void DispatchResizeEvent(long windowID, int width, int height) {
		for(Pair<Integer, IResizeCallback> pair : resizeCallbacks) {
			if(pair.first == windowID) {
				pair.second.OnResize(width, height);
			}
		}
	}
	
	public static void DispatchCloseEvent(long windowID) {
		for(Pair<Integer, ICloseCallback> pair : closeCallbacks) {
			if(pair.first == windowID) {
				pair.second.OnClose();
			}
		}
	}
	
	public static void DispatchMouseMovedEvent(long windowID, double x , double y) {
		for(Pair<Integer, IMouseCallback> pair : mouseCallbacks) {
			if(pair.first == windowID) {
				pair.second.OnMouseMoved(x, y);
			}
		}
	}
	
	public static void DispatchMouseButtonEvent(long windowID, int button, int action, int mods) {
		for(Pair<Integer, IMouseCallback> pair : mouseCallbacks) {
			if(pair.first == windowID) {
				switch(action) {
				case GLFW_PRESS:   pair.second.OnMousePressed(button) ;
				case GLFW_RELEASE: pair.second.OnMouseReleased(button);
				}
			}
		}
	}
	
	public static void DispatchKeyEvent(long windowID, int key, int scancode, int action, int mods) {
		for(Pair<Integer, IKeyCallback> pair : keyCallbacks) {
			if(pair.first == windowID) {
				switch(action) {
				case GLFW_PRESS:   pair.second.OnKeyPressed ((char) key);
				case GLFW_REPEAT:  pair.second.OnKeyHeld    ((char) key);
				case GLFW_RELEASE: pair.second.OnKeyReleased((char) key);
				}
			}
		}
	}
	
	
}
