#version 120

uniform sampler2D texture;
varying vec2 texCoord;

// inverts color
void main() {
    vec4 color = texture2D(texture, texCoord);

    gl_FragColor = vec4(1.0 - color.r, 1.0 - color.g, 1.0 - color.b, color.a);
}