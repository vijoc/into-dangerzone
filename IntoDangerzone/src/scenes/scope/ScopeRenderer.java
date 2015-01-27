package scenes.scope;

import java.util.ArrayList;

import math.Complex;
import math.Vector2D;
import ddf.minim.AudioSource;
import graphics.Renderer;
import processing.core.PApplet;
import processing.core.PGraphics;
import util.Pair;

public class ScopeRenderer extends Renderer {

	private PApplet applet;
	private AudioSource audioSource;
	private float[] lBuffer;
	private float[] rBuffer;
	private float[] sumBuffer;
	PGraphics context;

	int width;
	int height;
	ArrayList<Pair<Vector2D, Vector2D>> divisions;

	public ScopeRenderer(PApplet applet, AudioSource audioSource) {
		super(applet);
		this.audioSource = audioSource;
		this.applet = applet;
		width = applet.width;
		height = applet.height;
		divisions = new ArrayList<>();
		Vector2D tlCorner = new Vector2D(0, 0);
		Vector2D brCorner = new Vector2D(width, height);
		Pair<Vector2D, Vector2D> mainDiagonal = new Pair<Vector2D, Vector2D>(
				tlCorner, brCorner);
		divisions.add(mainDiagonal);
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

		if (!divisions.isEmpty()) {
			renderDivisions();
		} else {
			renderNormal();
		}
	}

	private void renderDivisions() {
		for (int i = 0; i < divisions.size(); i++) {
			Pair<Vector2D, Vector2D> division = divisions.get(i);
			renderDivision(division);
		}
	}

	private void renderDivision(Pair<Vector2D, Vector2D> division) {
		Vector2D start = division.x;
		Vector2D end = division.y;
		Vector2D difference = end.subtract(start);
		float heading = difference.getHeading();

		applet.stroke(255);
		applet.translate(-width / 2, -height / 2); // we're in TL corner now

		for (int i = 0; i < sumBuffer.length - 1; i++) {
			float d = PApplet.map(i, 0, sumBuffer.length, 0, 1); // d [0, 1]

			Complex z = Complex.fromPolar(d, heading);

			float x1 = PApplet.map(z.x(), 0, 1, 0, difference.getX());
			float x2 = PApplet.map(z.x(), 0, 1, 0, difference.getX());
			float y1 = PApplet.map(z.y() + sumBuffer[i], -2, 2, 0,
					difference.getY());
			float y2 = PApplet.map(z.y() + sumBuffer[i + 1], -2, 2, 0,
					difference.getY());
			applet.line(x1, y1, x2, y2);
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
}
