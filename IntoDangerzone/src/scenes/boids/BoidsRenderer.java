package scenes.boids;

import java.util.Random;

import processing.core.PApplet;

public class BoidsRenderer {

	public enum RenderMode {
		CIRCLE, TRIANGLE, LINE, PERPENDICULAR, SQUARE
	};

	private PApplet applet;
	private Flock flock;
	private Random rand;
	public RenderMode renderMode;
	private float boidSize;

	public BoidsRenderer(PApplet applet, Flock flock) {
		this.applet = applet;
		this.flock = flock;
		this.renderMode = RenderMode.SQUARE;
		this.rand = new Random();
		boidSize = flock.rules.weight * 8;
	}

	public void render() {
		boidSize = flock.rules.weight * 8;
		switch (renderMode) {
		case CIRCLE:
			renderCircles();
			break;
		case TRIANGLE:
			renderTriangles();
			break;
		case LINE:
			renderLines();
			break;
		case PERPENDICULAR:
			renderPerpendiculars();
			break;
		case SQUARE:
			renderSquares();
			break;
		default:
			break;
		}
	}

	public void setRenderMode(RenderMode renderMode) {
		this.renderMode = renderMode;
	}

	private void renderCircles() {
		applet.background(255);
		for (Boid b : flock.boids) {
			applet.fill(0, 0, 0);
			applet.stroke(0, 0, 0);
			applet.pushMatrix();
			applet.translate(-applet.width / 2, -applet.height / 2);
			applet.translate(b.location.getX(), b.location.getY());
			applet.ellipse(0, 0, boidSize, boidSize);
			applet.popMatrix();
		}
	}

	private void renderTriangles() {
		applet.background(255);
		for (Boid b : flock.boids) {
			float theta = (float) (b.velocity.getHeading() + Math.toRadians(90));
			applet.fill(0, 0, 0);
			applet.stroke(0, 0, 0);
			applet.pushMatrix();
			applet.translate(-applet.width / 2, -applet.height / 2);
			applet.translate(b.location.getX(), b.location.getY());
			applet.rotate(theta);
			applet.beginShape(processing.core.PShape.TRIANGLES);
			applet.vertex(0, -boidSize);
			applet.vertex(-boidSize / 2, boidSize / 2);
			applet.vertex(boidSize / 2, boidSize / 2);
			applet.endShape();
			applet.popMatrix();
		}
	}

	private void renderLines() {
		applet.background(255);
		for (Boid b : flock.boids) {
			float theta = (float) (b.velocity.getHeading() + Math.toRadians(90));
			applet.fill(0, 0, 0);
			applet.stroke(0, 0, 0);
			applet.pushMatrix();
			applet.translate(-applet.width / 2, -applet.height / 2);
			applet.translate(b.location.getX(), b.location.getY());
			applet.rotate(theta);
			applet.beginShape();
			applet.vertex(-boidSize / 4, -boidSize);
			applet.vertex(-boidSize / 4, boidSize);
			applet.vertex(boidSize / 4, boidSize);
			applet.vertex(boidSize / 4, -boidSize);
			applet.endShape();
			applet.popMatrix();
		}
	}

	private void renderPerpendiculars() {
		applet.background(255);
		for (Boid b : flock.boids) {
			float theta = (float) (b.velocity.getHeading() + Math.toRadians(90));
			applet.fill(0, 0, 0);
			applet.stroke(0, 0, 0);
			applet.pushMatrix();
			applet.translate(-applet.width / 2, -applet.height / 2);
			applet.translate(b.location.getX(), b.location.getY());
			applet.rotate(theta);
			applet.beginShape();
			applet.vertex(-boidSize, -boidSize / 4);
			applet.vertex(-boidSize, boidSize / 4);
			applet.vertex(boidSize, boidSize / 4);
			applet.vertex(boidSize, -boidSize / 4);
			applet.endShape();
			applet.popMatrix();
		}
	}

	private void renderSquares() {
		applet.background(255);
		applet.rectMode(applet.CENTER);
		for (Boid b : flock.boids) {
			applet.fill(0, 0, 0);
			applet.stroke(0, 0, 0);
			applet.pushMatrix();
			applet.translate(-applet.width / 2, -applet.height / 2);
			applet.translate(b.location.getX(), b.location.getY());
			applet.rect(0, 0, boidSize, boidSize);
			applet.popMatrix();
		}
		applet.rectMode(applet.CORNER);
	}

	void randomRenderingMode() {
		this.renderMode = RenderMode.values()[rand
				.nextInt(RenderMode.values().length)];
	}

}
