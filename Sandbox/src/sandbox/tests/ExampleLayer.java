package sandbox.tests;

import java.awt.Color;

import nutmeg.core.datatypes.BoolPtr;
import nutmeg.core.datatypes.IntPtr;
import nutmeg.game.engine.core.Application;
import nutmeg.game.engine.core.Layer;
import nutmeg.game.engine.rendering.Renderer;
import nutmeg.imgui.core.UI;

public class ExampleLayer extends Layer {
	BoolPtr close = new BoolPtr();
	@Override
	public void OnKeyPressed(char key) {
		// TODO Auto-generated method stub
		//System.err.println("Key Pressed: "+key);
	}

	@Override
	public void OnKeyHeld(char key) {
		// TODO Auto-generated method stub
		//System.err.println("Key Held: "+key);
	}

	@Override
	public void OnKeyReleased(char key) {
		// TODO Auto-generated method stub
	}

	@Override
	public void OnMouseMoved(double x, double y) {
		// TODO Auto-generated method stub
	}

	@Override
	public void OnMousePressed(int button) {
		// TODO Auto-generated method stub
		System.err.println("Mouse Held: "+button);
	}

	@Override
	public void OnMouseReleased(int button) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnAttach() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnRender() {
		// TODO Auto-generated method stub
		Renderer.ClearColor(new Color(red.GetValue(), green.GetValue(), blue.GetValue(), 255));
		Application.closeRequested = close.GetValue();
	}

	@Override
	public void OnDestroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnScroll(double xOff, double yOff) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnCharTyped(char character) {
		// TODO Auto-generated method stub
		System.out.print(character);
	}
	IntPtr red = new IntPtr();
	IntPtr blue = new IntPtr();
	IntPtr green = new IntPtr();
	@Override
	public void OnIMGuiRender() {
		UI.Begin("Test");
		UI.Text("Test");
		UI.SliderInt1("Background Red Value", red, 0, 255);
		UI.SliderInt1("Background Green Value", green, 0, 255);
		UI.SliderInt1("Background Blue Value", blue, 0, 255);
		boolean c = UI.Button("Close...");
		close.SetValue(c);
		
		UI.End();
	}

}
