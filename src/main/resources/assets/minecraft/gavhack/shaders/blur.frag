#version 120

uniform sampler2D texture;
uniform vec2 texelSize;
uniform int radius;

// box blur for now, will make gaussian later
void main() {
    vec2 pos = gl_TexCoord[0].xy;
    vec4 avgColor = vec4(0.0);
    for (int x = -radius; x <= radius; x++) {
        for (int y = -radius; y <= radius; y++) {
            avgColor += texture2D(texture, pos + (vec2(x, y) * texelSize));
        }
    }
    float divisor = (radius * 2 + 1) * (radius * 2 + 1);
    gl_FragColor = vec4(avgColor.r / divisor, avgColor.g / divisor, avgColor.b / divisor, 1.0);
}