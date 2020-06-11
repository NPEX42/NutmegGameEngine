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
	private static ArrayList<Pair<Long, IResizeCallback>> resizeCallbacks; 
	private static ArrayList<Pair<Long, ICloseCallback>>  closeCallbacks; 
	private static ArrayList<Pair<Long, IMouseCallback>>  mouseCallbacks; 
	private static ArrayList<Pair<Long, IKeyCallback>>    keyCallbacks;
	
	public static boolean Init() {
		resizeCallbacks = new ArrayList<Pair<Long,IResizeCallback>>();
		closeCallbacks  = new ArrayList<Pair<Long,ICloseCallback>> ();
		keyCallbacks    = new ArrayList<Pair<Long,IKeyCallback>>   ();
		mouseCallbacks  = new ArrayList<Pair<Long,IMouseCallback>> ();
		return glfwInit();
	}
	
	public static void Terminate() {
		glfwTerminate();
	}
	
	public static boolean WindowShouldClose(Window window) {
		return glfwWindowShouldClose(window.GetID());
	}
	
	public static void UpdateState(Window window) {
		glfwPollEvents();
		glfwSwapBuffers(window.GetID());
	}
	
	public static void RegisterWindow(Window window) {
		RegisterWindowResize(window);
		RegisterKeyEvent    (window);
		RegisterMouseEvent  (window);
		RegisterWindowClose (window);
	}
	
	public static long CreateWindow(int width, int height, String title) {
		long ID = glfwCreateWindow(width, height, title, 0, 0);
		glfwShowWindow(ID);
		return ID;
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
		resizeCallbacks.add(new Pair<Long, IResizeCallback>(window.GetID(), callback));
	}
	
	public static void AddCloseCallback(Window window, ICloseCallback callback) {
		closeCallbacks.add(new Pair<Long, ICloseCallback>(window.GetID(), callback));
	}
	
	public static void AddMouseCallback(Window window, IMouseCallback callback) {
		mouseCallbacks.add(new Pair<Long, IMouseCallback>(window.GetID(), callback));
	}
	
	public static void AddKeyCallback(Window window, IKeyCallback callback) {
		keyCallbacks.add(new Pair<Long, IKeyCallback>(window.GetID(), callback));
	}
	
	public static void DispatchResizeEvent(long windowID, int width, int height) {
		for(Pair<Long, IResizeCallback> pair : resizeCallbacks) {
			if(pair.first == windowID) {
				pair.second.OnResize(width, height);
			}
		}
	}
	
	public static void DispatchCloseEvent(long windowID) {
		for(Pair<Long, ICloseCallback> pair : closeCallbacks) {
			if(pair.first == windowID) {
				pair.second.OnClose();
			}
		}
	}
	
	public static void DispatchMouseMovedEvent(long windowID, double x , double y) {
		for(Pair<Long, IMouseCallback> pair : mouseCallbacks) {
			if(pair.first == windowID) {
				pair.second.OnMouseMoved(x, y);
			}
		}
	}
	
	public static void DispatchMouseButtonEvent(long windowID, int button, int action, int mods) {
		for(Pair<Long, IMouseCallback> pair : mouseCallbacks) {
			if(pair.first == windowID) {
				switch(action) {
				case GLFW_PRESS:   pair.second.OnMousePressed(button) ;
				case GLFW_RELEASE: pair.second.OnMouseReleased(button);
				}
			}
		}
	}
	
	public static void DispatchKeyEvent(long windowID, int key, int scancode, int action, int mods) {
		for(Pair<Long, IKeyCallback> pair : keyCallbacks) {
			if(pair.first == windowID) {
				switch(action) {
				case GLFW_PRESS:   pair.second.OnKeyPressed ((char) key);
				case GLFW_REPEAT:  pair.second.OnKeyHeld    ((char) key);
				case GLFW_RELEASE: pair.second.OnKeyReleased((char) key);
				}
			}
		}
	}

	public static void DestroyWindow(Window window) {
		glfwDestroyWindow(window.GetID());
	}

	public static void ShowWindow(Window window) {
		glfwShowWindow(window.GetID());
	}

	public static void HideWindow(Window window) {
		glfwHideWindow(window.GetID());
	}
	
	
}
