import java.util.ArrayList;

import processing.core.PApplet;

/**
 * ParticleCloud holds particles and applies a set of rules for them.
 */
public abstract class ParticleCloud {
	
	protected ArrayList<Particle> particles = new ArrayList<Particle>();
	
	abstract public void update();
}
