#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

varying vec4 vertColor;
uniform float fft[];
uniform float scope[];

void main() {
  /* invert colors */
  /* gl_FragColor = vec4(vec3(1) - vertColor.xyz, 1); */
  gl_FragColor = vec4(fft[0], fft[1], fft[2], fft[3]);
}