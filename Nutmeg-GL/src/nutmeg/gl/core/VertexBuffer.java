package nutmeg.gl.core;
import static org.lwjgl.opengl.GL46.*;

import nutmeg.core.Logger;
public class VertexBuffer {
	private int nVBOiD, nCoordSize, nLength, nIndex;
	
	private VertexBuffer(int _nVBOiD, int _nCoordSize, int _nLength, int _nIndex) {
		super();
		nVBOiD = _nVBOiD;
		nCoordSize = _nCoordSize;
		nLength = _nLength;
		nIndex = _nIndex;
		Enable();
	}
	
	public static VertexBuffer Create(int _nIndex, int _nCoordSize, float[] _fData, int _nStride, int _nOffset) {
		Logger.Assert("NMGL", "VertexBuffer", "Coordinate Size Out Of Range... ("+_nCoordSize+")", (_nCoordSize > 0 && _nCoordSize < 5));
		Logger.Assert("NMGL", "VertexBuffer", "Index Out Of Range... ("+_nIndex+"/"+OpenGL.GetMaxVertexArrayAttributes()+")", (_nIndex < OpenGL.GetMaxVertexArrayAttributes()));
		int _nID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, _nID);
		glBufferData(GL_ARRAY_BUFFER, _fData, GL_STATIC_DRAW);
		glVertexAttribPointer(_nIndex, _nCoordSize, GL_FLOAT, false, _nStride, _nOffset);
		return new VertexBuffer(_nID, _nCoordSize, _fData.length, _nIndex);
	}
	
	public static VertexBuffer ReserveFloat(int _nIndex, int _nCoordSize, int _nDataSize, int _nStride, int _nOffset) {
		int _nID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, _nID);
		glBufferData(GL_ARRAY_BUFFER, new float[_nDataSize], GL_DYNAMIC_DRAW);
		glVertexAttribPointer(_nIndex, _nCoordSize, GL_FLOAT, false, _nStride, _nOffset);
		return new VertexBuffer(_nID, _nCoordSize, _nDataSize, _nIndex);
	}
	
	public void Bind  () { glBindBuffer(GL_ARRAY_BUFFER, nVBOiD); }
	public void Unbind() { glBindBuffer(GL_ARRAY_BUFFER, 0);      }
	public void Delete() { glDeleteBuffers(nVBOiD);               }
	public void Enable() { glEnableVertexAttribArray(nIndex);     }
	
	public void SetData(float[] _fData) {
		Logger.Debug("NMGL", "Vertex Buffer", "Data Length: "+_fData.length);
		if(_fData.length > nLength || _fData.length % nCoordSize != 0) throw new RuntimeException("[NMGL] Allocated Data is Too Large And/Or Not A multiple of CoordSize");
		glInvalidateBufferData(nVBOiD);
		glBufferData(GL_ARRAY_BUFFER, _fData, GL_DYNAMIC_DRAW);
		Logger.Debug("NMGL", "Vertex Buffer", "VB"+toString());
	}
	
	public String toString() {
		return String.format("VertexBuffer-%04x (%04d elements) %01dD", nVBOiD, nLength, nCoordSize);
	}
	
}
