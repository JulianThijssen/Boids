package graphics.nim.boids;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import graphics.nim.boids.input.Input;
import graphics.nim.boids.math.Matrix4f;
import graphics.nim.boids.math.Vector3f;
import graphics.nim.boids.mesh.Mesh;
import graphics.nim.boids.mesh.MeshLoader;
import graphics.nim.boids.shader.Shader;
import graphics.nim.boids.shader.ShaderLoader;
import graphics.nim.boids.sim.Boid;
import graphics.nim.boids.texture.TextureData;
import graphics.nim.boids.texture.TextureLoader;

public class Renderer {
	private Camera camera = null;
	private Mesh quad = null;
	private TextureData boidTexture = null;
	private TextureData pointerTexture = null;
	private Shader shader = null;
	
	private Matrix4f projectionMatrix = new Matrix4f();
	private Matrix4f modelMatrix = new Matrix4f();
	
	public Renderer() {
		camera = new Camera(-Window.width/2, Window.width/2, -Window.height/2, Window.height/2, -1, 1);
		shader = ShaderLoader.loadShaders("res/shader.vert", "res/shader.frag");
		quad = MeshLoader.loadQuad();
		boidTexture = TextureLoader.loadTexture("res/boid.png");
		pointerTexture = TextureLoader.loadTexture("res/pointer.png");
		
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public void update(Scene scene) {
		glClearColor(0, 0, 0, 1);
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		glUseProgram(shader.handle);
		
		projectionMatrix.setIdentity();
		camera.loadProjectionMatrix(projectionMatrix);
		
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, boidTexture.handle);
		glUniform1i(glGetUniformLocation(shader.handle, "boidTex"), 0);
		
		for (Boid boid: scene.boids) {
			modelMatrix.setIdentity();
			modelMatrix.translate(boid.position);
			modelMatrix.rotate(new Vector3f(0, 0, boid.heading));
			modelMatrix.scale(new Vector3f(16, 16, 1));
			
			glUniformMatrix4(glGetUniformLocation(shader.handle, "projectionMatrix"), false, projectionMatrix.getBuffer());
			glUniformMatrix4(glGetUniformLocation(shader.handle, "modelMatrix"), false, modelMatrix.getBuffer());
			
			glBindVertexArray(quad.handle);
			glDrawArrays(GL_TRIANGLES, 0, 6);
		}
		
		drawPointer();
	}
	
	public void drawPointer() {
		float mx = (float) Input.mouseX - Window.width/2;
		float my = (float) Input.mouseY - Window.height/2;
		
		glBindTexture(GL_TEXTURE_2D, pointerTexture.handle);
		glUniform1i(glGetUniformLocation(shader.handle, "pointerTex"), 0);
		
		modelMatrix.setIdentity();
		modelMatrix.translate(new Vector3f(mx, -my, 0));
		modelMatrix.scale(new Vector3f(16, 16, 1));
		
		glUniformMatrix4(glGetUniformLocation(shader.handle, "projectionMatrix"), false, projectionMatrix.getBuffer());
		glUniformMatrix4(glGetUniformLocation(shader.handle, "modelMatrix"), false, modelMatrix.getBuffer());
		
		glBindVertexArray(quad.handle);
		glDrawArrays(GL_TRIANGLES, 0, 6);
	}
}
