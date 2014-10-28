package scenes.julia;

import java.util.Arrays;

import math.Complex;
import processing.core.PApplet;
import processing.core.PGraphics;
import graphics.Renderer;

public class JuliaSceneRenderer extends Renderer{

	int iterations = 10;
	float translateX;
	float translateY;
	float shrink;
	float shrinkv;
	float multi;
	float cx, cy;
	
	float colorStep;
	
	private JuliaSet set;
	
	PGraphics context;
	
	public JuliaSceneRenderer(PApplet parent, JuliaSet set) {
		super(parent);
		this.set = set;
		translateX = parent.width / 2;
		translateY = parent.height / 2;
		shrink = 2.0f / parent.width;
		shrinkv = shrink*2;
		context = parent.createGraphics(parent.width, parent.height);
		context.loadPixels();
		colorStep = 255 / iterations;
	}

	@Override
	public void render() {
		parent.background(255);
		
		context.beginDraw();		
		context.clear(); // Doesn't seem to work?
		Arrays.fill(context.pixels, context.color(255));
		
		for(float x = 0; x < context.width; x++) {
			for(float y = 0; y < context.height; y++) {
				
				Complex z = new Complex(
						(x-translateX)*shrinkv,
						(y-translateY)*shrinkv
				);
				
				int iter = set.calc(z);
				
				if(iter >= 0) {
					context.pixels[(int) (y*context.width + x)] = context.color(255 - iter*colorStep);
				}
			}
		}
		context.updatePixels();
		context.endDraw();
		parent.image(context, -parent.width/2, -parent.height/2, context.width, context.height);
	}

}
