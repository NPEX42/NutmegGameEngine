package nutmeg.game.engine.rendering;

import nutmeg.game.engine.ecs.Mesh;
import nutmeg.gl.core.Renderer;

public class MeshRenderer {
	public static void Render(Mesh mesh) {
		Renderer.Render(mesh.GetVertexArray(), mesh.GetIndexBuffer(), 0);
	}
}
