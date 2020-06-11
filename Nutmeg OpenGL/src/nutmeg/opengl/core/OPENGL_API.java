package nutmeg.opengl.core;
import static org.lwjgl.opengl.GL46.*;
public class OPENGL_API {
	public static void ClearColorBuffer(float r, float g, float b, float a) {
		glClearColor(r,g,b,a);
		glClear(GL_COLOR_BUFFER_BIT);
	}
	
	
}
