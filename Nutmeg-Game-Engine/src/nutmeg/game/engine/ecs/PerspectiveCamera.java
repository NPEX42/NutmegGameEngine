package nutmeg.game.engine.ecs;

import org.joml.Matrix4f;

public class PerspectiveCamera extends Camera {

	private float fFOV;
	public PerspectiveCamera(float fNear, float fFar, float _fFOV, Transform transform) {
		super(fNear, fFar, transform);
		fFOV = _fFOV;
	}

	@Override
	public Matrix4f GetProjection(float _fWidth, float _fHeight) {
		return new Matrix4f().perspective(fFOV, _fWidth / _fHeight, fNear, fFar);
	}

}
