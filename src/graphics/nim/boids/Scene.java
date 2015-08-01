package graphics.nim.boids;

import graphics.nim.boids.math.Vector3f;
import graphics.nim.boids.sim.Boid;

import java.util.ArrayList;
import java.util.List;

public class Scene {
	public List<Boid> boids = new ArrayList<Boid>();
	
	public Scene() {
		for (int x = -8; x < 8; x++) {
			for (int y = -8; y < 8; y++) {
				boids.add(new Boid(new Vector3f(50 * x, 50 * y, 0)));
			}
		}
	}
}
