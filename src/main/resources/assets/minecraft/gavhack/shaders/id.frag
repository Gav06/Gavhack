#version 120

uniform sampler2D texture;
uniform vec3 rgb;

void main() {
    vec4 texColor = texture2D(texture, gl_TexCoord[0].xy);

    if (texColor.a != 0) {
        gl_FragColor = vec4(rgb, 1.0);
    } else {
        gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0);
    }
}
