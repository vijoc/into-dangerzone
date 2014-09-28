import java.util.ArrayList;

import processing.core.PApplet;

/**
 * ParticleCloud holds particles and applies a set of rules for them.
 */
public abstract class ParticleCloud {

	protected InputProvider<float[]> particleSizeProvider;
	protected float[] particleSizes;
	
	protected ArrayList<Particle> particles = new ArrayList<Particle>();
	
	abstract public void update();
	
	public void setParticleSizeProvider(InputProvider<float[]> provider) {
		this.particleSizeProvider = provider;
	}
	
	public void display(PApplet parent) {
		updateParticleSizes();
		for(int i = 0; i < particles.size(); i++) {
			Particle particle = particles.get(i);
			parent.pushMatrix();
			Vector3D position = particle.getPosition();
			parent.translate(position.getX(), position.getY(), position.getZ());
			parent.stroke(255);
			parent.ellipse(0, 0, particleSizes[i], particleSizes[i]);
			parent.popMatrix();
		}
	}
	
	protected void updateParticleSizes() {
		particleSizes = new float[particles.size()];
		float[] input = particleSizeProvider.readInput();
		
		ArrayInputMapper.map(input, particleSizes);
		
		// Scale
		float max = Float.NEGATIVE_INFINITY;
		for(float val : input) {
			max = Math.max(max, val);
		}
		
		if(max == 0) return;
		
		for(int i = 0; i < particleSizes.length; i++) {
			particleSizes[i] = 10 + (particleSizes[i] / max) * 40;
		}
	}
}
