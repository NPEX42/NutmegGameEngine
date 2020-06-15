package nutmeg.opengl.buffers;

import nutmeg.opengl.core.OPENGL_API;
import static org.lwjgl.opengl.GL46.*;
public class IndexBuffer {
	public int ID, length;

	public int getID() {
		return ID;
	}

	public int getLength() {
		return length;
	}

	public IndexBuffer(int iD, int length) {
		ID = iD;
		this.length = length;
	}
	
	public static IndexBuffer Create(int[] tris) {
		int ID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, tris, GL_STATIC_DRAW);
		return new IndexBuffer(ID, tris.length);
	}
}
