#version 120
uniform sampler2D texture;
uniform vec2 resolution;
uniform float size;// BLUR SIZE (Default 8.0 - Radius)

void main(){
    vec4 center_col = texture2D(texture, gl_TexCoord[0].xy);

    vec4 Color = center_col;
    float texX = 1 / resolution.x;
    float texY = 1 / resolution.y;
    for (float x = -size; x <= size; x++){
        for (float y = -size; y <= size; y++){
            vec4 centerColExt = texture2D(texture, gl_TexCoord[0].xy + vec2(x * texX, y * texY));
            Color += centerColExt;
        }
    }

    // Output to screen
    Color /= pow(size * 2., 2.);
    gl_FragColor = Color;

}