import java.util.Random;


public class LayeredParticleCloud extends ParticleCloud {
	
	private int layerCount;
	private float layerDistance = 300.0f;
	private Random random = new Random();
	
	public LayeredParticleCloud(int particleCount, int layerCount) {
		this.layerCount = layerCount;
		
		for(int i = 0; i < particleCount; i++) {
			int layer = (i % layerCount) + 1;
			Vector3D position = generateRandomVector(1).toLength(layer * layerDistance);
			Particle particle = new Particle(position);
			particles.add(particle);
		}
	}

	@Override
	public void update() {
		for(int i = 0; i < particles.size(); i++) {
			Particle particle = particles.get(i);
			int layer = (i % layerCount) + 1;
			
			if(explosionEventProvider.readInput() == true) {
				applyExplosion(particle);
			}
			applySpring(particle, layer * layerDistance);
			applyFriction(particle);
		}
	}
	
	private void applyExplosion(Particle particle) {
		Vector3D force = particle.getPosition().subtract(new Vector3D(0,0,0))
				.toLength(15000);
		
		particle.addMomentum(force);
	}
	
	private void applySpring(Particle particle, float distance)  {
		float springConstant = 50.0f;
		Vector3D force = new Vector3D(0,0,0).subtract(particle.getPosition())
				.toLength(springConstant * (particle.getPosition().getLength() - distance));
		particle.addMomentum(force);
	}
	
	private void applyFriction(Particle particle) {
		Vector3D velocity = particle.getVelocity();
		if(velocity.getLength() == 0) return;
		
		float frictionConstant = 0.15f;
		particle.setVelocity(velocity.subtract(velocity.scalarMultiplication(frictionConstant)));
	}
	
	private Vector3D generateRandomVector(float scale) {
		float[] coords = new float[3];
		for(int i = 0; i < coords.length; i++) {
			boolean negative = random.nextBoolean();
			float value = random.nextFloat() * scale;
			if(negative) value *= -1;
			coords[i] = value;
		}
		return new Vector3D(coords[0], coords[1], coords[2]);
	}

}
