#version 120

uniform sampler2D texture;
uniform vec2 resolution;

uniform float radius;

uniform int fill;
uniform int dots;

int mod(int x, int y) {
    return x - y * int(floor(float(x) / float(y)));
}

void main(void) {
    vec4 centerCol = texture2D(texture, gl_TexCoord[0].xy);
    vec2 texelSize = vec2(1.0 / resolution.x, 1.0 / resolution.y);
    // if we are inside an object
    if (centerCol.a != 0.0) {
        // draw dots
        if (dots == 1 && mod(int(gl_FragCoord.x), 10) == 0 && mod(int(gl_FragCoord.y), 10) == 0) {
            gl_FragColor = vec4(centerCol.r, centerCol.g, centerCol.b, 1.0);
        } else {
            // draw fill
            if (fill == 1) {
                gl_FragColor = vec4(centerCol.r, centerCol.g, centerCol.b, 0.20);
            } else {
                gl_FragColor = vec4(0.0);
            }
        }
    } else {
        // sample to find a
        for (float x = -radius; x <= radius; x++) {
            for (float y = -radius; y <= radius; y++) {
                vec4 currentColor = texture2D(texture, gl_TexCoord[0].xy + vec2(texelSize.x * x, texelSize.y * y));

                if (currentColor.a != 0) {
                    gl_FragColor = currentColor;
                }
            }
        }
    }
}