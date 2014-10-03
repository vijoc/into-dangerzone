package math;

public class Quaternion {

	private float w, x, y, z;
	private float magnitude = -1;
	
	public static Quaternion add(Quaternion a, Quaternion b) {
		return new Quaternion(
				a.w + b.w, 
				a.x + b.x, 
				a.y + b.y,
				a.z + b.z);
	}
	
	public static Quaternion multiply(Quaternion a, Quaternion b) {
		Vector3D aVector = a.getXyzVector();
		Vector3D bVector = b.getXyzVector();
		
		return new Quaternion(
				a.w * b.w - Vector3D.dotProduct(aVector, bVector),
				Vector3D.crossProduct(aVector, bVector)
					.add(Vector3D.multiply(bVector, a.w))
					.add(Vector3D.multiply(aVector, b.w)));
	}
	
	public static Quaternion conjugate(Quaternion q) {
		return new Quaternion(q.w, q.getXyzVector().inverse());
	}
	
	public Quaternion(float w, Vector3D v) {
		this(w, v.getX(), v.getY(), v.getZ());
	}
	
	public Quaternion(float w, float x, float y, float z) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float getMagnitude() {
		if(magnitude < 0) {
			magnitude = (float) Math.sqrt(w*w + x*x + y*y + z*z);
		}
		return magnitude;
	}
	
	public float getW() {
		return this.w;
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	
	public float getZ() {
		return this.z;
	}
	
	public Vector3D getXyzVector() {
		return new Vector3D(x, y, z);
	}
	
}
