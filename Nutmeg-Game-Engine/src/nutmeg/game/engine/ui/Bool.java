package nutmeg.game.engine.ui;

import imgui.ImBool;

public class Bool {
	private ImBool imValue;
	
	public Bool() {
		imValue = new ImBool();
	}
	
	public boolean GetValue() {
		return imValue.get();
	}
	
	public void SetValue(boolean value) {
		this.imValue.set(value);
	}
	
	public ImBool GetReference() {
		return imValue;
	} 
}
