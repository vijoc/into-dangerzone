package particles;
import math.Vector3D;
import physics.Particle;
import processing.core.PApplet;


public class ParticleCubeRenderer extends ParticleRenderer {

	public static final int DEFAULT_FILL = 255;
	public static final int DEFAULT_STROKE = 255;
	public static final float DEFAULT_WIDTH = 15.0f;
	public static final float DEFAULT_HEIGHT = 15.0f;
	public static final float DEFAULT_LENGTH = 15.0f;
	
	private int fill = DEFAULT_FILL;
	private int stroke = DEFAULT_STROKE;
	
	private float width = DEFAULT_WIDTH;
	private float height = DEFAULT_HEIGHT;
	private float length = DEFAULT_LENGTH;
	
	public ParticleCubeRenderer(PApplet parent, Particle particle) {
		super(parent, particle);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render() {
		Vector3D position = particle.getPosition();
		
		parent.pushMatrix();
		
		parent.translate(position.getX(), position.getY(), position.getZ());
		/*parent.rotateX(xRotation);
		parent.rotateY(yRotation);
		parent.rotateZ(zRotation);*/
		
		parent.stroke(stroke);
		parent.fill(fill);
		
		parent.box(width, height, length);
		
		parent.popMatrix();
	}

}
