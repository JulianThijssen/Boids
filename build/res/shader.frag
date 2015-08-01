#version 330 core

uniform sampler2D boidTex;

in vec3 pass_position;
in vec2 pass_texCoord;

out vec4 out_Color;

void main(void) {
	vec4 color = texture(boidTex, pass_texCoord);
	out_Color = vec4(color);
}
