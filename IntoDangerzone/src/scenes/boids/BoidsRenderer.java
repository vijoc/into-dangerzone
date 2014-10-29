package scenes.boids;

import java.util.Random;

import processing.core.PApplet;

public class BoidsRenderer {

	public enum RenderMode {
		CIRCLE, TRIANGLE, LINE, PERPENDICULAR
	};

	private PApplet applet;
	private Flock flock;
	private Random rand;
	public RenderMode renderMode;

	public BoidsRenderer(PApplet applet, Flock flock) {
		this.applet = applet;
		this.flock = flock;
		this.renderMode = RenderMode.PERPENDICULAR;
		this.rand = new Random();
	}

	public void render() {
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
			applet.ellipse(0, 0, b.rules.boidSize, b.rules.boidSize);
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
			applet.vertex(0, -b.rules.boidSize);
			applet.vertex(-b.rules.boidSize / 2, b.rules.boidSize / 2);
			applet.vertex(b.rules.boidSize / 2, b.rules.boidSize / 2);
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
			applet.vertex(-b.rules.boidSize / 4, -b.rules.boidSize);
			applet.vertex(-b.rules.boidSize / 4, b.rules.boidSize);
			applet.vertex(b.rules.boidSize / 4, b.rules.boidSize);
			applet.vertex(b.rules.boidSize / 4, -b.rules.boidSize);
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
			applet.vertex(-b.rules.boidSize, -b.rules.boidSize / 4);
			applet.vertex(-b.rules.boidSize, b.rules.boidSize / 4);
			applet.vertex(b.rules.boidSize, b.rules.boidSize / 4);
			applet.vertex(b.rules.boidSize, -b.rules.boidSize / 4);
			applet.endShape();
			applet.popMatrix();
		}
	}

	
	void randomRenderingMode(){
		this.renderMode = RenderMode.values()[rand.nextInt(RenderMode.values().length)];
	}

}
