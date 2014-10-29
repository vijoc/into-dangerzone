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

	Rules(){
	}
	
	Rules(float size){
		this.boidSize = size;
		this.weight = size;
	}

	public void randomizeAll() {
		this.randomizeMaxSpeed();
	}

	public void randomizeMaxSpeed() {
		this.maxSpeed = rand.nextFloat()*10;
	}
	
	
}