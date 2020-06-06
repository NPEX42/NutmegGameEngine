package nutmeg.game.engine.core;

import java.awt.Color;
import java.util.HashMap;

import org.joml.Vector2f;

import nutmeg.core.Logger;
import nutmeg.game.engine.ui.ToggleButton;
import nutmeg.game.engine.ui.UIComponent;

public class UI {
	private static HashMap<String, UIComponent> components = new HashMap<String, UIComponent>();
	private static Application app;
	
	public static void Init(Application parent) {
		app = parent;
	}
	
	public static void Update() {
		Vector2f mousePos = new Vector2f();
		for(String componentID : components.keySet()) {
			UIComponent component = components.get(componentID);
			component.OnRender(app);
			if(app.mouse.IsButtonReleased(0) && component.InBounds(mousePos)) {
				component.OnLeftClick();
			} 
			
			if(app.mouse.IsButtonDown(0) && component.InBounds(mousePos)) {
				component.OnLeftClickHeld();
			} else {
				component.Reset();
			}
		}
	}
	
	public static void AddToggleButton(String name, Vector2f position, Vector2f size, Color active, Color inactive) {
		components.put(name, new ToggleButton(position, size, active, inactive));
	}

	public static ToggleButton GetToggleButton(String name) {
		UIComponent component = components.get(name);
		if(component instanceof ToggleButton) {
			return (ToggleButton) component;
		} else {
			Logger.Warn("NMGE", "UI", "UI Component is not a toggle button...");
			return null;
		}
	}
	
	public static void AddPushButton(String name, Vector2f position, Vector2f size, Color active, Color inactive) {
		components.put(name, new PushButton(position, size, active, inactive));
	}
	
	public static PushButton GetPushButton(String name) {
		UIComponent component = components.get(name);
		if(component instanceof PushButton) {
			return (PushButton) component;
		} else {
			Logger.Warn("NMGE", "UI", "UI Component is not a push button...");
			return null;
		}
	}
}
 