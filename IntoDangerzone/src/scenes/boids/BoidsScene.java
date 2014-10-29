package scenes.boids;

import java.util.Random;

import ddf.minim.AudioSource;
import audio.BeatListener;
import processing.core.PApplet;

public class BoidsScene extends core.Scene {

	private Flock flock;
	private BoidsRenderer boidsRenderer;

	private BeatListener beatListener;

	private int maxBoids = 500;
	private Random rand;
	private float newBoidProbability = 0.1f;
	private float newRulesProbability = 0.2f;
	private float newRenderingProbability = 0.03f;

	public BoidsScene(PApplet parent, AudioSource audioSource) {
		super(parent);
		this.parent = parent;
		this.beatListener = new BeatListener(audioSource);
		flock = new Flock(parent);
		boidsRenderer = new BoidsRenderer(parent, flock);

		rand = new Random();

		for (int i = 0; i < 1; i++) {
			flock.addBoid(new Boid(parent.width, parent.height));
		}
	}

	@Override
	public void update(float dtSeconds) {
		if (beatListener.isSnare()) {
			if (rand.nextFloat() < newBoidProbability) {
				if (flock.boids.size() < maxBoids)
				flock.addBoid(new Boid(parent.width, parent.height));
			}
		}
		if (beatListener.isKick()) {
			if (rand.nextFloat() < newRulesProbability) {
				flock.newRules();
			}
		}
		flock.run();
		if (beatListener.isHat()) {
			if (rand.nextFloat() < newRenderingProbability) {
				boidsRenderer.randomRenderingMode();
			}
		}
		int flockSize = flock.boids.size();
	}

	@Override
	public void render() {
		boidsRenderer.render();
	}

	@Override
	public void activated() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deactivated() {
		// TODO Auto-generated method stub

	}

}
