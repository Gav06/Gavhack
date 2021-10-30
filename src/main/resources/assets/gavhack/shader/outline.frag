#version 120

uniform sampler2D DiffuseSampler;
uniform vec2 TexelSize;

void main(void) {
    vec4 centerCol = texture2D(DiffuseSampler, gl_TexCoord[0].st);
    if(centerCol.r == 1.0f && centerCol.g == 1.0f && centerCol.b == 1.0f) {
        gl_FragColor = vec4(0, 0, 0, 0);
    }

    vec4 colAvg = vec4(0, 0, 0, 0);
    for(int xo = -7; xo < 7; xo++) {
        for(int yo = -7; yo < 7; yo++) {
            vec4 currCol = texture2D(DiffuseSampler, gl_TexCoord[0].st + vec2(xo * TexelSize.x, yo * TexelSize.y));
            if(currCol.r != 0.0F || currCol.g != 0.0F || currCol.b != 0.0F) {
                colAvg += vec4(1, 0, 0, max(0, (6.0f - sqrt(xo*xo*1.0f + yo*yo*1.0f)) / 2.0F));
            }
        }
    }
    colAvg.a /= 64.0F;
    gl_FragColor = colAvg;
}
