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
	private float weightDecreaseSpeed;

	Flock(PApplet applet) {
		boids = new ArrayList<Boid>();
		this.applet = applet;
		width = applet.width;
		height = applet.height;
		this.rand = new Random();
		this.rules = new Rules();
		this.weightDecreaseSpeed = (rules.getMaxWeight() - rules.getMinWeight())
				/ maxBoids;
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
			rules.setWeight((float) (rules.getWeight() - weightDecreaseSpeed));
			b.setRules(rules);
			boids.add(b);
			System.out.println("number of boids: " + boids.size() + " weight: "
					+ boids.get(0).rules.getWeight());
		}
	}

	void newRules() {
		rules.randomizeSomething();

		for (Boid b : boids) {
			b.setRules(rules);
		}
	}

}
