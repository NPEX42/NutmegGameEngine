package nutmeg.game.engine.core;

import java.awt.Color;

import org.joml.Vector2f;

import nutmeg.game.engine.ui.UIComponent;

public class PushButton extends UIComponent{
	Color activeColor, inactiveColor;
	boolean state = false;
	public PushButton(Vector2f position, Vector2f size,  Color active, Color inactive) {
		super(position, size);
		activeColor = active;
		inactiveColor = inactive;
	}

	@Override
	public void OnLeftClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnRightClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnMiddleClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnLeftClickHeld() {
		// TODO Auto-generated method stub
		state = true;
	}

	@Override
	public void Reset() {
		// TODO Auto-generated method stub
		state = false;
	}

	@Override
	public void OnRender(Application app) {
		// TODO Auto-generated method stub
		app.DrawQuad(x, y, w, h, (state) ? activeColor : inactiveColor);
	}
	
	public boolean GetState() {
		boolean b = state;
		return b;
	}

}
