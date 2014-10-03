package particles;
import math.Vector3D;
import physics.Particle;
import processing.core.PApplet;

public class ParticleEllipseRenderer extends ParticleRenderer {
	
	private static final float DEFAULT_WIDTH = 10.0f;
	private static final float DEFAULT_HEIGHT = 10.0f;
	
	private static final int DEFAULT_STROKE = 255;
	private static final int DEFAULT_FILL = 255;
	
	private float width;
	private float height;
	private int fill;
	private int stroke;
	
	private float xRotation = 0.0f;
	private float yRotation = 0.0f;
	private float zRotation = 0.0f;
	
	public ParticleEllipseRenderer(PApplet parent, Particle particle) {
		this(parent, particle, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_FILL, DEFAULT_STROKE);
	}
	
	public ParticleEllipseRenderer(PApplet parent, Particle particle, float width, float height) {
		this(parent, particle, width, height, DEFAULT_FILL, DEFAULT_STROKE);
	}
	
	public ParticleEllipseRenderer(PApplet parent, Particle particle, int fill, int stroke) {
		this(parent, particle, DEFAULT_WIDTH, DEFAULT_HEIGHT, fill, stroke);
	}
	
	public ParticleEllipseRenderer(PApplet parent, Particle particle, float width, float height, int fill, int stroke) {
		super(parent, particle);
		this.width = width;
		this.height = height;
		this.fill = fill;
		this.stroke = stroke;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
	
	public void setSize(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	public void rotateTowards(Vector3D point) {
		Vector3D position = particle.getPosition();
		
		Vector3D direction = position.subtract(point).normalize();
		
		float xDiff = position.getX() - point.getX();
		float yDiff = position.getY() - point.getY();
		float zDiff = position.getZ() - point.getZ();
		
		xRotation = (float) Math.atan2(zDiff, yDiff);
		yRotation = (float) Math.atan2(xDiff, zDiff);
		zRotation = (float) Math.atan2(-yDiff, xDiff);
	}

	@Override
	public void render() {
		Vector3D position = particle.getPosition();
		
		parent.pushMatrix();
		
		parent.translate(position.getX(), position.getY(), position.getZ());
		parent.rotateX(xRotation);
		parent.rotateY(yRotation);
		parent.rotateZ(zRotation);
		
		parent.stroke(stroke);
		parent.fill(fill);
		
		parent.ellipse(0, 0, width, height);
		
		parent.popMatrix();
	}
}
