package nutmeg.game.engine.rendering;

import java.awt.Color;

import nutmeg.opengl.buffers.VertexArray;
import nutmeg.opengl.core.OPENGL_API;

public class Renderer {
	protected static void ClearColor(Color c) {
		OPENGL_API.ClearColorBuffer(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f);
	}
	
	protected static void IndexedRender(VertexArray vao, int vertexCount) {
		OPENGL_API.DrawElements(vao.GetID(), vertexCount, 0);
	}
}
