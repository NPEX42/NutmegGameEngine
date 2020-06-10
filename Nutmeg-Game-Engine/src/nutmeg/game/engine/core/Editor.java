package nutmeg.game.engine.core;

import java.awt.Color;

import javax.swing.JFrame;

import nutmeg.gl.core.Framebuffer;
import nutmeg.gl.core.Renderer;

public class Editor {
	private static final int 
	WIDTH = 1080, 
	HEIGHT = 720;
	
	private static final String
	TITLE = "Nutmeg Editor 1.0a";
	
	FrameBufferSwingComponent jBuffer;
	Framebuffer buffer;
	
	JFrame frame;
	Window window;
	
	public void Start() {
		frame = new JFrame(TITLE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Loop();
	}
	
	public void Loop() {
		new Thread( () -> {
			window = Window.Open(WIDTH, HEIGHT, TITLE, true);
			window.Hide();
			buffer = Framebuffer.Create(WIDTH, HEIGHT);
			jBuffer = new FrameBufferSwingComponent(buffer, frame);
			frame.add(jBuffer);
			while(window.IsActive()) {
				buffer.Bind();
				Renderer.Background(Color.BLACK);
				buffer.Unbind();
				buffer.GenBufferedImage();
			}
		}).start();
	}
}
