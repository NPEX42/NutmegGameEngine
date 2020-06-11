package nutmeg.game.engine.rendering;

import nutmeg.gl.core.Shader;
import nutmeg.gl.core.VertexArray;
import nutmeg.gl.core.VertexBuffer;
import nutmeg.gl.core.*;

import nutmeg.gl.core.IndexBuffer;
public abstract class ARenderer {
	protected Shader batchedShader = Shader.LoadJarSafe("nutmeg/game/engine/res/shaders/batched.vs", "nutmeg/game/engine/res/shaders/batched.fs");
	protected VertexArray vao = new VertexArray();
	protected VertexBuffer posBuffer, uvBuffer, colBuffer, texBuffer;
	protected IndexBuffer trisBuffer;
	public abstract void Init  (                                                             );
	public abstract void Submit(float[] positions, float[] colors, float[] uvs, int textureID);
	public abstract void Flush (                                                             );
	public abstract void Reset (                                                             );
	
	protected float[] pos, cols, uvs, texIDs;
	protected int  [] tris                  ;
	
	final int MAX_VERTICES = 2000;
	
	protected void Render() {
		Renderer.Render(vao, trisBuffer, 0);
	}
		
}
