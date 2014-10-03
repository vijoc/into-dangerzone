package particles;
import java.util.ArrayList;

import physics.Particle;
import physics.PhysicsObjectManager;
import core.InputProvider;

/**
 * ParticleCloud holds particles and applies a set of rules for them.
 */
public abstract class ParticleCloud {
	
	protected InputProvider<Boolean> explosionEventProvider;
	protected ArrayList<Particle> particles = new ArrayList<Particle>();
	
	protected PhysicsObjectManager objectManager;
	
	public ParticleCloud(PhysicsObjectManager objectManager) {
		this.objectManager = objectManager;
	}
	
	abstract public void update();
	
	public void setExplosionEventProvider(InputProvider<Boolean> provider) {
		this.explosionEventProvider = provider;
	}
}
