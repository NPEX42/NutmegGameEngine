package nutmeg.game.engine.ui;

import java.awt.Color;

import org.joml.Vector2f;



import nutmeg.core.Logger;
import nutmeg.game.engine.core.Application;

public class ToggleButton extends UIComponent {
	boolean state = false;
	Color activeColor, inactiveColor;
	public ToggleButton(Vector2f position, Vector2f size, Color active, Color inactive) {
		super(position, size);
		activeColor = active;
		inactiveColor = inactive;
	}

	@Override
	public void OnLeftClick() {
		//Logger.Log("NMGE","Button","Button has been clicked");
		state = !state;
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
	public void OnRender(Application app) {
		app.DrawQuad(x, y, w, h, (state) ? activeColor : inactiveColor);
	}
	
	public boolean GetState() {
		boolean b = state;
		return b;
	}

	@Override
	public void OnLeftClickHeld() {
	}

	@Override
	public void Reset() {
	}

}
