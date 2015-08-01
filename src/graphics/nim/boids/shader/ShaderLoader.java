package graphics.nim.boids.shader;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import graphics.nim.boids.diag.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.opengl.GL20;

public class ShaderLoader {
	public static final int LOG_SIZE = 1024;
	
	public static Shader loadShaders(String vertpath, String fragpath) {
		int vertexShader = loadShader(vertpath, GL_VERTEX_SHADER);
		int fragmentShader = loadShader(fragpath, GL_FRAGMENT_SHADER);
		
		int shaderProgram = glCreateProgram();
		
		if(GL20.glGetShaderi(vertexShader, GL_COMPILE_STATUS) == GL_FALSE) {
			Log.error("Vertex shader: " + GL20.glGetShaderInfoLog(vertexShader, LOG_SIZE));
		}
		if(GL20.glGetShaderi(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE) {
			Log.error("Fragment shader: " + GL20.glGetShaderInfoLog(fragmentShader, LOG_SIZE));
		}
		
		glAttachShader(shaderProgram, vertexShader);
		glAttachShader(shaderProgram, fragmentShader);
		
		glLinkProgram(shaderProgram);
		glValidateProgram(shaderProgram);
		
		Shader shader = new Shader(shaderProgram);
	
		return shader;
	}
	
	private static int loadShader(String filename, int type) {
		StringBuilder shaderSource = new StringBuilder();
		
		int shaderID = 0;
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			String line = null;
			while((line = in.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}
			in.close();
		} catch(IOException e) {
			Log.error("Could not load shader from: " + filename);
		}
		
		shaderID = glCreateShader(type);
		glShaderSource(shaderID, shaderSource);
		glCompileShader(shaderID);
		
		return shaderID;
	}
}
