package nutmeg.glfw.core;
import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;

import imgui.ImFontAtlas;
import imgui.ImFontConfig;
import imgui.ImGuiFreeType;
import imgui.ImGuiIO;
import imgui.callbacks.ImStrConsumer;
import imgui.callbacks.ImStrSupplier;
import imgui.enums.ImGuiBackendFlags;
import imgui.enums.ImGuiConfigFlags;
import imgui.enums.ImGuiWindowFlags;
import nutmeg.core.datatypes.Pair;
import nutmeg.glfw.callbacks.ICharCallback;
import nutmeg.glfw.callbacks.ICloseCallback;
import nutmeg.glfw.callbacks.IKeyCallback;
import nutmeg.glfw.callbacks.IMouseCallback;
import nutmeg.glfw.callbacks.IResizeCallback;
public class GLFW_API {
	
	//Callback Managers
	private static ArrayList<Pair<Long, IResizeCallback>> resizeCallbacks; 
	private static ArrayList<Pair<Long, ICloseCallback>>  closeCallbacks ; 
	private static ArrayList<Pair<Long, IMouseCallback>>  mouseCallbacks ; 
	private static ArrayList<Pair<Long, IKeyCallback>>    keyCallbacks   ;
	private static ArrayList<Pair<Long, ICharCallback>>   charCallbacks  ;
	private static long[] mouseCursors;
	
	public static boolean Init() {
		resizeCallbacks = new ArrayList<Pair<Long,IResizeCallback>>();
		closeCallbacks  = new ArrayList<Pair<Long,ICloseCallback>> ();
		keyCallbacks    = new ArrayList<Pair<Long,IKeyCallback>>   ();
		mouseCallbacks  = new ArrayList<Pair<Long,IMouseCallback>> ();
		charCallbacks   = new ArrayList<Pair<Long,ICharCallback>>  ();
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
		RegisterWindowResize    (window);
		RegisterKeyEvent        (window);
		RegisterMouseEvent      (window);
		RegisterWindowClose     (window);
		RegisterCharEvent       (window);
		RegisterMouseButtonEvent(window);
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
	
	public static void RegisterMouseButtonEvent(Window window) {
		glfwSetMouseButtonCallback(window.GetID(), GLFW_API::DispatchMouseButtonEvent);
	}
	
	public static void RegisterCharEvent(Window window) {
		glfwSetCharCallback(window.GetID(), GLFW_API::DispatchCharEvent);
	}
	
	public static void AddResizeCallback(Window window, IResizeCallback callback) {
		System.err.println("Adding Window #"+window.GetID()+" to the Resize Listeners");
		resizeCallbacks.add(new Pair<Long, IResizeCallback>(window.GetID(), callback));
	}
	
	public static void AddCloseCallback(Window window, ICloseCallback callback) {
		System.err.println("Adding Window #"+window.GetID()+" to the Close Listeners");
		closeCallbacks.add(new Pair<Long, ICloseCallback>(window.GetID(), callback));
	}
	
	public static void AddMouseCallback(Window window, IMouseCallback callback) {
		System.err.println("Adding Window #"+window.GetID()+" to the Mouse Listeners");
		mouseCallbacks.add(new Pair<Long, IMouseCallback>(window.GetID(), callback));
	}
	
	public static void AddKeyCallback(Window window, IKeyCallback callback) {
		System.err.println("Adding Window #"+window.GetID()+" to the Key Listeners");
		keyCallbacks.add(new Pair<Long, IKeyCallback>(window.GetID(), callback));
	}
	
	public static void AddCharCallback(Window window, ICharCallback callback) {
		System.err.println("Adding Window #"+window.GetID()+" to the Char Listeners");
		charCallbacks.add(new Pair<Long, ICharCallback>(window.GetID(),callback));
	}
	
	public static void DispatchResizeEvent(long windowID, int width, int height) {
		for(Pair<Long, IResizeCallback> pair : resizeCallbacks) {
			if(pair.first.equals(windowID)) {
				//System.err.println("Window Match Found!");
				pair.second.OnResize(width, height);
			}
		}
	}
	
	public static void DispatchCloseEvent(long windowID) {
		for(Pair<Long, ICloseCallback> pair : closeCallbacks) {
			if(pair.first.equals(windowID)) {
				//System.err.println("Window Match Found!");
				pair.second.OnClose();
			}
		}
	}
	
	public static void DispatchMouseMovedEvent(long windowID, double x , double y) {
		//System.err.println("Dispatching Mouse Moved Event to Window #"+windowID+"... ("+x+","+y+")");
		for(Pair<Long, IMouseCallback> pair : mouseCallbacks) {
			//System.err.println("Window Match Found!");
			if(pair.first.equals(windowID)) {
				pair.second.OnMouseMoved(x, y);
			}
		}
	}
	
	public static void DispatchMouseButtonEvent(long windowID, int button, int action, int mods) {
		for(Pair<Long, IMouseCallback> pair : mouseCallbacks) {
			if(pair.first.equals(windowID)) {
				//System.err.println("Window Match Found!");
				switch(action) {
				case GLFW_PRESS:   pair.second.OnMousePressed(button) ; break;
				case GLFW_RELEASE: pair.second.OnMouseReleased(button); break;
				}
			}
		}
	}
	
	public static void DispatchKeyEvent(long windowID, int key, int scancode, int action, int mods) {
		for(Pair<Long, IKeyCallback> pair : keyCallbacks) {
			if(pair.first.longValue() == windowID) {
				//System.err.println("Window Match Found!");
				switch(action) {
				case GLFW_PRESS:   pair.second.OnKeyPressed ((char) key); break;
				case GLFW_REPEAT:  pair.second.OnKeyHeld    ((char) key); break;
				case GLFW_RELEASE: pair.second.OnKeyReleased((char) key); break;
				}
			}
		}
	}
	
	public static void DispatchCharEvent(long windowID, int c) {
		for(Pair<Long, ICharCallback> pair : charCallbacks) {
			//System.err.println("Window Match Found!");
			if(pair.first.equals(windowID)) {
				pair.second.OnCharTyped((char) c);
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

	public static void SetOpenGLContext(Window window) {
		glfwMakeContextCurrent(window.GetID());
	}

	public static void SetupImGUI(ImGuiIO io, Window window) {
		float fWidth = 1080;
		float fHeight = 720;
		long ID = window.GetID();
		io.setDisplaySize(fWidth, fHeight);
		 io.setIniFilename(null); // We don't want to save .ini file
	        io.setConfigFlags(ImGuiConfigFlags.NavEnableKeyboard | ImGuiConfigFlags.DockingEnable | ImGuiWindowFlags.AlwaysAutoResize); // Navigation with keyboard and enabled docking
	        io.setBackendFlags(ImGuiBackendFlags.HasMouseCursors); // Mouse cursors to display while resizing windows etc.
	        io.setBackendPlatformName("imgui_java_impl_glfw");
	        io.setSetClipboardTextFn(new ImStrConsumer() {
	            @Override
	            public void accept(final String s) {
	                glfwSetClipboardString(ID, s);
	            }
	        });
	        ImStrSupplier supplier = new ImStrSupplier() {
				
	        	@Override
	            public String get() {
	                final String clipboardString = glfwGetClipboardString(ID);
	                if (clipboardString != null) {
	                    return clipboardString;
	                } else {
	                    return "";
	                }
	            }
			};
	        io.setGetClipboardTextFn(supplier);
	        // ------------------------------------------------------------
	        // Fonts configuration
	        // Read: https://raw.githubusercontent.com/ocornut/imgui/master/docs/FONTS.txt

	        final ImFontAtlas fontAtlas = io.getFonts();
	        final ImFontConfig fontConfig = new ImFontConfig(); // Natively allocated object, should be explicitly destroyed

	        // Glyphs could be added per-font as well as per config used globally like here
	        fontConfig.setGlyphRanges(fontAtlas.getGlyphRangesCyrillic());

	        // Add a default font, which is 'ProggyClean.ttf, 13px'
	        fontAtlas.addFontDefault();

	        // Fonts merge example
	        fontConfig.setMergeMode(true); // When enabled, all fonts added with this config would be merged with the previously added font
	        fontConfig.setPixelSnapH(true);

	        //fontAtlas.addFontFromMemoryTTF(loadFromResources("basis33.ttf"), 16, fontConfig);

	        fontConfig.setMergeMode(false);
	        fontConfig.setPixelSnapH(false);

//	        // Fonts from file/memory example
//	        // We can add new fonts from the file system
//	        fontAtlas.addFontFromFileTTF("src/test/resources/Righteous-Regular.ttf", 14, fontConfig);
//	        fontAtlas.addFontFromFileTTF("src/test/resources/Righteous-Regular.ttf", 16, fontConfig);
//
//	        // Or directly from the memory
//	        fontConfig.setName("Roboto-Regular.ttf, 14px"); // This name will be displayed in Style Editor
//	        fontAtlas.addFontFromMemoryTTF(loadFromResources("Roboto-Regular.ttf"), 14, fontConfig);
//	        fontConfig.setName("Roboto-Regular.ttf, 16px"); // We can apply a new config value every time we add a new font
//	        fontAtlas.addFontFromMemoryTTF(loadFromResources("Roboto-Regular.ttf"), 16, fontConfig);

	        fontConfig.destroy(); // After all fonts were added we don't need this config more

	        // ------------------------------------------------------------
	        // Use freetype instead of stb_truetype to build a fonts texture
	        ImGuiFreeType.buildFontAtlas(fontAtlas, ImGuiFreeType.RasterizerFlags.LightHinting);
	}

	
	
	
}
