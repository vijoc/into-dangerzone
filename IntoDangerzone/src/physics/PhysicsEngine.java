package physics;

import math.Vector3D;

/**
 * PhysicsEngine is responsible for stepping the physics simulation for 
 * registered PhysicsObjects.
 */
public class PhysicsEngine {

	private PhysicsObjectManager objectManager;
	
	public PhysicsEngine() {
		this(new PhysicsObjectManager());
	}
	
	public PhysicsEngine(PhysicsObjectManager objectManager) {
		this.objectManager = objectManager;
	}
	
	public PhysicsObjectManager getPhysicsObjectManager() {
		return objectManager;
	}
	
	public void step(float dt) {
		stepParticles(dt); // Particles don't participate in collisions
		// TODO detect and resolve collisions
		stepRigidBodies(dt);
	}
	
	private void stepRigidBodies(float dt) {
		for(RigidBody body : this.objectManager.getRigidBodies()) {
			Vector3D v = integrateVelocity(body, dt);
			Vector3D x = integratePosition(body, v, dt);
			
			body.setVelocity(v);
			body.setPosition(x);
			
			body.setImpulse(new Vector3D(0,0,0));
			body.setForce(new Vector3D(0,0,0));
		}
	}
	
	private void stepParticles(float dt) {
		for(Particle object : this.objectManager.getParticles()) {
			Vector3D v = integrateVelocity(object, dt);
			Vector3D x = integratePosition(object, v, dt);
			
			object.setPosition(x);
			object.setVelocity(v);
			
			object.setImpulse(new Vector3D(0,0,0));
			object.setForce(new Vector3D(0,0,0));
		}
	}
	
	private Vector3D integrateVelocity(PhysicsObject object, float dt) {
		Vector3D a = Vector3D.multiply(object.getForce(), object.getInverseMass());
		Vector3D impulse = Vector3D.multiply(object.getImpulse(), object.getInverseMass());
		return integrate(object.getVelocity(), a, dt).add(impulse);
	}
	
	private Vector3D integratePosition(PhysicsObject object, Vector3D velocity, float dt) {
		return integrate(object.getPosition(), velocity, dt);
	}
	
	private Vector3D integrate(Vector3D x, Vector3D dx, float dt) {
		return Vector3D.add(x, dx.scalarMultiplication(dt));
	}
}