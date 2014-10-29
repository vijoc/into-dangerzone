package scenes.julia;

import java.util.Arrays;

import math.Complex;
import processing.core.PApplet;
import processing.core.PGraphics;
import graphics.Renderer;

public class JuliaSceneRenderer extends Renderer {
	
	public enum RenderMode {
		SOLID, STEPPED
	}
	
	private RenderMode mode = RenderMode.STEPPED;

	float translateX;
	float translateY;
	float shrink;
	float shrinkv;
	
	float colorStep;
	
	private JuliaSet set;
	
	PGraphics context;
	
	public JuliaSceneRenderer(PApplet parent, JuliaSet set) {
		super(parent);
		this.set = set;
		
		context = parent.createGraphics(2*parent.width/5, 2*parent.height/5);
		context.loadPixels();
		translateX = context.width / 2;
		translateY = context.height / 2;
		shrink = 2.0f / context.width;
		shrinkv = shrink*2;
		colorStep = 255 / this.set.getIterations();
	}

	@Override
	public void render() {
		parent.background(255);
		
		context.beginDraw();		
		context.clear(); // Doesn't seem to work?
		Arrays.fill(context.pixels, context.color(255));
		
		Complex z;
		for(float x = 0; x < context.width; x++) {
			for(float y = 0; y < context.height; y++) {
				
				z = new Complex(
						(x-translateX)*shrinkv,
						(y-translateY)*shrinkv
				);
				
				switch(mode) {
				case SOLID:
					renderSolid(z, x, y);
					break;
				case STEPPED:
					renderStepped(z, x, y);
					break;
				}
			}
		}
		context.updatePixels();
		context.endDraw();
		
		parent.image(context, -parent.width/2, -parent.height/2, parent.width, parent.height);
		parent.textSize(16);
		parent.color(255, 0, 0);
		parent.text("FPS: "+parent.frameRate, parent.width/3, parent.height/3);
	}
	
	public void setMode(RenderMode mode) {
		this.mode = mode;
	}
	
	private int pixelIndex(float x, float y) {
		return (int) (y * context.width + x);
	}
	
	private void renderSolid(Complex z, float x, float y) {
		if(set.lastIterationContains(z)) {
			context.pixels[pixelIndex(x, y)] = context.color(0);
		}
	}
	
	private void renderStepped(Complex z, float x, float y) {
		int iter = set.lastIterationContaining(z);
		
		if(iter >= 0) {
			context.pixels[pixelIndex(x, y)] = context.color(255 - iter*colorStep);
		}
	}

}
