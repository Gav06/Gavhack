uniform sampler2D texture;

uniform vec2 resolution;
uniform vec4 customColor;

uniform float dotColorSync;
uniform vec3 dotCustomColor;

void main()
{
    vec4 centerCol = texture2D(texture, gl_TexCoord[0].xy);
    float x = gl_FragCoord.x;
    float y = gl_FragCoord.y;

    gl_FragColor = vec4(customColor.r, customColor.g, customColor.b, centerCol.a * customColor.a);


    vec3 color = vec3(gl_FragColor.r, gl_FragColor.g, gl_FragColor.b);
    float alpha = gl_FragColor.a;
    if (int(gl_FragCoord.x) % 10 == 0 && int(gl_FragCoord.y) % 10 == 0)
    {
        if (dotColorSync == 0)
        {
            color = dotCustomColor;
        }
        alpha = 1.0;
    }
    gl_FragColor = vec4(color, centerCol.a * alpha);
}