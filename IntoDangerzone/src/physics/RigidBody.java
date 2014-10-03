package physics;

import math.Quaternion;
import math.Vector3D;

public class RigidBody extends PhysicsObject {

	private Quaternion orientation;
	
	RigidBody() {
		super();
	}
	
	RigidBody(Vector3D position) {
		super(position);
	}
	
	RigidBody(Vector3D position, float mass, Quaternion orientation) {
		super(position, mass);
		
	}

}
