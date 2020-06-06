package nutmeg.game.engine.ecs;

import nutmeg.gl.core.IndexBuffer;
import nutmeg.gl.core.VertexArray;
import nutmeg.gl.core.VertexBuffer;
import static nutmeg.core.Nutmeg.*;
public class Mesh {
	private VertexArray vaMesh;
	private VertexBuffer vbPos, vbUV;
	private IndexBuffer ibTris;
	
	public Mesh(float[] _fPositions, float[] _fTextureCoords, int[] _nTris) {
		vaMesh = new VertexArray();
		vbPos  = VertexBuffer.Create(NMGE_POSITION, NMGE_VEC3, _fPositions, 0, 0    );
		vbUV   = VertexBuffer.Create(NMGE_TEXCOORD, NMGE_VEC2, _fTextureCoords, 0, 0);
		ibTris = IndexBuffer.Create(_nTris);
	}
	
	public void Delete() {
		vaMesh.Delete();
		vbPos.Delete ();
		vbUV.Delete  ();
		ibTris.Delete();
	}
	
	public void Bind  () { vaMesh.Bind();   }
	public void Unbind() { vaMesh.Unbind(); }
	
	public int GetVertexCount() { return ibTris.GetLength(); }
	
	public VertexArray GetVertexArray() { return vaMesh; }
	public IndexBuffer GetIndexBuffer() { return ibTris; }
}
