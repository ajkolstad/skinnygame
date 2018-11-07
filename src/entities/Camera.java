package entities;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

	private float distanceFromPlayer = -1;
	static float angleAroundPlayer = 0;
	private Vector3f position = new Vector3f(0, 0, 0);
	private float pitch = 20;
	float yaw;
	private float roll;
	public static float angleChange;
	
	private Player player;
	public float getDistanceFromPlayer() {
		return distanceFromPlayer;
	}

	public static float getAngleAroundPlayer() {
		return angleAroundPlayer;
	}

	public Player getPlayer() {
		return player;
	}

	public float getSpeed() {
		return speed;
	}

	private float speed = 0.5f;
	
	

	public Camera(Player player) {
		this.player = player;
	}

	public void move() {
		calculatePitch();
		calculateAngleAroundPlayer();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance, verticalDistance);
		this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
	}
	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}


	public float getRoll() {
		return roll;
	}
	
	private void calculateCameraPosition(float horizontalDistance, float verticalDistance) {
		float theta = player.getRotY() + angleAroundPlayer;
		float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
		position.y = player.getPosition().y + verticalDistance + 5.4f;
		
	}
	private float calculateHorizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	private float calculateVerticalDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}

	
	private void calculatePitch() {
			float pitchChange = Mouse.getDY() * 0.2f;
			pitch -= pitchChange;
			if(pitch < -90) {
				pitch = -90;
			}
			if(pitch > 70) {
				pitch = 70;
			}
	}
	
	private void calculateAngleAroundPlayer() {
			angleChange = Mouse.getDX() * 0.2f;
	}
	
	public static float getAngleChange() {
		return angleChange;
	}
}
