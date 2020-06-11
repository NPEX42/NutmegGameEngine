package nutmeg.glfw.callbacks;

public interface IMouseCallback {
	public void OnMouseMoved   (double x, double y);
	public void OnMousePressed (int button        );
	public void OnMouseReleased(int button        );
	
	public void OnScroll(double xOff, double yOff);
}
