#define PROCESSING_COLOR_SHADER

uniform mat4 transform;
attribute vec4 vertex;
varying vec4 vertColor;

uniform float fft[1024];
uniform float waveform[1024];
int x;
int y;
vec4 color;
float colorX;

void main() {
  gl_Position = transform * vertex;
  x = int(gl_Position.x);
  y = int(gl_Position.x);
  color = vec4(x, y, 1, 1);
  vertColor = color;
}