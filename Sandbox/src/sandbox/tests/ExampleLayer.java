package sandbox.tests;

import java.awt.Color;

import nutmeg.game.engine.core.Layer;
import nutmeg.game.engine.rendering.Renderer;
import nutmeg.glfw.core.Window;

public class ExampleLayer extends Layer {

	@Override
	public void OnKeyPressed(char key) {
		// TODO Auto-generated method stub
		System.err.println("Key Pressed: "+key);
	}

	@Override
	public void OnKeyHeld(char key) {
		// TODO Auto-generated method stub
		System.err.println("Key Held: "+key);
	}

	@Override
	public void OnKeyReleased(char key) {
		// TODO Auto-generated method stub
		System.err.println("Key Released: "+key);
	}

	@Override
	public void OnMouseMoved(double x, double y) {
		// TODO Auto-generated method stub
		System.err.println("Mouse Moved: "+x+","+y);
	}

	@Override
	public void OnMousePressed(int button) {
		// TODO Auto-generated method stub
		System.err.println("Mouse Pressed: "+button);
	}

	@Override
	public void OnMouseReleased(int button) {
		// TODO Auto-generated method stub
		System.err.println("Mouse Released: "+button);
	}

	@Override
	public void OnAttach() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnRender() {
		// TODO Auto-generated method stub
		Renderer.ClearColor(Color.BLUE);
	}

	@Override
	public void OnDestroy() {
		// TODO Auto-generated method stub
		
	}

}
