package nutmeg.game.engine.rendering;

import java.awt.Color;

import nutmeg.opengl.buffers.IndexBuffer;
import nutmeg.opengl.buffers.VertexArray;
import nutmeg.opengl.buffers.VertexBuffer;

public class Renderer2D extends Renderer {
	
	private static VertexArray vao;
	private static VertexBuffer vboPos;
	
	public static void Background(Color color) {
		ClearColor(color);
	}
	
	public static void BeginScene() {
		
	}
	
	public static void EndSene() {
		
	}
	
	
	public static void DrawQuad(float x, float y, float w, float h, Color color) {
		System.err.println("Generating Positions...");
		System.err.println("["+x+","+y+"] | ("+w+"x"+h+")");
//		float[] pos = {
//				x - w / 2, y - h - 2, 0,
//				x + w / 2, y - h / 2, 0,
//				x + w / 2, y + h / 2, 0,
//				x - w / 2, y + h / 2
//		};
		
		float[] pos = {
				-0.5f, -0.5f, 0.0f,
				+0.5f, -0.5f, 0.0f,
				+0.5f, +0.5f, 0.0f,
				-0.5f, +0.5f, 0.0f,
		};
		//System.err.println("Generating Triangles...");
		int[] tris = {
				0,1,2,
				2,3,0
		};
		
		//System.err.println("Generating VAO...");
		if(vao == null) { vao = VertexArray.Generate();
			System.err.println(vao);
		}
		vao.Bind();
		//System.err.println(vao.GetID());
		//System.err.println("Generating VBO...");
		if(vboPos == null) vboPos = VertexBuffer.Create(0, pos, 3);
		//System.err.println(vboPos.GetID());
		vboPos.SetFloatData(pos);
		
		//System.err.println("Generating IndexBuffer...");
		IndexBuffer ibo = IndexBuffer.Create(tris);
		//System.err.println(ibo.getID());
		
		System.err.println("Rendering...");
		IndexedRender(vao, ibo.getLength());
	}
}
