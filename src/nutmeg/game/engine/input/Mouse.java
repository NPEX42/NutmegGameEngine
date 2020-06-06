package nutmeg.game.engine.input;
import static org.lwjgl.glfw.GLFW.*;
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
		if(action == GLFW_RELEASE) {
			buttonStatesReleased[button] = true;
		}
	}
}

