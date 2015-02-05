package scenes.scope;

import math.Complex;
import math.Vector2D;
import ddf.minim.AudioSource;
import graphics.Renderer;
import processing.core.PApplet;
import processing.core.PGraphics;
import util.Pair;

import java.util.ArrayList;

public class ScopeRenderer extends Renderer {

	private PApplet applet;
	private AudioSource audioSource;
	private float[] lBuffer;
	private float[] rBuffer;
	private float[] sumBuffer;
	PGraphics context;

	int width;
	int height;
	int scaleFactor;
	ArrayList<Pair<Vector2D, Vector2D>> divisions;

	public ScopeRenderer(PApplet applet, AudioSource audioSource) {
		super(applet);
		this.audioSource = audioSource;
		this.applet = applet;
		width = applet.width;
		height = applet.height;
		divisions = new ArrayList<>();
		scaleFactor = 1000;
		// initPairs();
	}

	private void initPairs() {
		Vector2D tlCorner = new Vector2D(0, 0);
		Vector2D brCorner = new Vector2D(width, height);
		Pair<Vector2D, Vector2D> mainDiagonal = new Pair<Vector2D, Vector2D>(
				tlCorner, brCorner);
		divisions.add(mainDiagonal);

		Vector2D foo = new Vector2D(0, height);
		Vector2D bar = new Vector2D(width, 0);
		Pair<Vector2D, Vector2D> foobar = new Pair<Vector2D, Vector2D>(foo, bar);
		divisions.add(foobar);

		Vector2D iddqd = new Vector2D(width / 2, 0);
		Vector2D idkfa = new Vector2D(width / 2, height);
		Pair<Vector2D, Vector2D> doom = new Pair<Vector2D, Vector2D>(iddqd,
				idkfa);
		divisions.add(doom);
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

		renderDivisions();
	}

	private void renderDivisions() {
		for (int i = 0; i < divisions.size(); i++) {
			Pair<Vector2D, Vector2D> division = divisions.get(i);
			renderDivision(division);
		}
	}

	private void renderDivision(Pair<Vector2D, Vector2D> division) {
		applet.stroke(255);

		Vector2D start = division.x;
		Vector2D end = division.y;
		Vector2D difference = end.subtract(start);
		float heading = difference.getHeading();
		Complex w = Complex.fromPolar(1, heading);

		applet.pushMatrix();
		applet.translate(-width / 2, -height / 2);
		// We're now in top-left corner

		float d = 0;
		Complex z = Complex.fromPolar(d, heading);

		float x0;
		float y0;
		x0 = PApplet.map(z.x(), 0, w.x(), start.getX(), end.getX());
		if (heading != 0) {
			y0 = PApplet.map(z.y(), 0, w.y(), start.getY(), end.getY());
		}
		else {
			y0 = PApplet.map(z.y(), 0, 1, start.getY(), end.getY());
		}
		applet.translate(x0, y0);
		applet.rotate(heading);
		for (int i = 1; i < sumBuffer.length; i++) {
			float x1 = PApplet.map(i, 0, sumBuffer.length, 0,
					difference.getLength());
			float y1 = sumBuffer[i] * scaleFactor;
			applet.line(x0, y0, x1, y1);
			x0 = x1;
			y0 = y1;
		}
		applet.popMatrix();
	}

	public void addPair(Pair<Vector2D, Vector2D> pair) {
		this.divisions.add(pair);
	}

	public void addPair() {
		Vector2D start = new Vector2D(0, parent.height / 3);
		Vector2D end = new Vector2D(parent.width, parent.height / 3);
		Pair<Vector2D, Vector2D> pair = new Pair<Vector2D, Vector2D>(start,
				end);
		addPair(pair);
	}

	public void distributePairs() {
		// TODO
	}

	public void removePair() {
		if (!divisions.isEmpty()) {
			divisions.remove(divisions.size() - 1);
		}
	}
}
