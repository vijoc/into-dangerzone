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
	
	public enum ColorMode {
		GRAYSCALE, BLUE_YELLOW
	}
	
	private RenderMode mode = RenderMode.STEPPED;
	private ColorMode colorMode = ColorMode.GRAYSCALE;

	float translateX;
	float translateY;
	float shrink;
	float shrinkv;
	
	float red = 255, green = 255, blue = 255;
	
	float colorStep;
	
	private JuliaSet set;
	
	private boolean renderDebug;
	
	private int firstColor;
	private int secondColor;
	private int thirdColor;
	
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
		setColorMode(colorMode);
	}

	@Override
	public void render() {
		parent.colorMode(PConstants.RGB, 255.0f);
		//parent.background(255);
		
		context.beginDraw();
		//context.clear(); // Doesn't seem to work?
		Arrays.fill(context.pixels, firstColor);
		
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
	
	public void toggleMode() {
		switch(mode) {
		case SOLID:
			this.mode = RenderMode.STEPPED;
			break;
		case STEPPED:
			this.mode = RenderMode.SOLID;
			break;
		}
	}
	
	public void setDebug(boolean d) {
		this.renderDebug = d;
	}
	
	public void toggleDebug() {
		setDebug(!renderDebug);
	}
	
	public void setColorMode(ColorMode cMode) {
		switch(cMode) {
		case GRAYSCALE:
			firstColor = parent.color(255);
			secondColor = parent.color(0);
			thirdColor = parent.color(255);
			break;
		case BLUE_YELLOW:
			firstColor = parent.color(0, 0, 100);
			secondColor = parent.color(125, 125, 0);
			thirdColor = parent.color(255);
		}
		this.colorMode = cMode;
	}
	
	public void toggleColorMode() {
		switch(colorMode) {
		case GRAYSCALE:
			setColorMode(ColorMode.BLUE_YELLOW);
			break;
		case BLUE_YELLOW:
			setColorMode(ColorMode.GRAYSCALE);
			break;
		}
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
				color = context.lerpColor(firstColor, secondColor, (val*2));
			} else {
				color = context.lerpColor(secondColor, thirdColor, (val-0.5f)*2);
			}
			
			context.pixels[pixelIndex(x, y)] = color;
		}
	}

}
