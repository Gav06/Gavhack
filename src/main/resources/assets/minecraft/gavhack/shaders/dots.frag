#extension GL_EXT_gpu_shader4 : enable

uniform sampler2D texture;
uniform vec3 dotColor;
uniform vec4 fillColor;

void main()
{
    vec4 centerCol = texture2D(texture, gl_TexCoord[0].xy);
    float x = gl_FragCoord.x;
    float y = gl_FragCoord.y;

    gl_FragColor = fillColor;


    vec3 color = vec3(gl_FragColor.r, gl_FragColor.g, gl_FragColor.b);
    float alpha = gl_FragColor.a;
    if (int(gl_FragCoord.x) % 10 == 0 && int(gl_FragCoord.y) % 10 == 0)
    {
        color = dotColor;
        alpha = 1.0;
    }
    gl_FragColor = vec4(color, centerCol.a * alpha);
}