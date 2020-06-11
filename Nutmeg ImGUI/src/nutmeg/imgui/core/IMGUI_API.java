package nutmeg.imgui.core;

import static org.lwjgl.glfw.GLFW.GLFW_ARROW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_HAND_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_HRESIZE_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_IBEAM_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_C;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DELETE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_END;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_HOME;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_INSERT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_ALT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SUPER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PAGE_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PAGE_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_ALT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_SUPER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_TAB;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_V;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Y;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Z;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_2;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_3;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_4;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_5;
import static org.lwjgl.glfw.GLFW.GLFW_VRESIZE_CURSOR;
import static org.lwjgl.glfw.GLFW.glfwCreateStandardCursor;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.enums.ImGuiKey;
import imgui.enums.ImGuiMouseCursor;
import imgui.gl3.ImGuiImplGl3;
import nutmeg.glfw.callbacks.ICharCallback;
import nutmeg.glfw.callbacks.IKeyCallback;
import nutmeg.glfw.callbacks.IMouseCallback;
import nutmeg.glfw.callbacks.IResizeCallback;
import nutmeg.glfw.core.GLFW_API;
import nutmeg.glfw.core.Window;

public class IMGUI_API implements ICharCallback, IMouseCallback, IKeyCallback, IResizeCallback {
	private static ImGuiImplGl3 imGuiGL3;
	private static ImGuiIO io;
	private static long[] mouseCursors;
	public void Init(int width, int height, Window window) {
	        // This line is critical for Dear ImGui to work.
	        ImGui.createContext();
	        ImGui.setNextWindowSize(width, height);
	        //ImGui.setNextWindowPos(0, 0);
	        GLFW_API.SetupImGUI(ImGui.getIO(), window);
	        io = ImGui.getIO();
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
	        
	        io = ImGui.getIO();
	        ImGui.styleColorsDark();
	        imGuiGL3 = new ImGuiImplGl3();
			// Method initializes LWJGL3 renderer.
	        // This method SHOULD be called after you've initialized your ImGui configuration (fonts and so on).
	        // ImGui context should be created as well.
	        imGuiGL3.init("#version 330 core");
	        
	        GLFW_API.AddKeyCallback(window, this);
	        GLFW_API.AddMouseCallback(window, this);
	        GLFW_API.AddCharCallback(window, this);
	        GLFW_API.AddResizeCallback(window, this);
	}
	
	public void Draw() {
		imGuiGL3.render(ImGui.getDrawData());
	}
	
	@Override
	public void OnCharTyped(char character) {
		// TODO Auto-generated method stub
		if (character != GLFW_KEY_DELETE) {
            io.addInputCharacter(character);
        }
	}
	@Override
	public void OnMouseMoved(double x, double y) {
		io.setMousePos((float)x, (float)y);
	}
	@Override
	public void OnMousePressed(int button) {
		// TODO Auto-generated method stub
		//Logger.Debug("NMGE", "Window::IMGUI", "Mouse Button Pressed! ("+button+")");
        io.setMouseDown(0, button == GLFW_MOUSE_BUTTON_1 && true);
        io.setMouseDown(1, button == GLFW_MOUSE_BUTTON_2 && true);
        io.setMouseDown(2, button == GLFW_MOUSE_BUTTON_3 && true);
        io.setMouseDown(3, button == GLFW_MOUSE_BUTTON_4 && true);
        io.setMouseDown(4, button == GLFW_MOUSE_BUTTON_5 && true);
        System.out.println("IMGUI_API Mouse Pressed");
        if (!io.getWantCaptureMouse() && io.getMouseDown(1)) {
            ImGui.setWindowFocus(null);
        }
	}
	@Override
	public void OnMouseReleased(int button) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				//Logger.Debug("NMGE", "Window::IMGUI", "Mouse Button Pressed! ("+button+")");
		        io.setMouseDown(0, button == GLFW_MOUSE_BUTTON_1 && false);
		        io.setMouseDown(1, button == GLFW_MOUSE_BUTTON_2 && false);
		        io.setMouseDown(2, button == GLFW_MOUSE_BUTTON_3 && false);
		        io.setMouseDown(3, button == GLFW_MOUSE_BUTTON_4 && false);
		        io.setMouseDown(4, button == GLFW_MOUSE_BUTTON_5 && false);

		        if (!io.getWantCaptureMouse() && io.getMouseDown(1)) {
		            ImGui.setWindowFocus(null);
		        }
	}
	@Override
	public void OnKeyPressed(char key) {
		// TODO Auto-generated method stub
		 io.setKeysDown(key, true);
		 
		 io.setKeyCtrl(io.getKeysDown(GLFW_KEY_LEFT_CONTROL) || io.getKeysDown(GLFW_KEY_RIGHT_CONTROL));
         io.setKeyShift(io.getKeysDown(GLFW_KEY_LEFT_SHIFT) || io.getKeysDown(GLFW_KEY_RIGHT_SHIFT));
         io.setKeyAlt(io.getKeysDown(GLFW_KEY_LEFT_ALT) || io.getKeysDown(GLFW_KEY_RIGHT_ALT));
         io.setKeySuper(io.getKeysDown(GLFW_KEY_LEFT_SUPER) || io.getKeysDown(GLFW_KEY_RIGHT_SUPER));
	}
	@Override
	public void OnKeyHeld(char key) {
		
	}
	@Override
	public void OnKeyReleased(char key) {
		// TODO Auto-generated method stub
		io.setKeysDown(key, false);
		
		io.setKeyCtrl(io.getKeysDown(GLFW_KEY_LEFT_CONTROL) || io.getKeysDown(GLFW_KEY_RIGHT_CONTROL));
        io.setKeyShift(io.getKeysDown(GLFW_KEY_LEFT_SHIFT) || io.getKeysDown(GLFW_KEY_RIGHT_SHIFT));
        io.setKeyAlt(io.getKeysDown(GLFW_KEY_LEFT_ALT) || io.getKeysDown(GLFW_KEY_RIGHT_ALT));
        io.setKeySuper(io.getKeysDown(GLFW_KEY_LEFT_SUPER) || io.getKeysDown(GLFW_KEY_RIGHT_SUPER));
	}
	@Override
	public void OnScroll(double xOff, double yOff) {
		io.setMouseWheelH(io.getMouseWheelH() + (float) xOff);
		io.setMouseWheel (io.getMouseWheel () + (float) yOff);
	}

	@Override
	public void OnResize(int newWidth, int newHeight) {
		io.setDisplaySize(newWidth, newHeight);
	}
	
	
}
