import java.util.ArrayList;

/**
 * PhysicsEngine is responsible for stepping the physics simulation for 
 * registered PhysicsObjects.
 */
public class PhysicsEngine {

	private ArrayList<PhysicsObject> objects = new ArrayList<PhysicsObject>();
	
	public PhysicsEngine() {
		// TODO Auto-generated constructor stub
	}
	
	public void registerObject(PhysicsObject object) {
		objects.add(object);
	}
	
	public void step(float dt) {
		for(PhysicsObject object : this.objects) {
			// Update position: add (velocity*dt) to current position.
			// x = x + dx*dt
			Vector3D dx = object.getVelocity();
			Vector3D x = object.getPosition().add(dx.scalarMultiplication(dt));
			
			// Update velocity: add ([F/m]*dt) to current velocity.
			// v = v + dv*dt
			Vector3D dv = object.getMomentum().scalarMultiplication(object.getInverseMass());
			Vector3D v = object.getVelocity().add(dv.scalarMultiplication(dt));
			
			object.setPosition(x);
			object.setVelocity(v);
			object.setMomentum(new Vector3D(0,0,0));
		}
	}
}