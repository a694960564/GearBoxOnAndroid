precision mediump float;
varying vec3 Normal;
varying vec3 FragPos;
uniform vec3 u_LightPos;
uniform vec3 u_ViewPos;
uniform vec3 u_ObjectColor;
vec3 lightColor = vec3(1.0,1.0,1.0);
float ambientStrength = 0.5;
float specularStrength = 0.5;
void main(){
    vec3 norm = normalize(Normal);
    vec3 lightDir = normalize(u_LightPos - FragPos);
    vec3 viewDir = normalize(u_ViewPos - FragPos);
    vec3 reflectDir = reflect(-lightDir, norm);
    float spec = pow(max(dot(viewDir,reflectDir), 0.0), 32.0);
    vec3 specular = specularStrength * spec * lightColor;
    float diff = max(dot(norm,lightDir), 0.0);
    vec3 diffuse = diff * lightColor;
    vec3 ambient = ambientStrength * lightColor;
    vec3 result = (ambient + diffuse + specular) * u_ObjectColor;
    gl_FragColor = vec4(result, 1.0);
}