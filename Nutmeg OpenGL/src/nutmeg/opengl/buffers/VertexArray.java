package nutmeg.opengl.buffers;

import nutmeg.opengl.core.OPENGL_API;

public class VertexArray {
	private int ID;
	public VertexArray(int id) {
		ID = id;
	}
	
	public static VertexArray Generate() {
		return OPENGL_API.BuildVertexArray();
	}
	
	public int GetID() { return ID; }

	public void Bind() {
		OPENGL_API.BindVAO(this);
	}
	
	public String toString() {
		return String.format("VAO: 0x%016x",ID);
	}
}
