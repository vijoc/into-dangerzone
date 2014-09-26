public class Vector3D {
	private float x, y, z;
	
	public Vector3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
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
	
	public Vector3D add(Vector3D other) {
		return new Vector3D(x + other.x, y + other.y, z + other.z);
	}
	
	public Vector3D subtract(Vector3D other)
	{
		return new Vector3D(x - other.x, y - other.y, z - other.z);
	}
	
	public Vector3D elementwiseDivision(float scalar) {
		return new Vector3D(x / scalar, y / scalar, z / scalar);
	}
	
	public Vector3D setDirectionTowards(Vector3D other) {
		float length =  (float) Math.sqrt(
				Math.pow(this.x, 2) + 
				Math.pow(this.y, 2) +
				Math.pow(this.z, 2));
		
		Vector3D direction = new Vector3D(
				other.x - x,
				other.y - y,
				other.z - z);
		
		float  dirLength = (float) Math.sqrt(
				Math.pow(direction.x, 2) + 
				Math.pow(direction.y, 2) +
				Math.pow(direction.z, 2));
		
		direction = new Vector3D((direction.x / dirLength), (direction.y / dirLength), (direction.z / dirLength ));
		return new Vector3D(direction.x * length, direction.y * length, direction.z * length);
	}
}
