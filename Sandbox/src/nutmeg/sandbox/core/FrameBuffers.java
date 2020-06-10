package nutmeg.sandbox.core;

import java.awt.Color;

import org.joml.Matrix4f;

import nutmeg.core.Nutmeg;
import nutmeg.game.engine.core.Window;
import nutmeg.game.engine.ecs.Mesh;
import nutmeg.game.engine.rendering.MeshRenderer;
import nutmeg.gl.core.Framebuffer;
import nutmeg.gl.core.Renderer;
import nutmeg.gl.core.Shader;

import org.lwjgl.opengl.*;

public class FrameBuffers {
	
	private float[] pos = {
			+0.5f, +0.5f, 0,
			-0.5f, +0.5f, 0,
			-0.5f, -0.5f, 0,
			+0.5f, -0.5f, 0
	};
	
	private float[] uvs = {
			1.0f,0.0f,
			0.0f,0.0f,
			0.0f,1.0f,
			1.0f,1.0f,	
	};
	
	private int[] tris = {
		0,1,2,
		2,3,0
	};
	
	public void Start() {
		Window window = Window.Open(1080, 720, "FrameBuffers", true);
		Nutmeg.EnableDebugMode();
		Mesh quad = new Mesh(pos, uvs, tris);
		Shader shader = Shader.Load("basic.v.glsl", "basic.f.glsl");
		shader.Bind();
		shader.BindAttrib(0, "a_Position");
		shader.BindAttrib(1, "a_UV");
		Framebuffer frameBuffer = Framebuffer.Create(1080, 720);
		while(window.IsActive()) {
			frameBuffer.Bind();
			MeshRenderer.Render(quad);
			Renderer.Background(Color.green);
			shader.SetUniformMat4("u_Transform", new Matrix4f());
			shader.SetUniformMat4("u_Projection", new Matrix4f());
			shader.SetUniformColor("u_TintColor", Color.white);
			MeshRenderer.Render(quad);
			frameBuffer.Unbind();
			shader.SetUniformMat4("u_Transform", new Matrix4f());
			shader.SetUniformMat4("u_Projection", new Matrix4f());
			shader.SetUniformColor("u_TintColor", Color.white);
			shader.SetUniformTexture("t_Albedo", frameBuffer.GetColorData(), 0, GL46.GL_TEXTURE0);
			MeshRenderer.Render(quad);
			window.Update();
		}
		window.Destroy();
		Window.CloseGLFW();
	}
}
