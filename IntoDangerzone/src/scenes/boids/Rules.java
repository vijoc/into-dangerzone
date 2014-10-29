package scenes.boids;

import java.util.Random;

public class Rules {
	float boidSize = 1.0f;
	float weight = 1.0f;

	float maxSteering = 0.03f;
	float maxSpeed = 2;

	float desiredSeparation = 25;
	float alignNeighborDist = 50;
	float cohesionNeighborDist = 50;

	float separationWeight = 1.5f;
	float alignmentWeight = 1.0f;
	float cohesionWeight = 1.0f;
	float deceleration = 0.3f; // must be between [0, 1)

	static Random rand = new Random();

	public static final Rules DEFAULT_RULES = new Rules();

	Rules() {
	}

	Rules(float size) {
		this.boidSize = size;
		this.weight = size;
	}

	// TODO fill this
	public void randomizeAll() {
	}

	public void randomizeSomething() {
		int parameter = rand.nextInt(10);
		switch (parameter) {
		case 0:
			this.maxSpeed = rand.nextFloat() * 10;
			break;
		case 1:
			this.desiredSeparation = (float) (Math.sqrt(rand.nextFloat()) * 50);
			break;
		case 2:
			this.alignNeighborDist = (float) (Math.sqrt(rand.nextFloat()) * 75);
			this.cohesionNeighborDist = (float) (Math.sqrt(rand.nextFloat()) * 75);
			break;
		case 3:
			this.separationWeight = rand.nextFloat() * 2.5f;
			this.alignmentWeight = rand.nextFloat() * 2.5f;
			this.cohesionWeight = rand.nextFloat() * 2.5f;
			break;
		default:
			break;
		}
	}
}