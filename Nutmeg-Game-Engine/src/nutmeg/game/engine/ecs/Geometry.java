package nutmeg.game.engine.ecs;

import java.awt.Color;

import nutmeg.gl.core.Texture2D;

public class Geometry {
	private Mesh mesh;
	private Transform transform;
	private Color tintColor;
	private Texture2D albedo, shading;
	
	public Geometry(Mesh mesh, Transform transform, Color tintColor, Texture2D albedo) {
		super();
		this.mesh = mesh;
		this.transform = transform;
		this.tintColor = tintColor;
		this.albedo = albedo;
		shading = Texture2D.LoadJar("nutmeg/game/engine/res/textures/white.png");
	}
	
	public Geometry(Mesh mesh, Transform transform, Color tintColor, Texture2D albedo, Texture2D shading) {
		super();
		this.mesh = mesh;
		this.transform = transform;
		this.tintColor = tintColor;
		this.albedo = albedo;
		this.shading = shading;
	}
	
	public Mesh GetMesh() {
		return mesh;
	}
	public Transform GetTransform() {
		return transform;
	}
	public Color GetTintColor() {
		return tintColor;
	}
	public Texture2D GetAlbedo() {
		return albedo;
	}

	public Texture2D GetShading() {
		return shading;
	}
	
	
}
