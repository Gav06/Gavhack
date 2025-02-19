#version 120

uniform sampler2D texture;
uniform vec2 texelSize;

uniform float radius;

uniform int fill;
uniform int dots;

float mod(float a, float b) {
    return a - (b * floor(a/b));
}

void main(void) {
    vec4 centerCol = texture2D(texture, gl_TexCoord[0].xy);

    if(centerCol.a != 0) {
        if (dots == 1 && mod(int(gl_FragCoord.x), 10) == 0 && mod(int(gl_FragCoord.y), 10) == 0) {
            gl_FragColor = vec4(centerCol.r, centerCol.g, centerCol.b, 1.0f);
        } else {
            if (fill == 1) {
                gl_FragColor = vec4(centerCol.r, centerCol.g, centerCol.b, 0.20);
            } else {
                gl_FragColor = vec4(0.0);
            }
        }
    } else {
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