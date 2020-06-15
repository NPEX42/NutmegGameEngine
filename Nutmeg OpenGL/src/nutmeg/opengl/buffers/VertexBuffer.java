package nutmeg.opengl.buffers;

import nutmeg.opengl.core.OPENGL_API;
import static org.lwjgl.opengl.GL46.*;
public class VertexBuffer {
	private int ID, index, size;
	
	public VertexBuffer(int iD, int index, int size) {
		super();
		ID = iD;
		this.index = index;
		this.size = size;
	}

	public int GetID() {
		return ID;
	}

	public int GetIndex() {
		return index;
	}

	public int GetSize() {
		return size;
	}
	
	public static VertexBuffer Create(int index, float[] data, int size) {
		int ID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, ID);
		glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
		glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0);
		return new VertexBuffer(ID, index, size);
	}
	
	public void SetFloatData(float[] data) {
		OPENGL_API.SetFloatData(this, data);
	}
	
	public void Destroy() {
		OPENGL_API.DestroyVBO(this);
	}
}
