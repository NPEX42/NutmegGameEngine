package nutmeg.gl.core;
import static org.lwjgl.opengl.GL46.*;

public class IndexBuffer {
	private int nIBOiD, nLength;
	
	private IndexBuffer(int _nIBOiD, int _nLength) {
		super();
		nIBOiD = _nIBOiD;
		nLength = _nLength;
	}
	
	public static IndexBuffer Create(int[] _nData) {
		int _nID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, _nID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, _nData, GL_STATIC_DRAW);
		return new IndexBuffer(_nID, _nData.length);
	}
	
	public void Bind  () { glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, nIBOiD); }
	public void Unbind() { glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);      }
	public void Delete() { glDeleteBuffers(nIBOiD);                       }

	public int GetLength() {
		return nLength;
	}
	
	public String toString() {
		return String.format("IndexBuffer-%04x (%04d elements) ", nIBOiD, nLength);
	}
	
	
}
