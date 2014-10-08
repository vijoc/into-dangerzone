package particles;
import graphics.Renderer;
import core.InputProvider;
import processing.core.PApplet;

public abstract class ParticleCloudRenderer extends Renderer {

	protected ParticleCloud particleCloud;
	
	protected InputProvider<float[]> particleSizeProvider;
	
	public ParticleCloudRenderer(PApplet parent, ParticleCloud particleCloud) {
		super(parent);
		this.particleCloud = particleCloud;
	}

	public InputProvider<float[]> getParticleSizeProvider() {
		return particleSizeProvider;
	}

	public void setParticleSizeProvider(InputProvider<float[]> particleSizeProvider) {
		this.particleSizeProvider = particleSizeProvider;
	}
}
