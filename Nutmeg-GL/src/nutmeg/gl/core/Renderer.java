package nutmeg.gl.core;
import static org.lwjgl.opengl.GL46.*;

import java.awt.Color;
public class Renderer {
	private static Shader shader;
	
	public static void Init(Shader _shShader) {
		shader = _shShader;
	}
	
	public static void Render(VertexArray _vMesh, int _nVertexCount, int _nOffset) {
		if(shader != null) { shader.Bind(); }
		_vMesh.Bind();
		glDrawArrays(GL_TRIANGLES, _nOffset, _nVertexCount);
	}
	
	public static void Render(VertexArray _vMesh, IndexBuffer _iTriangles, int _nOffset) {
		if(shader != null) { shader.Bind(); }
		_vMesh.Bind();
		_iTriangles.Bind();
		glDrawElements(GL_TRIANGLES, _iTriangles.GetLength(), GL_UNSIGNED_INT, _nOffset);
	}
	
	public static void Background(Color c) {
		glClearColor(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f);
		glClear(GL_COLOR_BUFFER_BIT);
	}
}
