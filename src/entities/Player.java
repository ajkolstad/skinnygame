package entities;

import models.TexturedModel;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import Game.ClientController;
import renderEngine.DisplayManager;

public class Player extends Entity {

	private static final float RUN_SPEED = 4;
	private float currentSpeed = 0;
	private static final float GRAVITY = -70;
	private static float JUMP_POWER = 10;
	private float upwardsSpeed = 0;
	private static final float TERRAIN_HEIGHT = 0;
	private boolean isInAir = false;

	public Player(TexturedModel model, Vector3f position, float rotX,
			float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}

	public void move() {
		checkInputs();
		
		setRotY(getRotY() - Camera.getAngleChange());
		upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0,
				upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		if (super.getPosition().y < TERRAIN_HEIGHT) {
			upwardsSpeed = 0;
			isInAir = false;
			super.getPosition().y = TERRAIN_HEIGHT;
		}
		ClientController.keyPressed(this.getRotY(), this.getPosition().x, this.getPosition().y, this.getPosition().z);
		
	}

	private void jump() {
		if (!isInAir) {
			this.upwardsSpeed = JUMP_POWER;
			isInAir = true;
		}
	}

	private void checkInputs() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W)
				&& Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			this.currentSpeed = (float) (RUN_SPEED * 2);
			float distance = currentSpeed
					* DisplayManager.getFrameTimeSeconds();
			float dx = (float) (distance * Math.sin(Math.toRadians(super
					.getRotY())));
			float dz = (float) (distance * Math.cos(Math.toRadians(super
					.getRotY())));
			super.increasePosition(dx, 0, dz);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			this.currentSpeed = RUN_SPEED;
			float distance = currentSpeed
					* DisplayManager.getFrameTimeSeconds();
			float dx = (float) (distance * Math.sin(Math.toRadians(super
					.getRotY())));
			float dz = (float) (distance * Math.cos(Math.toRadians(super
					.getRotY())));
			super.increasePosition(dx, 0, dz);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			this.currentSpeed = -RUN_SPEED;
			float distance = currentSpeed
					* DisplayManager.getFrameTimeSeconds();
			float dx = (float) (distance * Math.sin(Math.toRadians(super
					.getRotY())));
			float dz = (float) (distance * Math.cos(Math.toRadians(super
					.getRotY())));
			super.increasePosition(dx, 0, dz);
		} else {
			this.currentSpeed = 0;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.currentSpeed = -RUN_SPEED;
			float distance = currentSpeed
					* DisplayManager.getFrameTimeSeconds();
			float dx = (float) (distance * Math.sin(Math.toRadians(super
					.getRotY() + 90)));
			float dz = (float) (distance * Math.cos(Math.toRadians(super
					.getRotY() + 90)));
			super.increasePosition(dx, 0, dz);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.currentSpeed = RUN_SPEED;
			float distance = currentSpeed
					* DisplayManager.getFrameTimeSeconds();
			float dx = (float) (distance * Math.sin(Math.toRadians(super
					.getRotY() + 90)));
			float dz = (float) (distance * Math.cos(Math.toRadians(super
					.getRotY() + 90)));
			super.increasePosition(dx, 0, dz);
		} else {
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			jump();
		}
	}
}
