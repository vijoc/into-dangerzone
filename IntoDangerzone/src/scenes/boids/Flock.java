package scenes.boids;

import java.util.ArrayList;
import java.util.Random;

import math.Vector2D;
import processing.core.PApplet;

class Flock {
	ArrayList<Boid> boids;
	PApplet applet;
	Random rand;

	Flock(PApplet applet) {
		boids = new ArrayList<Boid>();
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
	}
	
	// TODO ugly hack with trying to get size to stay constant
	void newRules() {
		Rules rules = new Rules(boids.get(0).rules.boidSize);
		rules.randomizeSomething();
		
		for (Boid b : boids) {
			b.rules = rules;
		}
	}
	
	void setBoidSizes(float size){
		for (Boid b : boids) {
			b.rules.boidSize = size;
			b.rules.weight = size;
		}
	}
	
	void scaleBoidSizes(float scalingFactor){
		for (Boid b : boids) {
			b.rules.boidSize *= scalingFactor;
			b.rules.weight *= scalingFactor;
		}
	}

}
