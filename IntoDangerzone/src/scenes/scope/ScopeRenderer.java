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
	Mirroring mirroring;

	public ScopeRenderer(PApplet applet, AudioSource audioSource) {
		super(applet);
		this.audioSource = audioSource;
		this.applet = applet;
		width = applet.width;
		height = applet.height;
		mirroring = Mirroring.DIAGONAL;
	}

	private enum Mirroring {
		NONE, DIAGONAL, HORIZONTAL;
	}

	@Override
	public void render() {
		applet.background(0);
		lBuffer = audioSource.left.toArray();
		rBuffer = audioSource.right.toArray();
		sumBuffer = new float[lBuffer.length];

		for (int i = 0; i < lBuffer.length; i++) {
			sumBuffer[i] = lBuffer[i] + rBuffer[i];
		}

		switch (mirroring) {
		case NONE:
			renderNormal();
			break;
		case DIAGONAL:
			renderDiagonalMirror();
			break;
		case HORIZONTAL:
			renderHorizontalMirror();
			break;
		default:
			break;
		}
	}

	private void renderDiagonalMirror() {
		applet.translate(-width / 2, 0);
		applet.stroke(255);

		applet.translate(0, -height / 4);
		for (int i = 0; i < lBuffer.length - 1; i++) {
			float x = PApplet.map(i, 0, lBuffer.length, 0, applet.width);
			applet.line(x, lBuffer[i] * 1000, x, lBuffer[i + 1] * 1000);
			
			float x1 = PApplet.map(lBuffer[i], -1, 1, 0, applet.width);
			float y1 = PApplet.map(x, 0, applet.width, 0, applet.height);
			float x2 = PApplet.map(lBuffer[i+1], -1, 1, 0, applet.width);
			float y2 = PApplet.map(x, 0, applet.width, 0, applet.height);
			applet.line(x1, y1, x2, y2);
		}
	}

	private void renderHorizontalMirror() {
		applet.translate(-width / 2, 0);
		applet.stroke(255);

		applet.translate(0, -height / 4);
		for (int i = 0; i < lBuffer.length / 2 - 1; i++) {
			float x = PApplet.map(i, 0, lBuffer.length, 0, applet.width);
			applet.line(x, lBuffer[i] * 1000, x, lBuffer[i + 1] * 1000);
			applet.line(width - x, lBuffer[i] * 1000, width - x,
					lBuffer[i + 1] * 1000);
		}
		applet.translate(0, height / 2);
		for (int i = 0; i < rBuffer.length / 2 - 1; i++) {
			float x = PApplet.map(i, 0, rBuffer.length, 0, applet.width);
			applet.line(x, rBuffer[i] * 1000, x, rBuffer[i + 1] * 1000);
			applet.line(width - x, rBuffer[i] * 1000, width - x,
					rBuffer[i + 1] * 1000);
		}
		applet.translate(0, -height / 4);
		for (int i = 0; i < sumBuffer.length / 2 - 1; i++) {
			float x = PApplet.map(i, 0, sumBuffer.length, 0, applet.width);
			applet.line(x, sumBuffer[i] * 1000, x, sumBuffer[i + 1] * 1000);
			applet.line(width - x, sumBuffer[i] * 1000, width - x,
					sumBuffer[i + 1] * 1000);
		}
	}

	private void renderNormal() {
		applet.translate(-width / 2, 0);
		applet.stroke(255);

		applet.translate(0, -height / 4);
		for (int i = 0; i < lBuffer.length - 1; i++) {
			float x = PApplet.map(i, 0, lBuffer.length, 0, applet.width);
			applet.line(x, lBuffer[i] * 1000, x, lBuffer[i + 1] * 1000);
		}
		applet.translate(0, height / 2);
		for (int i = 0; i < rBuffer.length - 1; i++) {
			float x = PApplet.map(i, 0, rBuffer.length, 0, applet.width);
			applet.line(x, rBuffer[i] * 1000, x, rBuffer[i + 1] * 1000);
		}
		applet.translate(0, -height / 4);
		for (int i = 0; i < sumBuffer.length - 1; i++) {
			float x = PApplet.map(i, 0, sumBuffer.length, 0, applet.width);
			applet.line(x, sumBuffer[i] * 1000, x, sumBuffer[i + 1] * 1000);
		}
	}

	private void mirrorLeft() {
		applet.loadPixels();
		for (int x = 0; x < width / 2; x++) {
			for (int y = 0; y < height; y++) {
				applet.pixels[x + y * width] = applet.pixels[(width - x) + y
						* width - 1];
			}
		}
		applet.updatePixels();
	}

	private void mirrorTop() {
		applet.loadPixels();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height / 2; y++) {
				applet.pixels[x * height + y] = applet.pixels[x * height
						+ (height - y) - 1];
			}
		}
		applet.updatePixels();
	}
}
