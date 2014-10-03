package particles;
import graphics.Renderer;
import physics.Particle;
import processing.core.PApplet;


public abstract class ParticleRenderer extends Renderer {
	
	protected Particle particle;

	public ParticleRenderer(PApplet parent, Particle particle) {
		super(parent);
		this.particle = particle;
	}

}
