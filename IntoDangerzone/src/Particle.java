import processing.core.*;

public class Particle extends PhysicsObject {
	
	public Particle(Vector3D position) {
		this(position, 1.0f);
	}
	
	public Particle(Vector3D position, float mass) {
		super(position, mass);
	}
	
	public void display(PApplet parent) {
		parent.pushMatrix();
		Vector3D position = getPosition();
		parent.translate(position.getX(), position.getY(), position.getZ());
		parent.stroke(255);
		parent.ellipse(0, 0, 10, 10);
		parent.popMatrix();
	}
}