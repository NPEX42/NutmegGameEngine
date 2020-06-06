package nutmeg.game.engine.ecs;

import org.joml.Matrix4f;


public class OrthoCamera extends Camera {
	public OrthoCamera(float fNear, float fFar, Transform transform) {
		super(fNear, fFar, transform);
	}
	
	public Matrix4f GetProjection(float _fWidth, float _fHeight) {
		return new Matrix4f().ortho(0, _fWidth, _fHeight, 0, fFar, fNear);
	}
}
