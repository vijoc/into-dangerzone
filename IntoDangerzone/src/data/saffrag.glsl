#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

uniform float fft[1024];
uniform float waveform[1024];
uniform float width;
uniform float height;

void main() {
  gl_FragColor = vec4(gl_FragCoord.x/width, gl_FragCoord.y/height, 0.0, 1.0);
}