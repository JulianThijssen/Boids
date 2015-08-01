package graphics.nim.boids.sim;

import graphics.nim.boids.math.Vector3f;

public class Boid {
	public static final float MIN_SPEED = 2;
	public static final float MAX_SPEED = 4;
	
	public Vector3f position = null;
	public Vector3f velocity = new Vector3f(0, 0, 0);
	
	
	public float heading = 0;
	
	public Boid(Vector3f position) {
		this.position = position;
		velocity.set((float) Math.random(), (float) Math.random(), 0);
	}
	
	public void update() {
		heading = (float) Math.toDegrees(Math.atan2(velocity.y, velocity.x));
		
		if (velocity.length() > MAX_SPEED) {
			velocity.normalise().scale(MAX_SPEED);
		}
		if (velocity.length() < MIN_SPEED) {
			velocity.normalise().scale(MIN_SPEED);
		}
		position.add(velocity);
	}
	
	public void moveAhead() {
		
	}
}
