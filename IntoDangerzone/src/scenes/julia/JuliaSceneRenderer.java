package scenes.julia;

import java.util.Arrays;

import math.Complex;
import processing.core.PApplet;
import processing.core.PConstants;
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
	
	float red = 255, green = 255, blue = 255;
	
	float colorStep;
	
	private JuliaSet set;
	
	private boolean renderDebug;
	
	PGraphics context;
	
	public JuliaSceneRenderer(PApplet parent, JuliaSet set) {
		super(parent);
		this.set = set;
		
		context = parent.createGraphics(parent.width/2, parent.height/2);
		context.loadPixels();
		translateX = context.width / 2;
		translateY = context.height / 2;
		shrink = 2.0f / context.width;
		shrinkv = shrink*2;
		colorStep = 255 / this.set.getIterations();
	}

	@Override
	public void render() {
		parent.colorMode(PConstants.RGB, 255.0f);
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
		if(renderDebug) {
			parent.textSize(16);
			parent.color(255, 0, 0);
			parent.text("FPS: "+parent.frameRate, parent.width/3, parent.height/3);
			parent.text("c: " + set.getC().toString(), parent.width/3, parent.height/3 + 20);
			parent.text("Iterations: " + set.getIterations(), parent.width/3, parent.height/3 + 40);
		}
	}
	
	public void setMode(RenderMode mode) {
		this.mode = mode;
	}
	
	public void setDebug(boolean d) {
		this.renderDebug = d;
	}
	
	public void toggleDebug() {
		setDebug(!renderDebug);
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
		
		if(iter >= 1) {
			float val = (float) iter / set.getIterations();
			int color;
			if(val < 0.5) {
				color = context.lerpColor(context.color(255, 255, 255), context.color(0,0,0), val * 2);
			} else {
				color = context.lerpColor(context.color(0, 0, 0), context.color(255, 255, 255), (val-0.5f)*2);
			}
			
			context.pixels[pixelIndex(x, y)] = color;
		}
	}

}
