package nutmeg.game.engine.core;

import nutmeg.game.engine.ecs.Mesh;

public class PrimitiveFactory {
	private PrimitiveFactory() {}
	public static Mesh GenerateQuad(float x, float y, float w, float h) {
		float[] pos = {
				x - w / 2, y - h / 2, 0,
				x + w / 2, y - h / 2, 0,
				x - w / 2, y + h / 2, 0,
				x + w / 2, y + h / 2, 0,
		};
		
		float[] uv = {
				0,0,
				1,0,
				0,1,
				1,1
		};
		
		int[] tris = {
				0,1,2,
				2,3,1
		};
		
		return new Mesh(pos, uv, tris);
	}
}
