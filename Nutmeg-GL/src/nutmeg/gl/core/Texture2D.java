package nutmeg.gl.core;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.system.MemoryUtil;

import nutmeg.core.Logger;

import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.stb.STBImage.*;
@SuppressWarnings("unused")
public class Texture2D {
	private int nID;
	private ByteBuffer texture;
	private int w;
	private int h;

	public Texture2D(ByteBuffer data, int width, int height, int channels, int BytesPerChannel) {
		nID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, nID);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glGenerateMipmap(GL_TEXTURE_2D);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
		texture = data;
		w = width;
		h = height;
		
		Logger.Log("NMGL", "Texture2D", "Building Texture ID-"+String.format("%04x", nID));
	}
	
	public static Texture2D Load(String filePath) {
		long tp1, tp2;
		tp1 = System.currentTimeMillis();
		int[] width = new int[1], height = new int[1], channels = new int[1];
		ByteBuffer data;
		data = stbi_load(filePath, width, height, channels, 4);
		tp2 = System.currentTimeMillis();
		Logger.Log("NMGL", "Texture2D", "Texture '"+filePath+"' ("+width[0]+"x"+height[0]+") Loaded in "+(tp2 - tp1)+"ms");
		
		Logger.Assert("NMGL", "Texture2D", "Unable To Load Texture '"+filePath+"'", (data != null));
		
		return LoadRGBA8(data, width[0], height[0]);
	}
	
	public static Texture2D LoadRGBA8(ByteBuffer data, int width, int height) {
		return new Texture2D(data, width, height,4,4);
	}
	
	public static Texture2D LoadJar(String filePath) {
		long tp1, tp2;
		tp1 = System.currentTimeMillis();
		BufferedImage img = null;
		try {
			
			InputStream stream = Texture2D.class.getClassLoader().getResourceAsStream(filePath);
			if(stream == null) { System.err.println("Unable To Load file '"+filePath+"'"); return null; }
		    img = ImageIO.read(stream);
		    Texture2D tex = loadTexture(img);
		    tp2 = System.currentTimeMillis();
		    Logger.Log("NMGL", "Texture2D", "Texture '"+filePath+"' ("+tex.GetWidth()+"x"+tex.GetHeight()+") Loaded in "+(tp2 - tp1)+"ms");
			return tex;
		} catch (IOException e) {
			Logger.Throw("NMGL","Texture2D","Unable To Load file '"+filePath+"'",e);
			return null;
		}
	}
	
	private final static int BYTES_PER_PIXEL = 4;
	private static Texture2D loadTexture(BufferedImage image){
		 
		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
		
		ByteBuffer buffer = MemoryUtil.memAlloc(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL); //4 for RGBA, 3 for RGB
		 
		for(int y = 0; y < image.getHeight(); y++){
		   for(int x = 0; x < image.getWidth(); x++){
		       int pixel = pixels[y * image.getWidth() + x];
		       buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
		       buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
		       buffer.put((byte) (pixel & 0xFF));               // Blue component
		       buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
		   }
		}
		 
		 buffer.flip(); //FOR THE LOVE OF GOD DO NOT FORGET THIS
		 return new Texture2D(buffer, image.getWidth(), image.getHeight(), 4, 1);
	}
	
	public void Bind     (        ) { glBindTexture(GL_TEXTURE_2D, nID); }
	public void SetSlot  (int unit) { glActiveTexture(unit);             }
	public void Delete   (        ) { glDeleteTextures(nID);             }
	public int  GetID    (        ) { return nID;                        }
	public int  GetHeight(        ) { return h;                          } 
	public int  GetWidth (        ) { return w;                          } 
	
	
}
