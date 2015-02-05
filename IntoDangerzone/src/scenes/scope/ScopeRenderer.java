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

	private enum RenderMode {
		NORMAL, INVERSE;
	}

	private PApplet applet;
	private AudioSource audioSource;
	private float[] lBuffer;
	private float[] rBuffer;
	private float[] sumBuffer;
	PGraphics context;

	RenderMode renderMode;

	int width;
	int height;
	int scaleFactor;
	ArrayList<Pair<Vector2D, Vector2D>> divisions;
	private final int minimumScaleFactor = 20;
	private final int maximumScaleFactor = 500;

	public ScopeRenderer(PApplet applet, AudioSource audioSource) {
		super(applet);
		this.audioSource = audioSource;
		this.applet = applet;
		width = applet.width;
		height = applet.height;
		divisions = new ArrayList<>();
		scaleFactor = 50;
		renderMode = RenderMode.NORMAL;
	}

	@Override
	public void render() {
		applet.background(0);
		if (renderMode == RenderMode.INVERSE) {
			applet.background(255);
		}

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
			renderDivision(i);
		}
	}

	private void renderDivision(int divisionIndex) {
		Pair<Vector2D, Vector2D> division = divisions.get(divisionIndex);
		applet.stroke(255);
		if (renderMode == RenderMode.INVERSE) {
			applet.stroke(0);
		}

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
		} else {
			y0 = PApplet.map(z.y(), 0, 1, start.getY(), end.getY());
		}
		applet.translate(x0, y0);
		applet.rotate(heading);
		float[] buffer;
		switch (divisions.size()) {
		case 1:
			buffer = lBuffer;
			break;
		case 2:
			buffer = rBuffer;
			break;
		case 3:
			buffer = sumBuffer;
			break;
		default:
			return;
		}
		for (int i = 1; i < buffer.length; i++) {
			float x1 = PApplet.map(i, 0, buffer.length, 0,
					difference.getLength());
			float y1 = buffer[i] * scaleFactor;
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
		Vector2D start;
		Vector2D end;
		Pair<Vector2D, Vector2D> pair;
		switch (divisions.size()) {
		case 0:
			start = new Vector2D(0, parent.height / 4);
			end = new Vector2D(parent.width, parent.height / 4);
			pair = new Pair<Vector2D, Vector2D>(start, end);
			addPair(pair);
			break;
		case 1:
			start = new Vector2D(0, 3 * parent.height / 4);
			end = new Vector2D(parent.width, 3 * parent.height / 4);
			pair = new Pair<Vector2D, Vector2D>(start, end);
			addPair(pair);
			break;
		case 2:
			start = new Vector2D(0, parent.height / 2);
			end = new Vector2D(parent.width, parent.height / 2);
			pair = new Pair<Vector2D, Vector2D>(start, end);
			addPair(pair);
			break;
		default:
			break;
		}
	}

	public void distributePairs() {
		// TODO
	}

	public void removePair() {
		if (!divisions.isEmpty()) {
			divisions.remove(divisions.size() - 1);
		}
	}

	public void increaseScaling() {
		if (scaleFactor < maximumScaleFactor) {
			scaleFactor *= 1.1;
		}
	}

	public void decreaseScaling() {
		if (scaleFactor > minimumScaleFactor) {
			scaleFactor /= 1.1;
		}
	}

	public void flipRender() {
		switch (renderMode) {
		case NORMAL:
			renderMode = RenderMode.INVERSE;
			break;
		case INVERSE:
			renderMode = RenderMode.NORMAL;
			break;
		}
	}
}
