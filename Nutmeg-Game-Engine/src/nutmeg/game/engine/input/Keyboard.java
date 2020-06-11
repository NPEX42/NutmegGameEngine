package nutmeg.game.engine.input;
import static org.lwjgl.glfw.GLFW.*;
import nutmeg.core.Logger;
import nutmeg.game.engine.core.Window;
public class Keyboard {
	private boolean[] keyStatesReleased = new boolean[65536]; 
	public Keyboard(Window window) {
		super();
		this.window = window;
		
		glfwSetKeyCallback(window.GetWindowID(), this::OnKeyEvent);
	}

	Window window;
	public boolean isKeyPressed(char key) {
		return glfwGetKey(window.GetWindowID(), key) == GLFW_PRESS;
	}
	
	public boolean isKeyReleased(char key) {
		boolean state = keyStatesReleased[key];
		keyStatesReleased[key] = false;
		return state;
	}
	
	private void OnKeyEvent(long windowID, int key, int scancode, int action, int modifiers) {
		if(key < keyStatesReleased.length && action == GLFW_RELEASE) {
			Logger.Debug("NMGE","Keyboard","Key '"+(char)key+"' Has been released...");
			keyStatesReleased[key] = true;
		} else if(key < keyStatesReleased.length) {
			keyStatesReleased[key] = false;
		}
	}
}

