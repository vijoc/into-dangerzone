import processing.core.PApplet;

public class Camera {	
	private PApplet parent;
	
	private float positionX, positionY, positionZ;
	private float centerX, centerY, centerZ;
	
	private float upX, upY, upZ;
	
	private float positionChangeRate = 10;
	
	private InputProvider<Boolean> 
		positionXIncrementEventProvider = new ConstantProvider<Boolean>(false),
		positionXDecrementEventProvider = new ConstantProvider<Boolean>(false),
		positionYIncrementEventProvider = new ConstantProvider<Boolean>(false),
		positionYDecrementEventProvider = new ConstantProvider<Boolean>(false),
		positionZIncrementEventProvider = new ConstantProvider<Boolean>(false),
		positionZDecrementEventProvider = new ConstantProvider<Boolean>(false);
	
	public Camera(PApplet parent) {
		this(parent, 
				new Vector3D(
						parent.width / 2.0f,
						parent.height/2.0f,
						(parent.height / 2.0f) / (float) Math.tan(Math.PI*30.0f / 180.0f)),
				new Vector3D(
						parent.width / 2.0f,
						parent.height / 2.0f,
						0));
	}
	
	public Camera(PApplet parent, Vector3D position, Vector3D center) {
		this(parent, position, center, 0, 1, 0);
	}
	
	public Camera(PApplet parent, Vector3D position, Vector3D center, 
			float upX, float upY, float upZ) {
		this.parent = parent;
		setPosition(position);
		setCenter(center);
		this.upX = upX;
		this.upY = upY;
		this.upZ = upZ;
	}
	
	public Vector3D getPosition() {
		return new Vector3D(positionX, positionY, positionZ);
	}
	
	public void setPosition(Vector3D position) {
		this.positionX = position.getX();
		this.positionY = position.getY();
		this.positionZ = position.getZ();
	}
	
	public Vector3D getCenter() {
		return new Vector3D(centerX, centerY, centerZ);
	}

	public void setCenter(Vector3D center) {
		this.centerX = center.getX();
		this.centerY = center.getY();
		this.centerZ = center.getZ();
	}
	
	public void setPositionX(float x) {
		this.positionX = x;
	}
	
	public void setPositionY(float y) {
		this.positionY = y;
	}
	
	public void setPositionZ(float z) {
		this.positionZ = z;
	}
	
	public InputProvider<Boolean> getPositionXIncrementEventProvider() {
		return positionXIncrementEventProvider;
	}
	
	public void setPositionXIncrementEventProvider(InputProvider<Boolean> provider) {
		this.positionXIncrementEventProvider = provider;
	}

	public InputProvider<Boolean> getPositionXDecrementEventProvider() {
		return positionXDecrementEventProvider;
	}

	public void setPositionXDecrementEventProvider(
			InputProvider<Boolean> positionXDecrementEventProvider) {
		this.positionXDecrementEventProvider = positionXDecrementEventProvider;
	}

	public InputProvider<Boolean> getPositionYIncrementEventProvider() {
		return positionYIncrementEventProvider;
	}

	public void setPositionYIncrementEventProvider(
			InputProvider<Boolean> positionYIncrementEventProvider) {
		this.positionYIncrementEventProvider = positionYIncrementEventProvider;
	}

	public InputProvider<Boolean> getPositionYDecrementEventProvider() {
		return positionYDecrementEventProvider;
	}

	public void setPositionYDecrementEventProvider(
			InputProvider<Boolean> positionYDecrementEventProvider) {
		this.positionYDecrementEventProvider = positionYDecrementEventProvider;
	}

	public InputProvider<Boolean> getPositionZIncrementEventProvider() {
		return positionZIncrementEventProvider;
	}

	public void setPositionZIncrementEventProvider(
			InputProvider<Boolean> positionZIncrementEventProvider) {
		this.positionZIncrementEventProvider = positionZIncrementEventProvider;
	}

	public InputProvider<Boolean> getPositionZDecrementEventProvider() {
		return positionZDecrementEventProvider;
	}

	public void setPositionZDecrementEventProvider(
			InputProvider<Boolean> positionZDecrementEventProvider) {
		this.positionZDecrementEventProvider = positionZDecrementEventProvider;
	}

	public void update() {
		updatePosition();
		parent.camera(
				positionX, positionY, positionZ,
				centerX, centerY, centerZ,
				upX, upY, upZ);
	}
	
	private void updatePosition() {
		if(positionXIncrementEventProvider.readInput()) {
			positionX += positionChangeRate;
		}
		
		if(positionXDecrementEventProvider.readInput()) {
			positionX -= positionChangeRate;
		}
		
		if(positionYIncrementEventProvider.readInput()) {
			positionY += positionChangeRate;
		}
		
		if(positionYDecrementEventProvider.readInput()) {
			positionY -= positionChangeRate;
		}
		
		if(positionZIncrementEventProvider.readInput()) {
			positionZ += positionChangeRate;
		}
		
		if(positionZDecrementEventProvider.readInput()) {
			positionZ -= positionChangeRate;
		}
	}
}
