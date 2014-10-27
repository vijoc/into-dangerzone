package scenes.boids;

import java.util.ArrayList;

import processing.core.PApplet;

class Flock {
	ArrayList<Boid> boids; // An ArrayList for all the boids
	PApplet applet;

	Flock(PApplet applet) {
		boids = new ArrayList<Boid>(); // Initialize the ArrayList
		this.applet = applet;
	}

	void run() {
		for (Boid b : boids) {
			b.run(boids);
		}
	}

	void render() {
		for (Boid b : boids) {
			float theta = (float) (b.velocity.getHeading() + Math.toRadians(90));
			applet.fill(0);
			applet.stroke(0);
			applet.pushMatrix();
			applet.translate(-applet.width/2, -applet.height/2);
			applet.translate(b.location.getX(), b.location.getY());
			applet.rotate(theta);
			applet.beginShape(processing.core.PShape.TRIANGLES);
			applet.vertex(0, -b.r * 2);
			applet.vertex(-b.r, b.r * 2);
			applet.vertex(b.r, b.r * 2);
			applet.endShape();
			applet.popMatrix();
		}
		applet.text("boids: " + boids.size(), 0, 0);
	}

	void addBoid(Boid b) {
		boids.add(b);
	}

}
