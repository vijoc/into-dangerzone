import java.util.ArrayList;

import processing.core.PApplet;


public class ParticleCloudCubeRenderer extends ParticleCloudRenderer {

	ArrayList<ParticleCubeRenderer> particles = new ArrayList<ParticleCubeRenderer>();
	
	public ParticleCloudCubeRenderer(PApplet parent, ParticleCloud particleCloud) {
		super(parent, particleCloud);
		for(Particle particle : particleCloud.particles) {
			particles.add(new ParticleCubeRenderer(parent, particle));
		}
	}

	@Override
	public void render() {
		for(ParticleCubeRenderer particle : particles) {
			particle.render();
		}
	}

}
