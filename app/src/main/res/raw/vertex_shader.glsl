attribute vec3 a_Position;
attribute vec3 a_Normal;
uniform  mat4 u_MVPMatrix;
uniform mat4 u_ModelMatrix;
varying vec3 Normal;
varying vec3 FragPos;
void main(void){
        gl_Position = u_MVPMatrix * vec4(a_Position, 1.0);
        FragPos = vec3(u_ModelMatrix * vec4(a_Position, 1.0));
        Normal =  vec3(u_ModelMatrix * vec4(a_Normal, 1.0));
}