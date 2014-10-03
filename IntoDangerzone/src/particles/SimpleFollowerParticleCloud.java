package particles;
import java.util.Arrays;
import java.util.Random;

import math.Vector3D;
import physics.Particle;
import physics.PhysicsObjectManager;
import processing.core.PApplet;

public class SimpleFollowerParticleCloud extends ParticleCloud {

	private Random random = new Random();
	private float[] particleSizes;
	
	public SimpleFollowerParticleCloud(PhysicsObjectManager objectManager, int particleCount) {
		super(objectManager);
		Vector3D startPos = new Vector3D(0,0,0);
		
		for(int i = 0; i < particleCount; i++) {
			Particle particle = objectManager.createParticle(startPos);
			particles.add(particle);
			Vector3D startMomentum = generateRandomMomentum(150);
			particle.setImpulse(startMomentum);
		}
		
		particleSizes = new float[particles.size()];
		Arrays.fill(particleSizes, 10.0f);
	}

	@Override
	public void update() {
		for(int i = 0; i < particles.size(); i++) {
			Particle particle, followed;
			
			particle = particles.get(i);
			followed = i > 0 ? particles.get(i-1) : particles.get(particles.size()-1);
			
			updateCentralPull(particle);
			updateFollow(particle, followed);
		}
	}
	
	private Vector3D generateRandomMomentum(float scale) {
		float[] coords = new float[3];
		for(int i = 0; i < 3; i++) {
			boolean negative = random.nextBoolean();
			float value = random.nextFloat();
			coords[i] = value * scale;
			if(negative) coords[i] *= -1;
		}
		return new Vector3D(coords[0], coords[1], coords[2]);
	}
	
	private void updateFollow(Particle follower, Particle followed) {
		Vector3D followThrust = followed.getPosition().subtract(follower.getPosition()).normalize().scalarMultiplication(25);
		follower.applyImpulse(followThrust);
	}
	
	private void updateCentralPull(Particle particle) {
		Vector3D pull = new Vector3D(0,0,0).subtract(particle.getPosition()).scalarDivision(3);
		particle.applyImpulse(pull);
	}
}
