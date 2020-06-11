package nutmeg.glfw.callbacks;

public interface IKeyCallback {
	public void OnKeyPressed (char key);
	public void OnKeyHeld    (char key);
	public void OnKeyReleased(char key);
}
