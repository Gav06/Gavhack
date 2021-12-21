#version 120

uniform sampler2D texture;
uniform vec2 resolution;
uniform vec4 color;
uniform float size;
uniform float divider;
uniform float outerAlpha;

void main() {
    gl_FragColor = color;
    vec4 centerCol = texture2D(texture, gl_TexCoord[0].xy);
    if (centerCol.a == 0) {
        float texX = 1 / resolution.x;
        float texY = 1 / resolution.y;
        float alpha = 0.;
        for (float x = -size; x <= size; x++){
            for (float y = -size; y <= size; y++){
                vec4 centerColExt = texture2D(texture, gl_TexCoord[0].xy + vec2(x * texX, y * texY));
                if (centerColExt.a != 0){
                    float dist = distance(vec2(x, y), vec2(0));
                    float maxDist = distance(vec2(0), vec2(floor(size)));
                    alpha += divider > 0. ? max(0., (gl_FragColor.a * (outerAlpha * (maxDist - dist) / divider))) : 1.;
                }
            }
        }
        gl_FragColor = vec4(gl_FragColor.rgb, alpha);
    } else {
        gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0);
    }
}