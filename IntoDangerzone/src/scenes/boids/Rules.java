package scenes.boids;

import java.util.Random;

public class Rules {
	private final float minWeight = 0.5f;
	private final float maxWeight = 8.f;

	private float weight = 1.0f;

	private float maxSteering = 0.03f;
	private float minSpeed = 0;
	private float maxSpeed = 2;

	private float desiredSeparation = 25;
	private float alignNeighborDist = 50;
	private float cohesionNeighborDist = 50;

	private float separationWeight = 1.5f;
	private float alignmentWeight = 1.0f;
	private float cohesionWeight = 1.0f;
	private final float deceleration = 0.3f; // must be between [0, 1)

	static Random rand = new Random();

	public static final Rules DEFAULT_RULES = new Rules();

	Rules() {
	}

	Rules(float weight) {
		this.setWeight(weight);
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		if (weight > minWeight && weight < maxWeight)
			this.weight = weight;
	}

	public float getMaxSteering() {
		return maxSteering;
	}

	public void setMaxSteering(float maxSteering) {
		this.maxSteering = maxSteering;
	}

	public float getDeceleration() {
		return deceleration;
	}

	public float getMinSpeed() {
		return minSpeed;
	}

	public void setMinSpeed(float minSpeed) {
		this.minSpeed = minSpeed;
	}

	public void randomizeSomething() {
		int parameter = rand.nextInt(3);
		switch (parameter) {
		case 0:
			this.setMaxSpeed(rand.nextFloat() * 10);
			this.setMinSpeed(rand.nextFloat() * getMaxSpeed());
			break;
		case 1:
			this.setDesiredSeparation((float) (rand.nextFloat() * 50));
			break;
		case 2:
			this.setSeparationWeight(0.5f + rand.nextFloat() * 2.f);
			this.setAlignmentWeight(0.5f + rand.nextFloat() * 1.f);
			this.setCohesionWeight(0.5f + rand.nextFloat() * 1.f);
			break;
		default:
			break;
		}
	}

	public float getDesiredSeparation() {
		return desiredSeparation;
	}

	public void setDesiredSeparation(float desiredSeparation) {
		this.desiredSeparation = desiredSeparation;
	}

	public float getAlignNeighborDist() {
		return alignNeighborDist;
	}

	public void setAlignNeighborDist(float alignNeighborDist) {
		this.alignNeighborDist = alignNeighborDist;
	}

	public float getCohesionNeighborDist() {
		return cohesionNeighborDist;
	}

	public void setCohesionNeighborDist(float cohesionNeighborDist) {
		this.cohesionNeighborDist = cohesionNeighborDist;
	}

	public float getSeparationWeight() {
		return separationWeight;
	}

	public void setSeparationWeight(float separationWeight) {
		this.separationWeight = separationWeight;
	}

	public float getAlignmentWeight() {
		return alignmentWeight;
	}

	public void setAlignmentWeight(float alignmentWeight) {
		this.alignmentWeight = alignmentWeight;
	}

	public float getCohesionWeight() {
		return cohesionWeight;
	}

	public void setCohesionWeight(float cohesionWeight) {
		this.cohesionWeight = cohesionWeight;
	}

	public float getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
}