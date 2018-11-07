package Game;

import java.io.Serializable;

import org.lwjgl.util.vector.Vector3f;

@SuppressWarnings("serial")
public class tinyMessage implements Serializable {

	private float rotation;
private float X;
private float Y;
private float Z;
	


	public tinyMessage(float rotation, float x, float y, float z) {
		this.rotation = rotation;
		X = x;
		Y = y;
		Z = z;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public float getX() {
		return X;
	}

	public void setX(float x) {
		X = x;
	}

	public float getY() {
		return Y;
	}

	public void setY(float y) {
		Y = y;
	}

	public float getZ() {
		return Z;
	}

	public void setZ(float z) {
		Z = z;
	}

	public String toString() {
	String str = "X: " + X + "\nY: " + Y + "\nZ: " + Z + "\nRotY: " + rotation;
	return str;
	}
	
	public double checkSanity(float X2, float Z2) {
		double d = Math.sqrt(Math.pow((X - X2), 2) + (Math.pow((Z - Z2), 2)));
		if(d <= 50) {
			if ( d <= 40) {
				if(d <= 30) {
					if(d <= 20) {
						if(d <= 10) {
							return .5;
						}
						return .4;
					}
					return .3;
				}
				return .2;
			}
			return .1;
		}
		return 0;
	}
}
