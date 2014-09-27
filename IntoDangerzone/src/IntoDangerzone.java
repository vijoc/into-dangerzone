import ddf.minim.AudioInput;
import processing.core.*;
import processing.event.MouseEvent;

@SuppressWarnings("serial")
public class IntoDangerzone extends PApplet {

	public static final int PARTICLE_COUNT = 150;
	public static final boolean DRAW_AXES = true;

	Particle[] particles = new Particle[PARTICLE_COUNT];

	// Camera parameters
	float eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ;

	AudioAnalyser audioAnalyser;

	public void setup() {
		size(1024, 768, P3D);
		background(0);

		initializeParticles();
		initializeCamera();

		audioAnalyser = new AudioAnalyser(this);
	}

	public void initializeParticles() {
		Vector3D position, velocity;
		for (int i = 0; i < PARTICLE_COUNT; i++) {
			position = new Vector3D(0, 0, 0);
			particles[i] = new Particle(position);

			velocity = new Vector3D(random(-10, 10), random(-10, 10), random(
					-10, 10));

			particles[i].setVelocity(velocity);
		}

		for (int i = 0; i < PARTICLE_COUNT; i++) {
			if (i > 0) {
				particles[i].follow(particles[i - 1]);
			} else {
				particles[i].follow(particles[PARTICLE_COUNT - 1]);
			}
		}
	}

	public void initializeCamera() {
		eyeX = 0;
		eyeY = 0;
		eyeZ = 1200; // (float) ((height/2.0f) / Math.tan(PI*30.0 / 180.0f));
		centerX = 0;
		centerY = 0;
		centerZ = 0;
		upX = 0;
		upY = 1;
		upZ = 0;
	}

	public void updateModel() {
		for (int i = 0; i < particles.length; i++) {
			particles[i].display(this);
			particles[i].advance();
		}
	}

	public void drawFFT() {
		stroke(255, 0, 0, 128);
		float[] spectrum = audioAnalyser.getSpectrum();
		for (int i = 0; i < spectrum.length; i++) {
			line(i, 0, i, spectrum[i] * 10);
		}
	}

	public void drawScope() {
		stroke(255);

		for (int i = 0; i < width - 1; i++) {
			line(i, 50 + audioAnalyser.song.left.get(i) * 50, i + 1,
					50 + audioAnalyser.song.left.get(i + 1) * 50);
			line(i, 150 + audioAnalyser.song.right.get(i) * 50, i + 1,
					150 + audioAnalyser.song.right.get(i + 1) * 50);
		}

		// Scope for line in
		/*
		 * AudioInput in = audioAnalyser.getAudioInput(); for(int i = 0; i <
		 * in.bufferSize() - 1; i++) { line( i, 50 + in.left.get(i)*50, i+1, 50
		 * + in.left.get(i+1)*50 ); line( i, 150 + in.right.get(i)*50, i+1, 150
		 * + in.right.get(i+1)*50 ); }
		 */
	}

	public void drawAxes() {
		stroke(0, 255, 0);
		fill(0, 255, 0);
		line(-1000, 0, 0, 1000, 0, 0);
		line(0, -1000, 0, 0, 1000, 0);
		line(0, 0, -1000, 0, 0, 1000);
		for (int i = -1000; i < 1001; i += 100) {
			text(i, i, 0, 0);
			text(i, 0, i, 0);
			text(i, 0, 0, i);
		}

	}

	public void drawBeats() {
		if (audioAnalyser.beat.isKick())
			audioAnalyser.kickSize = 32;
		if (audioAnalyser.beat.isSnare())
			audioAnalyser.snareSize = 32;
		if (audioAnalyser.beat.isHat())
			audioAnalyser.hatSize = 32;
		textSize(audioAnalyser.kickSize);
		text("KICK", width / 4, height / 2);
		textSize(audioAnalyser.snareSize);
		text("SNARE", width / 2, height / 2);
		textSize(audioAnalyser.hatSize);
		text("HAT", 3 * width / 4, height / 2);
		audioAnalyser.kickSize = constrain(
				(int) (audioAnalyser.kickSize * 0.95), 16, 32);
		audioAnalyser.snareSize = constrain(
				(int) (audioAnalyser.snareSize * 0.95), 16, 32);
		audioAnalyser.hatSize = constrain((int) (audioAnalyser.hatSize * 0.95),
				16, 32);
	}

	public void draw() {
		background(0);

		audioAnalyser.fft.forward(audioAnalyser.song.mix);

		updateModel();
		drawFFT();
		drawScope();
		drawBeats();

		if (DRAW_AXES)
			drawAxes();

		eyeX = mouseX - width / 2;
		eyeY = mouseY - height / 2;

		camera(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);

	}

	public void mouseWheel(MouseEvent event) {
		float e = event.getCount();
		eyeZ += e * 100;
	}

	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "IntoDangerzone" });
	}
}