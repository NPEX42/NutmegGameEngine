package nutmeg.game.engine.rendering;

import java.awt.Color;

import nutmeg.opengl.core.OPENGL_API;

public class Renderer {
	public static void ClearColor(Color c) {
		OPENGL_API.ClearColorBuffer(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f);
	}
}
