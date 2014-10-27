package scenes.boids;

import audio.AudioAnalyser;
import processing.core.PApplet;

public class Boids extends core.Scene {

	Flock flock;

	private AudioAnalyser audioAnalyser;

	public Boids(PApplet parent, AudioAnalyser audioAnalyser) {
		super(parent);
		this.parent = parent;
		this.audioAnalyser = audioAnalyser;
		flock = new Flock(parent);

		for (int i = 0; i < 10; i++) {
			flock.addBoid(new Boid(0, 0, parent.width, parent.height));
		}
	}

	@Override
	public void update(float dtSeconds) {
		flock.run();
	}

	@Override
	public void render() {
		parent.background(255);
		flock.render();
	}

}
