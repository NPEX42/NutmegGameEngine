package nutmeg.gl.core;
import static org.lwjgl.opengl.GL11.GL_LINEAR;

import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL46.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.system.MemoryUtil;


import nutmeg.core.Logger;
@SuppressWarnings("unused")
public class Framebuffer {
	private int id, colorID, depthID, width, height;
	public static Framebuffer Create(int width, int height) {
		int id = glCreateFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, id);
		int colorID = glCreateTextures(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, colorID);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, MemoryUtil.memAlloc(width * height * 4));
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		
		int depthID = glCreateTextures(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, depthID);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH24_STENCIL8, width, height, 0, GL_DEPTH_STENCIL, GL_UNSIGNED_INT_24_8, MemoryUtil.memAlloc(width * height * 4));
		
		glFramebufferTexture(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, colorID, 0);
		glFramebufferTexture(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, depthID, 0);
		
		if(!(glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_COMPLETE)) Logger.Log("NMGL", "FrameBuffer", "unsuccessfully Built FrameBuffer #"+id+", ("+width+"x"+height+")");
		
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		
		return new Framebuffer(id, colorID, depthID, width, height);  
	}
	private Framebuffer(int id, int colorID, int depthID, int width, int height) {
		super();
		this.id = id;
		this.colorID = colorID;
		this.depthID = depthID;
		this.width = width;
		this.height = height;
	}
	
	public void Bind  () { glBindFramebuffer(GL_FRAMEBUFFER, id); }
	public void Unbind() { glBindFramebuffer(GL_FRAMEBUFFER, 0); };
	public void Delete() { glDeleteFramebuffers(id); }
	
	public Texture2D GetColorData() {
		return new Texture2D(colorID, width, height);
	}
	
	BufferedImage image;
	public BufferedImage GetBufferedImage() {
		return image;
	}
	
	public void GenBufferedImage() {
		ByteBuffer buffer = MemoryUtil.memAlloc(width * height * 4);
		glReadnPixels(0, 0, width, height, GL_RGBA8, GL_RGBA, buffer);
		byte[] data = new byte[buffer.capacity()];
		buffer.get(data);
		try {
			image = ImageIO.read(new ByteArrayInputStream(data));
		} catch (IOException e) {
			Logger.Throw("NMGL", "FrameBuffer", "Couldn't build Buffered Image", e);
		}
	}
	public static Framebuffer Create(float width2, float height2) {
		return Create((int)width2,(int)height2);
	}
	
	
}
