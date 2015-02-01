#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

varying vec4 vertColor;
uniform float fft[1024];
uniform float waveform[1024];

void main() {
  int posX = int(gl_FragCoord.x);
  int posY = int(gl_FragCoord.y);
  gl_FragColor = vec4(waveform[posX], fft[0], 1, 1);
}