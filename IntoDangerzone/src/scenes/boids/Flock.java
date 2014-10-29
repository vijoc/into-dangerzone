package scenes.boids;

import java.util.ArrayList;
import java.util.Random;

import processing.core.PApplet;

class Flock {
	ArrayList<Boid> boids;
	PApplet applet;
	Random rand;
	Rules rules;
	int width;
	int height;
	private int maxBoids = 500;

	Flock(PApplet applet) {
		boids = new ArrayList<Boid>();
		this.applet = applet;
		width = applet.width;
		height = applet.height;
		this.rand = new Random();
		this.rules = new Rules();
	}

	void initialize() {
		for (int i = 0; i < 1; i++) {
			boids.add(new Boid(width, height));
		}
	}

	void run() {
		for (Boid b : boids) {
			b.run(boids);
		}
	}

	int getFlockSize() {
		return boids.size();
	}

	void newBoid() {
		if (boids.size() < maxBoids) {
			Boid b = new Boid(width, height);
			rules.setWeight(rules.getWeight() * 0.999f);
			b.setRules(rules);
			boids.add(b);
		}
	}

	void newRules() {
		rules.randomizeSomething();

		for (Boid b : boids) {
			b.setRules(rules);
		}
	}

}
