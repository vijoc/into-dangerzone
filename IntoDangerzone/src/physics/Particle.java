package physics;
import math.Vector3D;

/**
 * Particles are points of mass without any collision bodies.
 */
public class Particle extends PhysicsObject {
	
	Particle() {
		
	}
	
	Particle(Vector3D position) {
		this(position, 1.0f);
	}
	
	Particle(Vector3D position, float mass) {
		super(position, mass);
	}
}