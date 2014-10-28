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
	
	PGraphics context;
	
	public JuliaSceneRenderer(PApplet parent) {
		super(parent);
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
		
		cx = (parent.mouseX - translateX) * shrink;
		cy = (parent.mouseY - translateY) * shrink;
		
		for(float x = 0; x < context.width; x++) {
			for(float y = 0; y < context.height; y++) {
				
				Complex z = new Complex(
						(x-translateX)*shrinkv,
						(y-translateY)*shrinkv
				);
				
				for(int i = 0; i < iterations; i++) {
					z = Complex.add(z.squared(), new Complex(cx, cy));
					
					if(Math.sqrt(z.squaredModule()) < 2) {
						context.pixels[(int) ( (context.width * y) + x)] = context.color(255-i*colorStep);
					}
				}
			}
		}
		context.updatePixels();
		context.endDraw();
		parent.image(context, -parent.width/2, -parent.height/2, context.width, context.height);
	}

}
