package scenes.boids;

import processing.core.PApplet;

public class BoidsRenderer {

	private PApplet applet;
	private Flock flock;

	public BoidsRenderer(PApplet applet, Flock flock) {
		this.applet = applet;
		this.flock = flock;
	}

	public void render() {
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
			applet.vertex(0, -b.rules.boidSize * 10);
			applet.vertex(-b.rules.boidSize * 6, b.rules.boidSize * 6);
			applet.vertex(b.rules.boidSize * 6, b.rules.boidSize * 6);
			applet.endShape();
			applet.popMatrix();
		}
	}

}
