import processing.core.*;

public class Particle {
	
	private Vector3D position;
	private Vector3D velocity;
	
	private Particle following = null;
	
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
	
	public void follow(Particle other)
	{
		this.following = other;
	}
	
	public void advance() {
		Vector3D centerVelocity = position.directedTowards(new Vector3D(0,0,0)).scalarDivision(100);
		this.velocity = this.velocity.directedTowards(following.position);
		this.position = this.position.add(this.velocity).add(centerVelocity);
	}
	
	public void display(PApplet parent) {
		parent.pushMatrix();
		parent.translate(position.getX(), position.getY(), position.getZ());
		parent.stroke(255);
		parent.ellipse(0, 0, 10, 10);
		parent.popMatrix();
	}
}