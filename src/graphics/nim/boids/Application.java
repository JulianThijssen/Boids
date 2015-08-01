package graphics.nim.boids;

import graphics.nim.boids.sim.World;

public class Application {
	public static void main(String[] args) {
		Window window = new Window("Flocking Simulation", 1280, 768);
		Scene scene = new Scene();
		World world = new World();
		Renderer renderer = new Renderer();
		
		while (!window.isClosed()) {
			world.update(scene);
			renderer.update(scene);
			
			window.poll();
			window.update();
		}
	}
}
