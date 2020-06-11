package nutmeg.game.engine.core;

import java.awt.Color;

import nutmeg.gl.core.Framebuffer;
import nutmeg.gl.core.Shader;
import nutmeg.gl.core.Texture2D;

public interface ApplicationAPI {
	//Initialisation Functions =====================================
	public void ConstructWindow(int width, int height, String title);
	//==============================================================
	
	//User Functions ===============================================
	public void OnUserCreate (                  );
	public void OnUserUpdate (float fElaspedTime);
	public void OnUserDestroy(                  );
	public void OnUIRender   (                  );
	//==============================================================
	
	//Drawing Functions ============================================
	public void DrawRect  (float x, float y, Color color      );
	public void DrawImage (float x, float y, Texture2D texture);
	public void Background(Color color                    );
	//==============================================================
	
	//Renderer Functions ===========================================
	public void      SetShader       (Shader newShader  );
	public void      SetRenderTarget (Framebuffer buffer); //A Null Framebuffer indicates the render target of the screen.
	public void      SubmitGeometry  (                  );
	public Texture2D GetRenderTexture(                  );
	//==============================================================
	
	
	
	
}
