import java.util.ArrayList;

import processing.core.PApplet;

public class ParticleCloudEllipseRenderer extends ParticleCloudRenderer {
	
	private ArrayList<ParticleEllipseRenderer> particles = new ArrayList<ParticleEllipseRenderer>();

	public ParticleCloudEllipseRenderer(PApplet parent,
			ParticleCloud particleCloud) {
		super(parent, particleCloud);
		
		for(Particle particle : particleCloud.particles) {
			particles.add(new ParticleEllipseRenderer(parent, particle));
		}
	}

	@Override
	public void render() {
		updateParticleSizes();
		updateParticleRotations();
		for(ParticleEllipseRenderer particle : particles) {
			particle.render();
		}
	}
	
	protected void updateParticleSizes() {
		float[] particleSizes = new float[particles.size()];
		float[] input = particleSizeProvider.readInput();
		
		ArrayInputMapper.map(input, particleSizes);
		
		// Scale
		float max = Float.NEGATIVE_INFINITY;
		for(float val : input) {
			max = Math.max(max, val);
		}
		
		if(max == 0) return;
		
		for(int i = 0; i < particleSizes.length; i++) {
			float size = 10 + (particleSizes[i] / max) * 40;
			particles.get(i).setSize(size, size);
		}
	}
	
	protected void updateParticleRotations() {
		Vector3D center = new Vector3D(0,0,0);
		for(int i = 0; i < particles.size(); i++) {
			particles.get(i).rotateTowards(center);
		}
	}

}
