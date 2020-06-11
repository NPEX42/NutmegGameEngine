package nutmeg.gl.core;
import static org.lwjgl.opengl.GL46.*;

import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;

import org.joml.*;

import nutmeg.core.IO;
import nutmeg.core.Logger;
import nutmeg.core.Nutmeg;
public class Shader {
	private int nProgramID, nVertexID, nFragmentID;
	private HashMap<String, Integer> uniformCache = new HashMap<String, Integer>();

	private Shader(int nProgramID, int nVertexID, int nFragmentID) {
		super();
		this.nProgramID = nProgramID;
		this.nVertexID = nVertexID;
		this.nFragmentID = nFragmentID;
	}
	
	public static Shader Compile(String _sVertexSource, String _sFragmentSource) {
		int nVS, nFS, nPG;
		nVS = glCreateShader(GL_VERTEX_SHADER  );
		nFS = glCreateShader(GL_FRAGMENT_SHADER);
		
		glShaderSource(nVS, _sVertexSource);
		glShaderSource(nFS, _sFragmentSource);
		
		glCompileShader(nVS);
		if(glGetShaderi(nVS, GL_COMPILE_STATUS ) == GL_FALSE){
			Logger.Warn("NMGL", "Shader", "Unable To Compile Vertex Shader...");
			Logger.Warn("NMGL", "Shader", glGetShaderInfoLog(nFS));
			//System.exit(-1);
		}
		
		glCompileShader(nFS);
		if(glGetShaderi(nFS, GL_COMPILE_STATUS ) == GL_FALSE){
			Logger.Warn("NMGL", "Shader", "Unable To Compile Fragment Shader...");
			Logger.Warn("NMGL", "Shader", glGetShaderInfoLog(nFS));
			//System.exit(-1);
		}
		
		nPG = glCreateProgram();
		
		glAttachShader(nPG, nVS);
		glAttachShader(nPG, nFS);
		
		glLinkProgram(nPG);
		return new Shader(nPG, nVS, nFS);
				
	}
	
	public static Shader Load(String _sVertexPath, String _sFragmentPath) {
		String vs, fs;
		vs = IO.loadString(_sVertexPath  );
		fs = IO.loadString(_sFragmentPath);
		
		if(vs == null || fs == null) { System.err.println("[NMGL/Shader] Unable To Load shader Files..."); System.exit(1);}
		
		return Shader.Compile(vs, fs);
	}
	
	public static Shader LoadJar(String _sVertexPath, String _sFragmentPath) throws IOException {
		String vs, fs;
		vs = IO.loadString(Shader.class.getClassLoader().getResourceAsStream(_sVertexPath), _sVertexPath);
		fs = IO.loadString(Shader.class.getClassLoader().getResourceAsStream(_sFragmentPath), _sFragmentPath);
		
		if(vs == null || fs == null) { System.err.println("[NMGL/Shader] Unable To Load shader Files..."); Nutmeg.Close(); }
		
		return Shader.Compile(vs, fs);
	}
	
	public static Shader LoadJarSafe(String _sVertexPath, String _sFragmentPath) {
		try {
			String vs, fs;
			vs = IO.loadString(Shader.class.getClassLoader().getResourceAsStream(_sVertexPath), _sVertexPath);
			fs = IO.loadString(Shader.class.getClassLoader().getResourceAsStream(_sFragmentPath), _sFragmentPath);
			if(vs == null || fs == null) { System.err.println("[NMGL/Shader] Unable To Load shader Files..."); Nutmeg.Close();}
			
			return Shader.Compile(vs, fs);
		} catch (IOException ioex) {
			Logger.Throw("NMGL", "Shader", "Unable To Load Shaders", ioex);
			Nutmeg.Close();
			return null;
		}
	}
	
	
	public void Bind  () { glUseProgram(nProgramID);                                                             }
	public void Unbind() { glUseProgram(0);                                                                      }
	public void Delete() { glDeleteShader(nVertexID);  glDeleteShader(nFragmentID); glDeleteProgram(nProgramID); }
	
	private int getUniform(String name) {
		Bind();
		if(uniformCache.containsKey(name)) {
			return uniformCache.get(name);
		} else {
			int loc = glGetUniformLocation(nProgramID, name);
			if(loc == -1) Logger.Debug("NMGL", "Shader", "Unable To Find Uniform '"+name+"'");
			if(loc > -1) uniformCache.put(name, loc);
			return loc;
		}
	}
	
	public void SetUniformColor(String name, Color c) {
		glUniform4f(getUniform(name), c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f);
	}
	
	public void SetUniformTexture(String name, Texture2D texture, int nSlot, int nUnit) {
		int maxSlots = OpenGL.GetMaxTextureImageUnits();
		if(nSlot > maxSlots) Logger.Throw("NMGL", "Shader", "Unable to allocate Slot #"+nSlot, new Exception());
		SetUniformInt(name, nSlot);
		texture.Bind();
		texture.SetSlot(nUnit);
	}
	
	public void SetUniformInt(String name, int _nValue) {
		glUniform1i(getUniform(name), _nValue);
	}
	
	public void SetUniformIntArray(String name, int[] _nValue) {
		glUniform1iv(getUniform(name), _nValue);
	}
	
	public void SetUniformFloat(String name, float _fValue) {
		glUniform1f(getUniform(name), _fValue);
	}
	
	public void SetUniformFloatArray(String name, float[] _fValue) {
		glUniform1fv(getUniform(name), _fValue);
	}
	
	
	public void SetUniformDouble(String name, double _dValue) {
		glUniform1d(getUniform(name), _dValue);
	}
	
	public void SetUniformDoubleArray(String name, double[] _dValue) {
		glUniform1dv(getUniform(name), _dValue);
	}
	
	public void SetUniformVec2(String name, Vector2f _v2Value) {
		glUniform2f(getUniform(name), _v2Value.x, _v2Value.y);
	}
	
	public void SetUniformVec3(String name, Vector3f _v3Value) {
		glUniform3f(getUniform(name), _v3Value.x, _v3Value.y, _v3Value.z);
	}
	
	public void SetUniformVec4(String name, Vector4f _v4Value) {
		glUniform4f(getUniform(name), _v4Value.x, _v4Value.y, _v4Value.z, _v4Value.w);
	}
	
	public void SetUniformMat2(String name, Matrix2f _m2Value) {
		glUniformMatrix2fv(getUniform(name), false, _m2Value.get(new float[4]));
	}
	
	public void SetUniformMat3(String name, Matrix3f _m3Value) {
		glUniformMatrix3fv(getUniform(name), false, _m3Value.get(new float[9]));
	}
	
	public void SetUniformMat4(String name, Matrix4f _m4Value) {
		glUniformMatrix4fv(getUniform(name), false, _m4Value.get(new float[16]));
	}
	
	public void BindAttrib(int _nIndex, String _sName) {
		glBindAttribLocation(nProgramID, _nIndex, _sName);
	}
	
	
}
