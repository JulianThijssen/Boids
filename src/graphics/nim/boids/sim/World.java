package graphics.nim.boids.sim;

import graphics.nim.boids.Scene;
import graphics.nim.boids.Window;
import graphics.nim.boids.input.Input;
import graphics.nim.boids.math.Vector3f;

public class World {
	public World() {

	}
	
	public void update(Scene scene) {
		float mx = (float) Input.mouseX - Window.width/2;
		float my = (float) Input.mouseY - Window.height/2;
		Vector3f centerOfMass = new Vector3f(mx, -my, 0);
		
		// Collision resolution and flocking
		for (Boid boid1: scene.boids) {
			Vector3f c = new Vector3f(0, 0, 0);
			
			for (Boid boid2: scene.boids) {
				if (boid2 != boid1) {
					float distance = Vector3f.distance(boid1.position, boid2.position);
					if (distance < 20) {
						c = Vector3f.sub(c, Vector3f.sub(boid2.position, boid1.position));
						boid1.velocity.add(c.scale(0.125f));
					}
					if (distance < 30) {
						boid1.velocity.add(Vector3f.scale(boid2.velocity, 0.05f));
					}
				}
			}
		}
		
		// Random movement
		for (Boid boid: scene.boids) {
			boid.velocity.add(new Vector3f((float) Math.random(), (float) Math.random(), 0).scale(0.125f));
		}
		
		// Wall avoiding
		for (Boid boid: scene.boids) {
			if (boid.position.x > Window.width/2 - 50) {
				boid.velocity.add(new Vector3f(-1, 0, 0));
			}
			if (boid.position.x < -Window.width/2 + 50) {
				boid.velocity.add(new Vector3f(1, 0, 0));
			}
			if (boid.position.y > Window.height/2 - 50) {
				boid.velocity.add(new Vector3f(0, -1, 0));
			}
			if (boid.position.y < -Window.height/2 + 50) {
				boid.velocity.add(new Vector3f(0, 1, 0));
			}
		}
		
		// Center of mass movement
		for (Boid boid: scene.boids) {
			Vector3f comDirection = Vector3f.sub(centerOfMass, boid.position);
			
			
			if (comDirection.length() < 100) {
				float dot = Vector3f.dot(boid.velocity, comDirection);
				Vector3f vel = boid.velocity.normalise();
				
				if (dot < 0) {
					boid.velocity.add(new Vector3f(-vel.y, vel.x, 0).scale(0.001f));
				}
				else {
					boid.velocity.add(new Vector3f(vel.y, -vel.x, 0).scale(0.001f));
				}
			}
			else {
				boid.velocity.add(comDirection.scale(1f / (comDirection.length() * 5)));
			}

			boid.update();
		}
	}
}
