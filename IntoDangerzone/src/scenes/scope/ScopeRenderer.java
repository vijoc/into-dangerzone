package scenes.scope;

import ddf.minim.AudioSource;
import graphics.Renderer;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;

public class ScopeRenderer extends Renderer {
	
	private PApplet applet;
	private AudioSource audioSource;
	private float[] lBuffer;
	private float[] rBuffer;
	private float[] sumBuffer;
	PGraphics context;
	
	int width;
	int height;
	
	public ScopeRenderer(PApplet applet, AudioSource audioSource) {
		super(applet);
		this.audioSource = audioSource;
		this.applet = applet;
		width = applet.width;
		height = applet.height;
	}

	@Override
	public void render() {
		applet.background(0);
		lBuffer = audioSource.left.toArray();
		rBuffer = audioSource.right.toArray();
		sumBuffer = new float[lBuffer.length];
		
		for(int i = 0; i < lBuffer.length; i++){
			sumBuffer[i] = lBuffer[i] + rBuffer[i];
		}
		
		applet.translate(-width/2, 0);
		applet.stroke(255);
		
		applet.translate(0, -height/4);
		for(int i = 0; i < lBuffer.length-1; i++) {
			float x = PApplet.map(i, 0, lBuffer.length, 0, applet.width);
			applet.line(x, lBuffer[i]*1000, x, lBuffer[i+1]*1000);
		}
		applet.translate(0, height/2);
		for(int i = 0; i < rBuffer.length-1; i++) {
			float x = PApplet.map(i, 0, rBuffer.length, 0, applet.width);
			applet.line(x, rBuffer[i]*1000, x, rBuffer[i+1]*1000);
		}
		applet.translate(0, -height/4);
		for(int i = 0; i < sumBuffer.length-1; i++) {
			float x = PApplet.map(i, 0, sumBuffer.length, 0, applet.width);
			applet.line(x, sumBuffer[i]*1000, x, sumBuffer[i+1]*1000);
		}
		
		mirrorLeft();
		mirrorTop();
	}
	
	private void mirrorLeft(){
		applet.loadPixels();
		for(int x = 0; x < width/2; x++){
			for(int y = 0; y < height; y++){
				applet.pixels[x+y*width] = applet.pixels[(width-x)+y*width-1];
			}
		}
		applet.updatePixels();
	}
	
	private void mirrorTop(){
		applet.loadPixels();
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height/2; y++){
				applet.pixels[x*height+y] = applet.pixels[x*height+(height-y)-1];
			}
		}
		applet.updatePixels();		
	}
}
