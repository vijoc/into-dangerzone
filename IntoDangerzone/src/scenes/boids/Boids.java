package scenes.boids;

import java.util.Random;

import audio.AudioAnalyser;
import processing.core.PApplet;

public class Boids extends core.Scene {

	Flock flock;

	private AudioAnalyser audioAnalyser;

	private int maxBoids = 500;
	private Random rand;
	private float newBoidProbability = 0.1f;

	public Boids(PApplet parent, AudioAnalyser audioAnalyser) {
		super(parent);
		this.parent = parent;
		this.audioAnalyser = audioAnalyser;
		flock = new Flock(parent);

		rand = new Random();

		for (int i = 0; i < 1; i++) {
			flock.addBoid(new Boid(0, 0, parent.width, parent.height));
		}
	}

	@Override
	public void update(float dtSeconds) {
		if (audioAnalyser.isKick()) {
			if (flock.boids.size() < maxBoids) {
				if (rand.nextFloat() < newBoidProbability){
					flock.addBoid(new Boid(0, 0, parent.width, parent.height));
				}
			}
		}
		flock.run();
	}

	@Override
	public void render() {
		parent.background(255);
		flock.render();
	}

}
