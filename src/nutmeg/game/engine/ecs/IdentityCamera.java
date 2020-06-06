package nutmeg.game.engine.ecs;

import org.joml.Matrix4f;

public class IdentityCamera extends Camera {

	public IdentityCamera() {
		super(0, 0, new Transform());
	}

	@Override
	public Matrix4f GetProjection(float _fWidth, float _fHeight) {
		return new Matrix4f();
	}

}
