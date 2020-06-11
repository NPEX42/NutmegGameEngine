package nutmeg.game.engine.core;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.swing.JComponent;

import nutmeg.core.Logger;
import nutmeg.gl.core.Framebuffer;

public class FrameBufferSwingComponent extends JComponent {
	private static final long serialVersionUID = 1L;
	Framebuffer buffer;
	ImageObserver obs;
	
	
	public FrameBufferSwingComponent(Framebuffer buffer, ImageObserver _obs) {
		super();
		this.buffer = buffer;
		obs = _obs;
	}



	@Override
	protected void paintComponent(Graphics g) {
		if(buffer != null) {
			BufferedImage image = buffer.GetBufferedImage();
			if(image != null) {
				g.drawImage(image, 0, 0, 1080, 720, obs);
			} else {
				Logger.Assert("NMGE", "Editor", "Unable To Use image, Image Is Null!", false);
			}
		} else {
			Logger.Assert("NMGE", "Editor", "Unable To Use Frame Buffer!", false);
		}
	}
}
