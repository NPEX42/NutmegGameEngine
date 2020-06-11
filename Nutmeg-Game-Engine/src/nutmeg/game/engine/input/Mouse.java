package nutmeg.game.engine.input;
import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector2f;

import imgui.ImGui;
import imgui.ImGuiIO;
import nutmeg.core.Logger;
import nutmeg.game.engine.core.Window;
public class Mouse {
	private Window window;
	private boolean[] buttonStatesReleased = new boolean[3];
	public Mouse(Window window) {
		super();
		this.window = window;
		glfwSetMouseButtonCallback(window.GetWindowID(), this::OnMouseButtonEvent);
	}

	public boolean IsButtonDown(int button) {
		glfwPollEvents();
		return glfwGetMouseButton(window.GetWindowID(), button) == GLFW_PRESS;
	}
	
	public boolean IsButtonReleased(int button) {
		boolean state = buttonStatesReleased[button];
		buttonStatesReleased[button] = false;
		return state;
	}
	
	public Vector2f GetMousePosition() {
		return new Vector2f(GetMouseX(), GetMouseY());
	}
	
	public int GetMouseX() {
		glfwPollEvents();
		double[] x = new double[1], y = new double[1];
		glfwGetCursorPos(window.GetWindowID(), x, y);
		return (int) x[0];
	}
	
	public int GetMouseY() {
		glfwPollEvents();
		double[] x = new double[1], y = new double[1];
		glfwGetCursorPos(window.GetWindowID(), x, y);
		return (int) y[0];
	}
	
	int oldX, oldY;
	public int GetMouseDY() {
		glfwPollEvents();
		double[] x = new double[1], y = new double[1];
		glfwGetCursorPos(window.GetWindowID(), x, y);
		int DY = (int) y[0] - oldY;
		oldY = (int) y[0];
		return DY;
	}
	
	public int GetMouseDX() {
		glfwPollEvents();
		double[] x = new double[1], y = new double[1];
		glfwGetCursorPos(window.GetWindowID(), x, y);
		int DX = (int) x[0] - oldX;
		oldX = (int) x[0];
		return DX;
	}
	
	public void OnMouseButtonEvent(long window, int button, int action, int modifier) {
		ImGuiIO io = ImGui.getIO();
		Logger.Debug("NMGE", "Keyboard::IMGUI", "Mouse Button Pressed! ("+button+")");
        io.setMouseDown(0, button == GLFW_MOUSE_BUTTON_1 && action != GLFW_RELEASE);
        io.setMouseDown(1, button == GLFW_MOUSE_BUTTON_2 && action != GLFW_RELEASE);
        io.setMouseDown(2, button == GLFW_MOUSE_BUTTON_3 && action != GLFW_RELEASE);
        io.setMouseDown(3, button == GLFW_MOUSE_BUTTON_4 && action != GLFW_RELEASE);
        io.setMouseDown(4, button == GLFW_MOUSE_BUTTON_5 && action != GLFW_RELEASE);

        if (!io.getWantCaptureMouse() && io.getMouseDown(1)) {
            ImGui.setWindowFocus(null);
        }
		
		if(action == GLFW_RELEASE) {
			buttonStatesReleased[button] = true;
		}
	}
}

