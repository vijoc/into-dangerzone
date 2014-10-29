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

	void disturb() {
		for (Boid b : boids) {
			Vector2D random = new Vector2D(rand.nextFloat() * 100,
					rand.nextFloat() * 100);
			b.applyForce(random);
		}
	}
	
	int getFlockSize(){
		return boids.size();
	}

	void addBoid(Boid b) {
		boids.add(b);
		float f = 1;//2*rand.nextFloat();
		b.weight = f;
		b.boidSize = f;
	}
	
	// TODO more elaborate randomization, parameterization etc
	void newRules() {
		float size = rand.nextFloat()*4;
		for (Boid b : boids) {
			b.boidSize = size;
			b.weight = size;
		}
	}

}
