package scenes.boids;

import java.util.ArrayList;
import java.util.Random;

import math.Vector2D;
import math.Constants;

import java.lang.Math;

class Boid {

	Vector2D location;
	Vector2D velocity;
	Vector2D acceleration;
	float r;
	float maxSteering;
	float maxSpeed;
	private float width;
	private float height;
	private float desiredSeparation = 25;

	Boid(float x, float y, float width, float height) {
		acceleration = new Vector2D(0, 0);
		this.width = width;
		this.height = height;

		Random rand = new Random();
		float angle = rand.nextFloat() * Constants.TWO_PI;
		velocity = new Vector2D((float) Math.cos(angle),
				(float) Math.sin(angle));

		location = new Vector2D(x, y);
		r = 2.0f;
		maxSpeed = 2;
		maxSteering = 0.03f;
	}

	void run(ArrayList<Boid> boids) {
		flock(boids);
		update();
		checkBoundaries();
	}

	void applyForce(Vector2D force) {
		// TODO add mass here?
		acceleration = acceleration.add(force);
	}

	void flock(ArrayList<Boid> boids) {
		Vector2D separation = separate(boids);
		Vector2D alignment = align(boids);
		Vector2D cohesion = cohesion(boids);

		// Weighing
		separation = separation.scalarMultiplication(1.5f);
		alignment = alignment.scalarMultiplication(1.0f);
		cohesion = cohesion.scalarMultiplication(1.0f);

		applyForce(separation);
		applyForce(alignment);
		applyForce(cohesion);
	}

	void update() {
		if (velocity.getLength() < maxSpeed)
			velocity = velocity.add(acceleration);
		location = location.add(velocity);
		// Reset acceleration to 0 each cycle
		acceleration = acceleration.scalarMultiplication(0);
	}

	Vector2D seek(Vector2D target) {
		Vector2D desired = target.subtract(location);
		desired = desired.normalize();
		desired = desired.scalarMultiplication(maxSpeed);

		Vector2D steer = desired.subtract(velocity);
		if (steer.getLength() > maxSteering) {
			steer = steer.toLength(maxSteering);
		}
		return steer;
	}

	// TODO this not really belong here
	void checkBoundaries() {
		if (location.getX() < -r)
			this.location = new Vector2D(width + r, this.location.getY());
		if (location.getY() < -r)
			this.location = new Vector2D(this.location.getX(), height + r);
		if (location.getX() > width + r)
			this.location = new Vector2D(-r, this.location.getY());
		if (location.getY() > height + r)
			this.location = new Vector2D(this.location.getX(), -r);
	}

	Vector2D separate(ArrayList<Boid> boids) {
		Vector2D steer = new Vector2D(0, 0);
		int count = 0;
		for (Boid other : boids) {
			float d = location.distanceTo(other.location);
			if ((d > 0) && (d < desiredSeparation)) {
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
			steer = steer.scalarMultiplication(maxSpeed);
			steer = steer.subtract(velocity);

			if (steer.getLength() > maxSteering) {
				steer = steer.toLength(maxSteering);
			}
		}
		return steer;
	}

	Vector2D align(ArrayList<Boid> boids) {
		float neighbordist = 50;
		Vector2D sum = new Vector2D(0, 0);
		int count = 0;
		for (Boid other : boids) {
			float d = location.distanceTo(other.location);
			if ((d > 0) && (d < neighbordist)) {
				sum = sum.add(other.velocity);
				count++;
			}
		}
		if (count > 0) {
			sum = sum.scalarDivision((float) count);
			sum = sum.normalize();
			sum = sum.scalarMultiplication(maxSpeed);
			Vector2D steer = sum.subtract(velocity);
			if (steer.getLength() > maxSteering) {
				steer = steer.toLength(maxSteering);
			}
			return steer;
		} else {
			return new Vector2D(0, 0);
		}
	}

	Vector2D cohesion(ArrayList<Boid> boids) {
		float neighbordist = 50;
		Vector2D sum = new Vector2D(0, 0);

		int count = 0;
		for (Boid other : boids) {
			float d = location.distanceTo(other.location);
			if ((d > 0) && (d < neighbordist)) {
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
