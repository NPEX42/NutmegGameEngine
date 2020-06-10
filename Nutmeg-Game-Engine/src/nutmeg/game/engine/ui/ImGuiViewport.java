package nutmeg.game.engine.ui;

import imgui.ImGui;
import nutmeg.gl.core.Texture2D;

public class ImGuiViewport {
	public static void ViewPort(String name, Texture2D texture, float scale) {
		UI.Begin(name);
			ImGui.image(texture.GetID(), texture.GetWidth() * scale, texture.GetHeight() * scale);
		UI.End();
	}
}
