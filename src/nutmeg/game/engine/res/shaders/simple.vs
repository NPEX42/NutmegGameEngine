#version 330 core
in  vec3 a_Position;
in  vec2 a_UV;
out vec2 v_UV;
out mat4 v_Proj;
out vec3 v_Pos;
uniform mat4 u_Transform;
uniform mat4 u_Projection;
void main() {
	gl_Position = u_Projection * u_Transform * vec4(a_Position,1);
	v_UV = a_UV;
}