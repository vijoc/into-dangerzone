package scenes.boids;

import java.util.ArrayList;
import java.util.Random;

import math.Vector2D;
import processing.core.PApplet;

class Flock {
	ArrayList<Boid> boids;
	PApplet applet;
	Random rand;
	Rules rules;

	Flock(PApplet applet) {
		boids = new ArrayList<Boid>();
		this.applet = applet;
		this.rand = new Random();
		this.rules = new Rules();
	}

	void run() {
		for (Boid b : boids) {
			b.run(boids);
		}
	}

	int getFlockSize() {
		return boids.size();
	}

	void addBoid(Boid b) {
		boids.add(b);
		b.setRules(rules);
	}

	// TODO ugly hack with trying to get size to stay constant
	void newRules() {
		Rules rules = new Rules(this.rules.weight);
		rules.randomizeSomething();

		for (Boid b : boids) {
			b.setRules(rules);
		}
	}

}
