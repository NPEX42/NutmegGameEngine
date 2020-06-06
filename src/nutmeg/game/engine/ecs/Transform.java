package nutmeg.game.engine.ecs;

import org.joml.*;
import static nutmeg.core.Nutmeg.*;

public class Transform {
	private float x, y, z, pitch, yaw, roll, xs, ys, zs;

	public Transform(float x, float y, float z, float pitch, float yaw, float roll, float xs, float ys, float zs) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.pitch = pitch;
		this.yaw = yaw;
		this.roll = roll;
		this.xs = xs;
		this.ys = ys;
		this.zs = zs;
	}
	
	public Transform() {
		this(0,0,0,0,0,0,1,1,1);
	}
	
	public Transform(float X, float Y, float Z) {
		this(X,Y,Z,0,0,0,1,1,1);
	}
	
	public Transform(float X, float Y, float Z, float Pitch, float Yaw, float Roll) {
		this(X,Y,Z,Pitch,Yaw,Roll,1,1,1);
	}
	
	public Matrix4f GetTransformMatrix() {
		Matrix4f mat = new Matrix4f();
		mat.translate(x, y, z);
		mat.rotate(pitch, NMGE_V3_RIGHT);
		mat.rotate(yaw, NMGE_V3_UP);
		mat.rotate(roll, NMGE_V3_FORWARD);
		mat.scale(xs, ys, zs);
		return mat;
	}
	
	public void Translate(float X, float Y, float Z) {
		x += X;
		y += Y;
		z += Z;
	}
	
	public void Translate2D(float X, float Y) {
		x += X;
		y += Y;
		z += 0.0f;
	}
	
	public void Rotate(float YAW, float PITCH, float ROLL) {
		yaw += YAW;
		pitch += PITCH;
		roll += ROLL;
	}
	
	public void Rotate2D(float rot) {
		roll += rot;
	}
	
	public void Scale(float X, float Y, float Z) {
		xs += X;
		ys += Y;
		zs += Z;
	}
	
	public void Scale2D(float X, float Y) {
		xs += X;
		ys += Y;
	}
	
	public void SetScale(float X, float Y, float Z) {
		xs = X;
		ys = Y;
		zs = Z;
	}
	
	public void SetScale2D(float X, float Y) {
		xs = X;
		ys = Y;
	}
	
	public void SetPosition(float X, float Y, float Z) {
		x = X;
		y = Y;
		z = Z;
	}
	
	public void SetPosition(float X, float Y) {
		x = X;
		y = Y;
		z = 0;
	}
	
	
}
