package nutmeg.game.engine.core;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;

import imgui.ImFontAtlas;
import imgui.ImFontConfig;
import imgui.ImGui;
import imgui.ImGuiFreeType;
import imgui.ImGuiIO;
import imgui.callbacks.ImStrConsumer;
import imgui.callbacks.ImStrSupplier;
import imgui.enums.ImGuiBackendFlags;
import imgui.enums.ImGuiConfigFlags;
import imgui.enums.ImGuiKey;
import imgui.enums.ImGuiMouseCursor;
import imgui.enums.ImGuiWindowFlags;
import nutmeg.core.Logger;
import nutmeg.core.Nutmeg;
public class Window {
	private long nWindowID;
	private ImGuiIO io;
	private float fWidth, fHeight;
	private boolean bIsFocused = true;
	private long[] mouseCursors;
	public Window(float _fWidth, float _fHeight, long _nWindowID) {
		nWindowID = _nWindowID;
		fWidth = _fWidth;
		fHeight = _fHeight;
		glfwSetWindowSizeCallback(nWindowID, this::OnResize);
		glfwSetWindowFocusCallback(nWindowID, this::OnFocusChanged);
	}
	public static Window Open(int _nWidth, int _nHeight, String _sTitle, boolean vsync) {
		if(!glfwInit()) return null;
		long _nID = glfwCreateWindow(_nWidth, _nHeight, _sTitle, 0, 0);
		glfwMakeContextCurrent(_nID);
		GL.createCapabilities();
		glfwShowWindow(_nID);
		glfwSwapInterval((vsync ? 1 : 0));
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
	
	public void Hide() {
		glfwHideWindow(nWindowID);
	}
	
	public void Update() {
		if(Nutmeg.GetDebug()) GLUtil.setupDebugMessageCallback();
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
		if(io != null)
			io.setDisplaySize(_nWidth, _nHeight);
		
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
	public void SetupImGUI(ImGuiIO io) {
		this.io = io;
		io.setDisplaySize(fWidth, fHeight);
		 io.setIniFilename(null); // We don't want to save .ini file
	        io.setConfigFlags(ImGuiConfigFlags.NavEnableKeyboard | ImGuiConfigFlags.DockingEnable | ImGuiWindowFlags.AlwaysAutoResize); // Navigation with keyboard and enabled docking
	        io.setBackendFlags(ImGuiBackendFlags.HasMouseCursors); // Mouse cursors to display while resizing windows etc.
	        io.setBackendPlatformName("imgui_java_impl_glfw");
	        
	        

	        // ------------------------------------------------------------
	        // Keyboard mapping. ImGui will use those indices to peek into the io.KeysDown[] array.
	        final int[] keyMap = new int[ImGuiKey.COUNT];
	        keyMap[ImGuiKey.Tab] = GLFW_KEY_TAB;
	        keyMap[ImGuiKey.LeftArrow] = GLFW_KEY_LEFT;
	        keyMap[ImGuiKey.RightArrow] = GLFW_KEY_RIGHT;
	        keyMap[ImGuiKey.UpArrow] = GLFW_KEY_UP;
	        keyMap[ImGuiKey.DownArrow] = GLFW_KEY_DOWN;
	        keyMap[ImGuiKey.PageUp] = GLFW_KEY_PAGE_UP;
	        keyMap[ImGuiKey.PageDown] = GLFW_KEY_PAGE_DOWN;
	        keyMap[ImGuiKey.Home] = GLFW_KEY_HOME;
	        keyMap[ImGuiKey.End] = GLFW_KEY_END;
	        keyMap[ImGuiKey.Insert] = GLFW_KEY_INSERT;
	        keyMap[ImGuiKey.Delete] = GLFW_KEY_DELETE;
	        keyMap[ImGuiKey.Backspace] = GLFW_KEY_BACKSPACE;
	        keyMap[ImGuiKey.Space] = GLFW_KEY_SPACE;
	        keyMap[ImGuiKey.Enter] = GLFW_KEY_ENTER;
	        keyMap[ImGuiKey.Escape] = GLFW_KEY_ESCAPE;
	        keyMap[ImGuiKey.KeyPadEnter] = GLFW_KEY_KP_ENTER;
	        keyMap[ImGuiKey.A] = GLFW_KEY_A;
	        keyMap[ImGuiKey.C] = GLFW_KEY_C;
	        keyMap[ImGuiKey.V] = GLFW_KEY_V;
	        keyMap[ImGuiKey.X] = GLFW_KEY_X;
	        keyMap[ImGuiKey.Y] = GLFW_KEY_Y;
	        keyMap[ImGuiKey.Z] = GLFW_KEY_Z;
	        io.setKeyMap(keyMap);

	        mouseCursors = new long[64];
			// ------------------------------------------------------------
	        // Mouse cursors mapping
	        mouseCursors[ImGuiMouseCursor.Arrow] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
	        mouseCursors[ImGuiMouseCursor.TextInput] = glfwCreateStandardCursor(GLFW_IBEAM_CURSOR);
	        mouseCursors[ImGuiMouseCursor.ResizeAll] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
	        mouseCursors[ImGuiMouseCursor.ResizeNS] = glfwCreateStandardCursor(GLFW_VRESIZE_CURSOR);
	        mouseCursors[ImGuiMouseCursor.ResizeEW] = glfwCreateStandardCursor(GLFW_HRESIZE_CURSOR);
	        mouseCursors[ImGuiMouseCursor.ResizeNESW] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
	        mouseCursors[ImGuiMouseCursor.ResizeNWSE] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
	        mouseCursors[ImGuiMouseCursor.Hand] = glfwCreateStandardCursor(GLFW_HAND_CURSOR);
	        mouseCursors[ImGuiMouseCursor.NotAllowed] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);

	        // ------------------------------------------------------------
	        // GLFW callbacks to handle user input

	        glfwSetKeyCallback(nWindowID, (w, key, scancode, action, mods) -> {
	            if (action == GLFW_PRESS) {
	                io.setKeysDown(key, true);
	            } else if (action == GLFW_RELEASE) {
	                io.setKeysDown(key, false);
	            }

	            io.setKeyCtrl(io.getKeysDown(GLFW_KEY_LEFT_CONTROL) || io.getKeysDown(GLFW_KEY_RIGHT_CONTROL));
	            io.setKeyShift(io.getKeysDown(GLFW_KEY_LEFT_SHIFT) || io.getKeysDown(GLFW_KEY_RIGHT_SHIFT));
	            io.setKeyAlt(io.getKeysDown(GLFW_KEY_LEFT_ALT) || io.getKeysDown(GLFW_KEY_RIGHT_ALT));
	            io.setKeySuper(io.getKeysDown(GLFW_KEY_LEFT_SUPER) || io.getKeysDown(GLFW_KEY_RIGHT_SUPER));
	        });

	        glfwSetCharCallback(nWindowID, (w, c) -> {
	            if (c != GLFW_KEY_DELETE) {
	                io.addInputCharacter(c);
	            }
	        });

	        glfwSetMouseButtonCallback(nWindowID, (w, button, action, mods) -> {
	        	Logger.Debug("NMGE", "Window::IMGUI", "Mouse Button Pressed! ("+button+")");
	            io.setMouseDown(0, button == GLFW_MOUSE_BUTTON_1 && action != GLFW_RELEASE);
	            io.setMouseDown(1, button == GLFW_MOUSE_BUTTON_2 && action != GLFW_RELEASE);
	            io.setMouseDown(2, button == GLFW_MOUSE_BUTTON_3 && action != GLFW_RELEASE);
	            io.setMouseDown(3, button == GLFW_MOUSE_BUTTON_4 && action != GLFW_RELEASE);
	            io.setMouseDown(4, button == GLFW_MOUSE_BUTTON_5 && action != GLFW_RELEASE);

	            if (!io.getWantCaptureMouse() && io.getMouseDown(1)) {
	                ImGui.setWindowFocus(null);
	            }
	        });

	        glfwSetScrollCallback(nWindowID, (w, xOffset, yOffset) -> {
	            io.setMouseWheelH(io.getMouseWheelH() + (float) xOffset);
	            io.setMouseWheel(io.getMouseWheel() + (float) yOffset);
	        });


	        io.setSetClipboardTextFn(new ImStrConsumer() {
	            @Override
	            public void accept(final String s) {
	                glfwSetClipboardString(nWindowID, s);
	            }
	        });
	        ImStrSupplier supplier = new ImStrSupplier() {
				
	        	@Override
	            public String get() {
	                final String clipboardString = glfwGetClipboardString(nWindowID);
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
	public void UpdateIMGUICursor(int imguiCursor) {
		glfwSetCursor(nWindowID, mouseCursors[imguiCursor]);
        glfwSetInputMode(nWindowID, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
	}
	
	
}
