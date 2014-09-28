/**
 * Particles are points of mass without any collision bodies.
 */
public class Particle extends PhysicsObject {
	
	public Particle(Vector3D position) {
		this(position, 1.0f);
	}
	
	public Particle(Vector3D position, float mass) {
		super(position, mass);
	}
}