package nutmeg.game.engine.ecs;

import org.joml.Matrix4f;
@SuppressWarnings("unused")
public abstract class Camera {
	private Transform transform;
	
	public abstract Matrix4f GetProjection(float _fWidth, float _fHeight);
	
	float fNear, fFar;
	public Camera(float fNear, float fFar, Transform transform) {
		this.fNear = fNear;
		this.fFar = fFar;
	}
}
