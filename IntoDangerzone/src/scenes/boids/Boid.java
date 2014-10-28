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
	float boidSize = 1.0f;
	float weight = 1.0f;
	float maxSteering = 0.03f;
	float maxSpeed = 2;
	private float width;
	private float height;

	private float desiredSeparation = 25;
	private float alignNeighborDist = 50;
	private float cohesionNeighborDist = 50;

	private float separationWeight = 1.5f;
	private float alignmentWeight = 1.0f;
	private float cohesionWeight = 1.0f;

	private float deceleration = 0.3f; // must be between [0, 1)

	Boid(float x, float y, float width, float height) {
		acceleration = new Vector2D(0, 0);
		this.width = width;
		this.height = height;

		Random rand = new Random();
		float angle = rand.nextFloat() * Constants.TWO_PI;
		velocity = new Vector2D((float) Math.cos(angle),
				(float) Math.sin(angle));

		location = new Vector2D(x, y);
	}

	void run(ArrayList<Boid> boids) {
		flock(boids);
		update();
		checkBoundaries();
	}

	void applyForce(Vector2D force) {
		acceleration = acceleration.add(force.scalarDivision(weight));
	}

	void flock(ArrayList<Boid> boids) {
		Vector2D separation = separate(boids);
		Vector2D alignment = align(boids);
		Vector2D cohesion = cohesion(boids);

		// Weighing
		separation = separation.scalarMultiplication(separationWeight);
		alignment = alignment.scalarMultiplication(alignmentWeight);
		cohesion = cohesion.scalarMultiplication(cohesionWeight);

		applyForce(separation);
		applyForce(alignment);
		applyForce(cohesion);
	}

	void update() {
		velocity = velocity.add(acceleration);
		if (velocity.getLength() > maxSpeed) {
			velocity = velocity.toLength(maxSpeed);
		}
		location = location.add(velocity);
		// Decrease acceleration
		acceleration = acceleration.scalarMultiplication(deceleration);
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

	// TODO this not really belongs here
	void checkBoundaries() {
		if (location.getX() < -boidSize)
			this.location = new Vector2D(width + boidSize, this.location.getY());
		if (location.getY() < -boidSize)
			this.location = new Vector2D(this.location.getX(), height + boidSize);
		if (location.getX() > width + boidSize)
			this.location = new Vector2D(-boidSize, this.location.getY());
		if (location.getY() > height + boidSize)
			this.location = new Vector2D(this.location.getX(), -boidSize);
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
		Vector2D sum = new Vector2D(0, 0);
		int count = 0;
		for (Boid other : boids) {
			float d = location.distanceTo(other.location);
			if ((d > 0) && (d < alignNeighborDist)) {
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
		Vector2D sum = new Vector2D(0, 0);

		int count = 0;
		for (Boid other : boids) {
			float d = location.distanceTo(other.location);
			if ((d > 0) && (d < cohesionNeighborDist)) {
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
