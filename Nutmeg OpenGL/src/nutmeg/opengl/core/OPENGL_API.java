package nutmeg.opengl.core;
import static org.lwjgl.opengl.GL46.*;

import nutmeg.opengl.buffers.IndexBuffer;
import nutmeg.opengl.buffers.VertexArray;
import nutmeg.opengl.buffers.VertexBuffer;
public class OPENGL_API {
	public static void ClearColorBuffer(float r, float g, float b, float a) {
		glClearColor(r,g,b,a);
		glClear(GL_COLOR_BUFFER_BIT);
	}
	
	public static void DrawArrays(int vaoID, int vertexCount, int offset) {
		glBindVertexArray(vaoID);
		glDrawArrays(GL_TRIANGLES, offset, vertexCount);
	}
	
	public static void DrawElements(int vaoID, int vertexCount, int offset) {
		glBindVertexArray(vaoID);
		glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, offset);
	}

	public static VertexArray BuildVertexArray() {
		System.err.println("Generating VAO...");
		return new VertexArray(glGenVertexArrays());
	}
	
	public static void BindVAO(VertexArray array) {
		glBindVertexArray(array.GetID());
	}
	
	public static void DestroyVAO(VertexArray array) {
		glDeleteVertexArrays(array.GetID());
	}

	public static VertexBuffer ReserveFloatBuffer(int index, int length, int size) {
		System.err.println("Generating VBO...");
		VertexBuffer vbo = new VertexBuffer(glGenBuffers(), index, size);
		BindVBO(vbo);
		System.err.println("Setting VBO Data...");
		SetFloatData(vbo, new float[length * size]);
		System.err.println("Setting VBO Pointer...");
		glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0);
		return vbo;
	}
	
	public static void Enable(VertexBuffer buffer) {
		glEnableVertexAttribArray(buffer.GetIndex());
	}
	
	public static void BindVBO(VertexBuffer buffer) {
		glBindBuffer(GL_ARRAY_BUFFER, buffer.GetID());
	}
	
	public static void SetFloatData(VertexBuffer buffer, float[] data) {
		//glInvalidateBufferData(buffer.GetID());
		BindVBO(buffer);
		glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
	}

	public static void DestroyVBO(VertexBuffer vertexBuffer) {
		glDeleteBuffers(vertexBuffer.GetID());
	}

	public static IndexBuffer BuildIndexBuffer(int[] tris) {
		IndexBuffer buffer = new IndexBuffer(glGenBuffers(), tris.length);
		SetTris(buffer, tris);
		return buffer;
	}
	
	public static void SetTris(IndexBuffer buffer, int[] tris) {
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, buffer.getID());
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, tris, GL_STATIC_DRAW);
	}
	
	
	
	
}
