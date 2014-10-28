package math;

/**
 * Vector2D is an immutable vector in two-dimensional space.
 *
 */
public class Vector2D {
	private float x, y;
	private float length;

	public Vector2D(float x, float y) {
		this.x = x;
		this.y = y;
		this.length = (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * Return the x-coordinate of this vector.
	 * 
	 * @return the x-coordinate of this vector
	 */
	public float getX() {
		return x;
	}

	/**
	 * Return the y-coordinate of this vector.
	 * 
	 * @return the y-coordinate of this vector
	 */
	public float getY() {
		return y;
	}

	/**
	 * Normalize this vector and return the normalized version.
	 * 
	 * @return the normalized version of this vector.
	 */
	public Vector2D normalize() {
		if (length == 0.0)
			return this;
		return new Vector2D(x / length, y / length);
	}

	/**
	 * Returns the length of this vector.
	 * 
	 * @return the length of this vector.
	 */
	public float getLength() {
		return length;
	}

	/**
	 * Adds given vector to this vector and returns the resulting vector.
	 * 
	 * @param other
	 *            the vector to add to this
	 * @return the resulting vector
	 */
	public Vector2D add(Vector2D other) {
		return new Vector2D(x + other.x, y + other.y);
	}

	/**
	 * Subtracts given vector from this vector and returns the resulting vector.
	 * 
	 * @param other
	 *            the vector to subtract from this
	 * @return the resulting vector
	 */
	public Vector2D subtract(Vector2D other) {
		return new Vector2D(x - other.x, y - other.y);
	}

	/**
	 * Divides this vector by given scalar and returns the resulting vector.
	 * 
	 * @param scalar
	 *            the scalar to divide this vector by
	 * @return the resulting vector
	 */
	public Vector2D scalarDivision(float scalar) {
		return new Vector2D(x / scalar, y / scalar);
	}

	/**
	 * Multiplies this vector by given scalar and returns the resulting vector.
	 * 
	 * @param scalar
	 *            the scalar to multiply this vector by
	 * @return the resulting vector
	 */
	public Vector2D scalarMultiplication(float scalar) {
		return new Vector2D(x * scalar, y * scalar);
	}

	/**
	 * Calculates the dot product of this vector and given vector and returns
	 * the resulting scalar.
	 * 
	 * @param other
	 *            the right-hand side vector for the dot product
	 * @return the resulting scalar
	 */
	public float dotProduct(Vector2D other) {
		return (x * other.x) + (y * other.y);
	}

	/**
	 * Returns a vector that has the same direction as this, but magnitude set
	 * to a given scalar.
	 * 
	 * @param length
	 *            the length of the resulting vector
	 * @return the resulting vector
	 */
	public Vector2D toLength(float length) {
		return this.normalize().scalarMultiplication(length);
	}

	/**
	 * Returns a vector that has the same magnitude as this but points towards
	 * given position.
	 * 
	 * @param position
	 *            the position the resulting vector should point towards
	 * @return the resulting vector
	 */
	public Vector2D directedTowards(Vector2D position) {
		Vector2D directed = position.subtract(this);
		if (directed.length <= 0)
			return new Vector2D(0, 0);

		return position.subtract(this).toLength(this.getLength());
	}


	/**
	 * Returns vectors heading in radians.
	 * @return heading in radians
	 */
	public float getHeading() {
		return (float) java.lang.Math.atan2(y, x);
	}
	
	/**
	 * Returns distance to another vector.
	 * @param position
	 * @return distance to position
	 */
	public float distanceTo(Vector2D position) {
		Vector2D difference = position.subtract(this);
		return difference.getLength();
	}
}