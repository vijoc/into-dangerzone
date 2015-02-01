#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

varying vec4 vertColor;
uniform float fft[];
uniform float waveform[];

void main() {
  /* invert colors */
  /* gl_FragColor = vec4(vec3(1) - vertColor.xyz, 1); */
  float x = float(1024);
  float y = float(1024);
  gl_FragColor = vec4(gl_FragCoord.x/x, gl_FragCoord.y/y, gl_FragCoord.x, 1);
}