#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

uniform float fft[1024];
uniform float waveform[1024];
uniform int bufferLength;
uniform vec2 resolution;

void main() {
	int bufX = int(float(bufferLength) * gl_FragCoord.x/resolution.x);
	int bufY = int(float(bufferLength) * gl_FragCoord.y/resolution.y);
	
	float color = waveform[bufX] + fft[bufY];
	gl_FragColor = vec4(255.0, 255.0, 255.0, color);
	//gl_FragColor = vec4(gl_FragCoord.x, gl_FragCoord.y, 0, 1.0);
	//gl_FragColor = vec4(gl_FragCoord.x/width, gl_FragCoord.y/height, 0.0, 1.0);
}