package nutmeg.gl.core;
import static org.lwjgl.opengl.GL46.*;
public class VertexArray {
	private int nVAOiD;
	
	public VertexArray() {
		nVAOiD = glGenVertexArrays();
		Bind();
	}
	
	public void Bind  () { glBindVertexArray(nVAOiD);    }
	public void Unbind() { glBindVertexArray(0);         }
	public void Delete() { glDeleteVertexArrays(nVAOiD); }
}
