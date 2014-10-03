package physics;

import java.util.ArrayList;

import math.Vector3D;

public class PhysicsObjectManager {
	
	private ArrayList<Particle> particles = new ArrayList<Particle>();
	private ArrayList<RigidBody> bodies = new ArrayList<RigidBody>();
	
	public PhysicsObjectManager() {
		
	}
	
	/**
	 * Create and return a new RigidBody.
	 * @return the created RigidBody
	 */
	public RigidBody createRigidBody() {
		RigidBody body = new RigidBody();
		registerRigidBody(body);
		return body;
	}
	
	/**
	 * Destroy a previously created RigidBody in the sense that it's no longer tracked 
	 * by the PhysicsObjectManager.
	 * @param body RigidBody to destroy
	 */
	public void destroyBody(RigidBody body) {
		this.bodies.remove(body);
	}
	
	/**
	 * Create and return a new Particle.
	 * @return the created particle
	 */
	public Particle createParticle() {
		return createParticle(new Vector3D(0, 0, 0), 1.0f);
	}
	
	/**
	 * Create and return a new Particle with an initial position.
	 * @param initialPosition where the particle should be created
	 * @return the created particle
	 */
	public Particle createParticle(Vector3D initialPosition) {
		return createParticle(initialPosition, 1.0f);
	}
	
	/**
	 * Create and return a new Particle with an initial position and given mass.
	 * @param initialPosition where the particle should be created
	 * @param mass the amount of mass the particle should be given
	 * @return the created particle
	 */
	public Particle createParticle(Vector3D initialPosition, float mass) {
		Particle particle = new Particle(initialPosition, mass);
		registerParticle(particle);
		return particle;
	}
	
	/**
	 * Destroy a previously created Particle in the sense that it's no longer tracked 
	 * by the PhysicsObjectManager.
	 * @param particle Particle to destroy
	 */
	public void destroyParticle(Particle particle) {
		this.particles.remove(particle);
	}
	
	ArrayList<Particle> getParticles() {
		return this.particles;
	}
	
	ArrayList<RigidBody> getRigidBodies() {
		return this.bodies;
	}
	
	private void registerRigidBody(RigidBody body) {
		this.bodies.add(body);
	}
	
	private void registerParticle(Particle object) {
		this.particles.add(object);
	}
}
