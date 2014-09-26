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
	
}
