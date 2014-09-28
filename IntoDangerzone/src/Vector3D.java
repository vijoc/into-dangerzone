public class Vector3D {
	private float x, y, z;
	private float length;
	
	public Vector3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.length = (float) Math.sqrt(x*x + y*y + z*z);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}
	
	public Vector3D normalize() {
		if(length == 0.0) return this;
		return new Vector3D(x / length, y / length, z / length);
	}
	
	/**
	 * Returns the length of this vector.
	 * @return the length of this vector.
	 */
	public float getLength() {
		return length;
	}
	
	/**
	 * Adds given vector to this vector and returns the resulting vector.
	 * @param other
	 * @return
	 */
	public Vector3D add(Vector3D other) {
		return new Vector3D(x + other.x, y + other.y, z + other.z);
	}
	
	/**
	 * Subtracts given vector from this vector and returns the resulting vector.
	 * @param other
	 * @return
	 */
	public Vector3D subtract(Vector3D other) {
		return new Vector3D(x - other.x, y - other.y, z - other.z);
	}
	
	/**
	 * Divides this vector by given scalar and returns the resulting vector.
	 * @param scalar
	 * @return
	 */
	public Vector3D scalarDivision(float scalar) {
		return new Vector3D(x / scalar, y / scalar, z / scalar);
	}
	
	/**
	 * Multiplies this vector by given scalar and returns the resulting vector.
	 * @param scalar
	 * @return
	 */
	public Vector3D scalarMultiplication(float scalar) {
		return new Vector3D(x * scalar, y * scalar, z * scalar);
	}
	
	/**
	 * 
	 * @param length
	 * @return
	 */
	public Vector3D toLength(float length) {
		return this.scalarDivision(getLength()).scalarMultiplication(length);
	}
	
	public Vector3D crossProduct(Vector3D other) {
		return new Vector3D(
				(y*other.z - z*other.y),
				(z*other.x - x*other.z),
				(x*other.y - y*other.x));
	}
	
	public float dotProduct(Vector3D other) {
		return (x * other.x) + (y * other.y) + (z * other.z);
	}
	
	public Vector3D directedTowards(Vector3D position) {
		if(x == position.x && y == position.y && z == position.z) {
			return this;
		}
		
		return position.subtract(this).toLength(this.getLength());
	}
}