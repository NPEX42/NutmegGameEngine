#version 330 core
in  vec2 v_UV;
in  mat4 v_Proj;
in  vec3 v_Pos;
out vec4 v_PixelColor;
uniform vec4 u_TintColor;
uniform sampler2D t_Albedo;
uniform sampler2D t_Shading;

void main() {
	v_PixelColor = texture(t_Albedo,v_UV) * texture(t_Shading,v_UV) * u_TintColor;
	//v_PixelColor = texture(t_Shading,v_UV) * u_TintColor;
} 