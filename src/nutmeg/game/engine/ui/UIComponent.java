package nutmeg.game.engine.ui;

import org.joml.Vector2f;

import nutmeg.game.engine.core.Application;

public abstract class UIComponent {
	private Vector2f topLeft, bottomRight;
	public float x, y, w, h;
	public abstract void OnLeftClick();
	public abstract void OnRightClick();
	public abstract void OnMiddleClick();
	public abstract void OnLeftClickHeld();
	
	public abstract void Reset();
	
	public abstract void OnRender(Application app);
	
	public boolean InBounds(Vector2f mousePos) {
		return 
				(mousePos.x > topLeft.x && mousePos.y > topLeft.y)
				&&
				(mousePos.x < bottomRight.x && mousePos.y < bottomRight.y);
	}
	public UIComponent(Vector2f position, Vector2f size) {
		super();
		this.topLeft = new Vector2f(position.x - size.x / 2, position.y - size.y / 2);
		this.bottomRight = new Vector2f(position.x + size.x / 2, position.y + size.y / 2);
		
		x = position.x;
		y = position.y;
		
		w = size.x;
		h = size.y;
	}
	
	
}
