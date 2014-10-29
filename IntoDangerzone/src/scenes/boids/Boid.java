package scenes.boids;

import java.util.ArrayList;
import java.util.Random;

import math.Vector2D;
import math.Constants;

import java.lang.Math;

class Boid {
	
	Rules rules;
	private float width;
	private float height;

	Vector2D location;
	Vector2D velocity;
	Vector2D acceleration;

	Boid(float width, float height) {
		this.rules = new Rules();
		acceleration = new Vector2D(0, 0);
		this.width = width;
		this.height = height;

		Random rand = new Random();
		float angle = rand.nextFloat() * Constants.TWO_PI;
		velocity = new Vector2D((float) Math.cos(angle),
				(float) Math.sin(angle));
		
		float x = rand.nextFloat()*width;
		float y = rand.nextFloat()*height;
		this.location = new Vector2D(x, y);
	}

	void run(ArrayList<Boid> boids) {
		flock(boids);
		update();
		checkBoundaries();
	}

	void applyForce(Vector2D force) {
		acceleration = acceleration.add(force.scalarDivision(rules.weight));
	}

	void flock(ArrayList<Boid> boids) {
		Vector2D separation = separate(boids);
		Vector2D alignment = align(boids);
		Vector2D cohesion = cohesion(boids);

		// Weighing
		separation = separation.scalarMultiplication(rules.separationWeight);
		alignment = alignment.scalarMultiplication(rules.alignmentWeight);
		cohesion = cohesion.scalarMultiplication(rules.cohesionWeight);

		applyForce(separation);
		applyForce(alignment);
		applyForce(cohesion);
	}

	void update() {
		velocity = velocity.add(acceleration);
		if (velocity.getLength() > rules.maxSpeed) {
			velocity = velocity.toLength(rules.maxSpeed);
		}
		location = location.add(velocity);
		// Decrease acceleration
		acceleration = acceleration.scalarMultiplication(rules.deceleration);
	}

	Vector2D seek(Vector2D target) {
		Vector2D desired = target.subtract(location);
		desired = desired.normalize();
		desired = desired.scalarMultiplication(rules.maxSpeed);

		Vector2D steer = desired.subtract(velocity);
		if (steer.getLength() > rules.maxSteering) {
			steer = steer.toLength(rules.maxSteering);
		}
		return steer;
	}

	// TODO this not really belongs here
	void checkBoundaries() {
		if (location.getX() < -rules.boidSize)
			this.location = new Vector2D(width + rules.boidSize, this.location.getY());
		if (location.getY() < -rules.boidSize)
			this.location = new Vector2D(this.location.getX(), height + rules.boidSize);
		if (location.getX() > width + rules.boidSize)
			this.location = new Vector2D(-rules.boidSize, this.location.getY());
		if (location.getY() > height + rules.boidSize)
			this.location = new Vector2D(this.location.getX(), -rules.boidSize);
	}

	Vector2D separate(ArrayList<Boid> boids) {
		Vector2D steer = new Vector2D(0, 0);
		int count = 0;
		for (Boid other : boids) {
			float d = location.distanceTo(other.location);
			if ((d > 0) && (d < rules.desiredSeparation)) {
				Vector2D diff = location.subtract(other.location);
				diff = diff.normalize();
				diff = diff.scalarDivision(d); // weigh by distance
				steer = steer.add(diff);
				count++;
			}
		}
		if (count > 0) {
			steer = steer.scalarDivision((float) count);
		}

		if (steer.getLength() > 0) {
			steer = steer.normalize();
			steer = steer.scalarMultiplication(rules.maxSpeed);
			steer = steer.subtract(velocity);

			if (steer.getLength() > rules.maxSteering) {
				steer = steer.toLength(rules.maxSteering);
			}
		}
		return steer;
	}

	Vector2D align(ArrayList<Boid> boids) {
		Vector2D sum = new Vector2D(0, 0);
		int count = 0;
		for (Boid other : boids) {
			float d = location.distanceTo(other.location);
			if ((d > 0) && (d < rules.alignNeighborDist)) {
				sum = sum.add(other.velocity);
				count++;
			}
		}
		if (count > 0) {
			sum = sum.scalarDivision((float) count);
			sum = sum.normalize();
			sum = sum.scalarMultiplication(rules.maxSpeed);
			Vector2D steer = sum.subtract(velocity);
			if (steer.getLength() > rules.maxSteering) {
				steer = steer.toLength(rules.maxSteering);
			}
			return steer;
		} else {
			return new Vector2D(0, 0);
		}
	}

	Vector2D cohesion(ArrayList<Boid> boids) {
		Vector2D sum = new Vector2D(0, 0);

		int count = 0;
		for (Boid other : boids) {
			float d = location.distanceTo(other.location);
			if ((d > 0) && (d < rules.cohesionNeighborDist)) {
				sum = sum.add(other.location);
				count++;
			}
		}
		if (count > 0) {
			sum = sum.scalarDivision(count);
			return seek(sum);
		} else {
			return new Vector2D(0, 0);
		}
	}
}
