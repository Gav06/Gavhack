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
    float peak = gaussian1D(0, n);  // Maximum value occurs at mean (0 = center)
    return value / peak;  // Normalize to [0,1] range
}

void main() {
    vec2 pos = gl_TexCoord[0].xy;
    vec3 avgColor = vec3(0.0);
    float sumWeight = 0.0;
    // use x since we are horizontal
    for (int x = -radius; x <= radius; x++) {
        float gaussian = normalizedGaussian1D(x, radius);
        avgColor += texture2D(texture, pos + vec2(x * texelSize.x, 0.0)).rgb * gaussian;
        sumWeight += gaussian;
    }

    avgColor = avgColor / sumWeight;
    gl_FragColor = vec4(avgColor, 1.0);
}