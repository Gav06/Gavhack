#version 120

uniform sampler2D texture;
uniform vec2 texelSize;
uniform int radius;

float gaussian1D(float x, int n) {
    // Constants for the gaussian function
    float sigma = float(n) / 6.0;  // Standard deviation (covers ~99.7% of distribution within n)
    float mu = 0.0;  // Mean (centered at 0)

    // Calculate gaussian value
    float exponent = -0.5 * pow((x - mu) / sigma, 2.0);
    float coefficient = 1.0 / (sigma * sqrt(2.0 * 3.14159265359));  // 1 / (σ * √(2π))

    return coefficient * exp(exponent);
}

// Normalized version that ensures the output is between 0 and 1
float normalizedGaussian1D(float x, int n) {
    float value = gaussian1D(x, n);
    float peak = gaussian1D(mu, n);  // Maximum value occurs at mean
    return value / peak;  // Normalize to [0,1] range
}


// box blur for now, will make gaussian later
void main() {
    vec2 pos = gl_TexCoord[0].xy;
    vec3 avgColor = vec3(0.0);
    for (int x = -radius; x <= radius; x++) {
        for (int y = -radius; y <= radius; y++) {
            avgColor += texture2D(texture, pos + (vec2(x, y) * texelSize)).rgb;
        }
    }
    float divisor = (radius * 2 + 1) * (radius * 2 + 1);
    gl_FragColor = vec4(avgColor.rgb / divisor, 1.0);
}