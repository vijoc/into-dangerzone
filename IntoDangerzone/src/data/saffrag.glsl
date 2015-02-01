#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

varying vec4 vertColor;
uniform float fft[];
uniform float waveform[];

void main() {
  int posX = int(gl_FragCoord.x);
  int posY = int(gl_FragCoord.y);
  gl_FragColor = vec4(waveform.get(posX), fft[posY], 1, 1);
}