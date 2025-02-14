#version 320

in vec2 texCoord;
out vec4 gl_Position;

// just a normal vertex transformation
void main() {
    texCoord = gl_MultiTexCoord0.xy;
    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}