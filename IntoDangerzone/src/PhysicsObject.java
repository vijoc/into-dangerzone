public abstract class PhysicsObject {
	
	// Primary
	private Vector3D position = new Vector3D(0,0,0);
	private Vector3D momentum = new Vector3D(0,0,0);
	private float mass;
	private float inverseMass;
	
	// Secondary
	private Vector3D velocity = new Vector3D(0,0,0);
	
	public PhysicsObject(Vector3D position) {
		this(position, 1.0f);
	}
	
	public PhysicsObject(Vector3D position, float mass) {
		if(mass <= 0.0f) throw new IllegalArgumentException("Mass must be positive.");
		this.position = position;
		this.mass = mass;
		this.inverseMass = 1.0f / mass;
	}
	
	public float getMass() {
		return mass;
	}
	
	public void setMass(float mass) {
		this.mass = mass;
		this.inverseMass = 1.0f / mass;
	}
	
	public float getInverseMass() {
		return this.inverseMass;
	}
	
	public Vector3D getPosition() {
		return this.position;
	}
	
	public void setPosition(Vector3D position) {
		this.position = position;
	}
	
	public Vector3D getMomentum() {
		return this.momentum;
	}
	
	public void setMomentum(Vector3D momentum) {
		this.momentum = momentum;
	}
	
	public void addMomentum(Vector3D momentum) {
		this.momentum = this.momentum.add(momentum);
	}
	
	public Vector3D getVelocity() {
		return this.velocity;
	}
	
	public void setVelocity(Vector3D velocity) {
		this.velocity = velocity;
	}
}
