#version 120

uniform sampler2D texture;

void main() {
    vec4 texColor = texture2D(texture, gl_TexCoord[0].xy);
    float avg = (texColor.r + texColor.g + texColor.b) / 3;
    gl_FragColor = vec4(1.0 - texColor.r, 1.0 - texColor.g, 1.0 - texColor.b, 1.0);
}