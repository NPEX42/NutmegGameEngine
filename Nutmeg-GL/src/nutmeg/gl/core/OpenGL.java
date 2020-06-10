package nutmeg.gl.core;

import static org.lwjgl.opengl.GL46.*;
public class OpenGL {
	public static int GetMaxTextureImageUnits() {
		int[] maxSlots = new int[1];
		glGetIntegerv(GL_MAX_TEXTURE_IMAGE_UNITS, maxSlots);
		return maxSlots[0];				
	}
	
	public static int GetMaxVertexArrayAttributes() {
		int[] maxAttribs = new int[1];
		glGetIntegerv(GL_MAX_VERTEX_ATTRIBS, maxAttribs);
		return maxAttribs[0];	
	}
	
	public static String GetVersion() {
		return glGetString(GL_VERSION);
	}
	
	public static String GetVendor() {
		return glGetString(GL_VENDOR);
	}
	
	public static String[] GetExtensions() {
		return glGetString(GL_EXTENSIONS).split(" ");
	}
	
	public static String GetShadingLanguageVersion() {
		return glGetString(GL_SHADING_LANGUAGE_VERSION);
	}
	
	public static String GetRenderer() {
		return glGetString(GL_VERSION);
	}
	
	
}
