#version 320

uniform sampler2d texture;
in vec2 texCoord;
out vec4 gl_FragColor;

// inverts color
void main() {
    vec4 color = texture2D(texture, texCoord);
    gl_FragColor = vec4(1.0 - color.x, 1.0 - color.y, 1.0 - color.z, color.w);
}