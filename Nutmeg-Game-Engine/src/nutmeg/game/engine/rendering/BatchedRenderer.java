package nutmeg.game.engine.rendering;

import nutmeg.core.ArrayUtils;
import static nutmeg.core.Nutmeg.*;
import nutmeg.gl.core.IndexBuffer;
import nutmeg.gl.core.VertexBuffer;

public class BatchedRenderer extends ARenderer {
	
	@Override
	public void Init() {
		batchedShader.BindAttrib(NMGE_POSITION, "a_Position");
		
		pos    = new float[0];
		uvs    = new float[0];
		texIDs = new float[0];
		cols   = new float[0];
		
		tris = new int[MAX_VERTICES];
		
		int offset = 0;
		for(int i = 0; i < MAX_VERTICES - 6; i += 6) {
			
			tris[i + 0] = offset + 0;
			tris[i + 1] = offset + 1;
			tris[i + 2] = offset + 2;

			tris[i + 3] = offset + 2;
			tris[i + 4] = offset + 3;
			tris[i + 5] = offset + 0;

			offset += 4;
		}
		
		trisBuffer = IndexBuffer.Create(tris);
		posBuffer  = VertexBuffer.ReserveFloat(NMGE_POSITION, NMGE_VEC3, MAX_VERTICES * NMGE_VEC3, 0, 0);
		uvBuffer   = VertexBuffer.ReserveFloat(NMGE_TEXCOORD, NMGE_VEC2, MAX_VERTICES * NMGE_VEC2, 0, 0);
		colBuffer  = VertexBuffer.ReserveFloat(NMGE_COLOR   , NMGE_VEC3, MAX_VERTICES * NMGE_VEC3, 0, 0);
		texBuffer  = VertexBuffer.ReserveFloat(NMGE_TEXID   , NMGE_VEC1, MAX_VERTICES * NMGE_VEC1, 0, 0);
	}

	@Override
	public void Submit(float[] positions, float[] colors, float[] uvs, int textureID) {
		
		pos      = ArrayUtils.concatenate(pos, positions);
		this.uvs = ArrayUtils.concatenate(this.uvs, uvs);
		texIDs   = ArrayUtils.concatenate(new float[] {textureID}, texIDs);
		cols     = ArrayUtils.concatenate(cols, colors);
		
		if(pos.length > MAX_VERTICES) {
			Flush();
			Reset();
		}
	}

	@Override
	public void Flush() {
		batchedShader.Bind();
		vao.Bind();
		posBuffer.SetData(pos   );
		uvBuffer.SetData (uvs   );
		colBuffer.SetData(cols  );
		texBuffer.SetData(texIDs);
		
		Render();
	}

	@Override
	public void Reset() {
		pos    = new float[0];
		uvs    = new float[0];
		texIDs = new float[0];
		cols   = new float[0];
	}

}
