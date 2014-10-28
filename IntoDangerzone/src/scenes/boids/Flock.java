package scenes.boids;

import java.util.ArrayList;
import java.util.Random;

import math.Vector2D;
import processing.core.PApplet;

class Flock {
	ArrayList<Boid> boids; // An ArrayList for all the boids
	PApplet applet;
	Random rand;

	Flock(PApplet applet) {
		boids = new ArrayList<Boid>(); // Initialize the ArrayList
		this.applet = applet;
		this.rand = new Random();
	}

	void run() {
		for (Boid b : boids) {
			b.run(boids);
		}
	}

	void render() {
		for (Boid b : boids) {
			float theta = (float) (b.velocity.getHeading() + Math.toRadians(90));
			applet.fill(0, 0, 0);
			applet.stroke(0, 0, 0);
			applet.pushMatrix();
			applet.translate(-applet.width / 2, -applet.height / 2);
			applet.translate(b.location.getX(), b.location.getY());
			applet.rotate(theta);
			applet.beginShape(processing.core.PShape.TRIANGLES);
			applet.vertex(0, -b.boidSize * 10);
			applet.vertex(-b.boidSize * 6, b.boidSize * 6);
			applet.vertex(b.boidSize * 6, b.boidSize * 6);
			applet.endShape();
			applet.popMatrix();
		}
	}

	void disturb() {
		for (Boid b : boids) {
			Vector2D random = new Vector2D(rand.nextFloat() * 100,
					rand.nextFloat() * 100);
			b.applyForce(random);
		}
	}

	void addBoid(Boid b) {
		boids.add(b);
		float f = 2*rand.nextFloat();
		b.weight = f;
		b.boidSize = f;
	}

}
