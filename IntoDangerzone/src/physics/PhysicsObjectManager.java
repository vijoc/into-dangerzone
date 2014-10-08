package physics;

import java.util.ArrayList;

public class PhysicsObjectManager {
	
	private ArrayList<PhysicsObject> objects = new ArrayList<PhysicsObject>();
	
	public PhysicsObjectManager() {
		
	}
	
	public ArrayList<PhysicsObject> getObjects() {
		return this.objects;
	}
	
	void addObject(PhysicsObject object) {
		this.objects.add(object);
	}
	
}
