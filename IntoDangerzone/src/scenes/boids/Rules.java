package scenes.boids;

import java.util.Random;

public class Rules {
	float weight = 1.0f;

	float maxSteering = 0.03f;
	float minSpeed = 0;
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

	Rules(float weight) {
		this.weight = weight;
	}

	// TODO fill this
	public void randomizeAll() {
	}

	public void randomizeSomething() {
		int parameter = rand.nextInt(2);
		switch (parameter) {
		case 0:
			this.maxSpeed = rand.nextFloat() * 10;
			this.minSpeed = rand.nextFloat() * maxSpeed;
			break;
		case 1:
			this.desiredSeparation = (float) (rand.nextFloat() * 50);
			break;
		case 2:
			this.separationWeight = 0.5f + rand.nextFloat() * 2.f;
			this.alignmentWeight = 0.5f + rand.nextFloat() * 1.f;
			this.cohesionWeight = 0.5f + rand.nextFloat() * 1.f;
			break;
		default:
			break;
		}
	}
}