#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

varying vec4 vertColor;

void main() {
  /* invert colours */
  gl_FragColor = vec4(vec3(1) - vertColor.xyz, 1);
}