package graphics.nim.boids.mesh;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class MeshLoader {
	public static Mesh loadQuad() {
		//Declare some variables
		Mesh mesh = new Mesh();
		FloatBuffer vertexBuffer = null;
		FloatBuffer textureBuffer = null;
		
		//Create the vertex array object
		int vao = glGenVertexArrays();
		glBindVertexArray(vao);
		
		//Vertices
		float[] vertices = {
			-0.5f, -0.5f, 0,
			0.5f, -0.5f, 0,
			-0.5f, 0.5f, 0,
			-0.5f, 0.5f, 0,
			0.5f, -0.5f, 0,
			0.5f, 0.5f, 0
		};
		
		vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
		vertexBuffer.put(vertices);
		vertexBuffer.flip();
		
		//Put the vertex buffer into the VAO
		int vertexVBO = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertexVBO);
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glEnableVertexAttribArray(0);
		
		//Textures
		float[] texCoords = {
			0, 0,
			1, 0,
			0, 1,
			0, 1,
			1, 0,
			1, 1
		};
		
		textureBuffer = BufferUtils.createFloatBuffer(texCoords.length);
		textureBuffer.put(texCoords);
		textureBuffer.flip();
		
		//Put the texture coordinate buffer into the VAO
		int textureVBO = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, textureVBO);
		glBufferData(GL_ARRAY_BUFFER, textureBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glEnableVertexAttribArray(1);
		
		//Unbind the vao
		glBindVertexArray(0);
			
		mesh.handle = vao;
		
		return mesh;
	}
}
