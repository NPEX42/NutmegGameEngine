package pixel.draw.app;

import java.awt.Color;

import nutmeg.game.engine.core.Application;
import nutmeg.game.engine.ecs.OrthoCamera;
import nutmeg.game.engine.ecs.Transform;

public class App extends Application {
	int tileW, tileH, gridW, gridH, splitW, splitH, gridX, gridY, palIdx, bpc;
	Color[] grid, pallete;
	@Override
	public void OnUserCreate() {
		tileW = 16;
		tileH = 16;
		
		gridW = 16;
		gridH = 16;
		
		splitW = 1;
		splitH = 1;
		
		gridX = 8;
		gridY = 8;
		
		bpc = 1;
		
		grid = new Color[gridW * gridH];
		System.out.println((int) Math.pow(8, bpc));
		pallete = new Color[(int) Math.pow(8, bpc)];
		System.err.println("Pallete Length: "+pallete.length);
		for(int i = 0; i < grid.length; i++) {
			grid[i] = Color.white;
		}
		//color = r * (255 / bpc), 
		int idx = 0;
		for(int r = 0; r <  Math.pow(2, bpc); r++) {
			for(int g = 0; g <  Math.pow(2, bpc); g++) {
				for(int b = 0; b <  Math.pow(2, bpc); b++) {
					int RED, GREEN, BLUE;
					RED = (int) (r * (255 / Math.pow(2, bpc))) * 2;
					GREEN = (int) (g * (255 / Math.pow(2, bpc))) * 2;
					BLUE = (int) (b * (255 / Math.pow(2, bpc))) * 2;
					if(idx < pallete.length ) pallete[idx] = new Color(RED % 256, GREEN % 256, BLUE% 256);
					System.err.println("Pallete Index: "+idx+" (RGB) ("+r+","+g+","+b+") "+String.format("0x%02x%02x%02x", RED, GREEN, BLUE));
					idx++;
				}
			}
		}
	}

	@Override
	public void OnUserUpdate(float fElapsedTime) {
		Background(Color.gray);
//		DrawGrid(gridX,gridY);
//		DrawPallete(8, 16);
//		
//		int tx, ty;
//		tx = mouse.GetMouseX() / tileW;
//		ty = mouse.GetMouseY() / tileH;
//		
//		if(InBounds(tx, ty, 0, gridW, 0, gridH)) DrawSquare(tx, ty, gridX,gridY, pallete[palIdx]);
//		if(mouse.IsButtonDown(0)) {
//			SetColor(pallete[palIdx], tx, ty);
//		} else if(mouse.IsButtonDown(1)) {
//			SetColor(Color.white, tx, ty);
//		}
//		
//		if(keyboard.isKeyReleased('Q')) palIdx--;
//		if(keyboard.isKeyReleased('E')) palIdx++;
//		if(palIdx < 0) palIdx = pallete.length - 1;
//		palIdx %= pallete.length;
		
//		if(keyboard.isKeyPressed('A')) gridX -= 1;
//		if(keyboard.isKeyPressed('D')) gridX += 1;
		
	}

	@Override
	public void OnUserDestroy() {
	}
	
	public void DrawGrid(int xOff, int yOff) {
		for(int y = 0; y < gridH; y++) {
			for(int x = 0; x < gridW; x++) {
				DrawSquare(x, y, xOff, yOff, GetColor(x, y));
			}
		}
	}
	
	public void DrawPallete(int xOff, int yOff) {
		int y = yOff;
		int x = 0;
		for(int i = 0; i < pallete.length; i++) {
			if((x * tileW) > width) { y++; x = 0; }
			DrawSquare(x, y, xOff, yOff, pallete[i]);
			x++;
		}
	}
	
	public void DrawSquare(int x, int y, int xOff, int yOff, Color color) {
		//if(color != null) DrawQuad((x * tileW) + xOff, (y * tileH) + yOff, tileW - splitW, tileH - splitH, color);
	}
	
	public Color GetColor(int x, int y) {
		if(InBounds(x, y, 0, gridW, 0, gridH)) {
			return grid[y * gridW + x];
		} else {
			return Color.black;
		}
	} 
	
	public void  SetColor(Color col, int x, int y) {
		if(InBounds(x, y, 0, gridW, 0, gridH))
			grid[y * gridW + x] = col;
	}
	
	public boolean InBounds(int x, int y, int minX, int maxX, int minY, int maxY) {
		return (x >= minX) && (x < maxX) && (y >= minY) && (y < maxY);
	}

	@Override
	public void OnUIRender() {
	}
	
}
