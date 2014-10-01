import java.util.ArrayList;

/**
 * ParticleCloud holds particles and applies a set of rules for them.
 */
public abstract class ParticleCloud {
	
	protected InputProvider<Boolean> explosionEventProvider;
	protected ArrayList<Particle> particles = new ArrayList<Particle>();
	
	abstract public void update();
	
	public void setExplosionEventProvider(InputProvider<Boolean> provider) {
		this.explosionEventProvider = provider;
	}
}
