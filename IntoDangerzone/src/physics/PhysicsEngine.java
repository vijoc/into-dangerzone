package physics;
import java.util.ArrayList;

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
		for(PhysicsObject object : this.objectManager.getObjects()) {
			// We're using semi-implicit Euler integration, which means that we're using 
			// the "future" velocity (velocity at subsequent time step) to integrate the 
			// position at the next time step. This is in contrast to using the current 
			// velocity for determining the next position, which would be explicit Euler
			// integration.
			
			// a = F/m = F * (1/m)
			Vector3D a = object.getForce().scalarMultiplication(object.getInverseMass());
			
			// v(t+dt) = v(t) * a(t+dt)*dt
			Vector3D v = object.getVelocity()
					.add(a.scalarMultiplication(dt))
					.add(object.getImpulse().scalarMultiplication(object.getInverseMass()));
			
			// x(t+dt) = x(t) + v(t+dt)*dt
			Vector3D x = object.getPosition().add(v.scalarMultiplication(dt));
			
			object.setPosition(x);
			object.setVelocity(v);
			
			// External forces are cleared on every time step.
			object.setImpulse(new Vector3D(0,0,0));
			object.setForce(new Vector3D(0,0,0));
		}
	}
}