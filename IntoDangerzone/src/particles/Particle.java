package particles;
import physics.PhysicsObject;
import physics.PhysicsObjectManager;
import math.Vector3D;

/**
 * Particles are points of mass without any collision bodies.
 */
public class Particle extends PhysicsObject {
	
	public Particle(PhysicsObjectManager manager, Vector3D position) {
		this(manager, position, 1.0f);
	}
	
	public Particle(PhysicsObjectManager manager, Vector3D position, float mass) {
		super(manager, position, mass);
	}
}