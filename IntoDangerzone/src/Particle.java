import processing.core.*;

public class Particle {
	
	private Vector3D position;
	private Vector3D velocity;
	
	public Particle(Vector3D position) {
		this.position = position;
	}
	
	public Vector3D getPosition() {
		return position;
	}

	public void setPosition(Vector3D position) {
		this.position = position;
	}

	public Vector3D getVelocity() {
		return velocity;
	}
	
	public void setVelocity(Vector3D velocity) {
		this.velocity = velocity;
	}
	
	public void display(PApplet parent) {
		parent.pushMatrix();
		parent.translate(position.getX(), position.getY(), position.getZ());
		parent.stroke(255);
		parent.sphere(10);
		parent.popMatrix();
	}
}