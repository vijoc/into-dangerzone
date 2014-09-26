import processing.core.*;
import processing.event.MouseEvent;
import ddf.minim.*;
import ddf.minim.analysis.FFT;

@SuppressWarnings("serial")
public class IntoDangerzone extends PApplet {

	public static final int PARTICLE_COUNT = 150;
	public static final boolean DRAW_AXES = true;

	Minim minim;
	AudioPlayer song;
	AudioInput input;
	FFT fft;

	Particle[] particles = new Particle[PARTICLE_COUNT];

	// Camera parameters
	float eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ;

	public void setup() {
		size(1024, 768, P3D);
		background(0);

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

		eyeX = 0;
		eyeY = 0;
		eyeZ = 1200; // (float) ((height/2.0f) / Math.tan(PI*30.0 / 180.0f));
		centerX = 0;
		centerY = 0;
		centerZ = 0;
		upX = 0;
		upY = 1;
		upZ = 0;

		minim = new Minim(this);

		song = minim.loadFile("test.mp3");
		song.play();

		fft = new FFT(song.bufferSize(), song.sampleRate());
	}

	public void draw() {
		background(0);

		fft.forward(song.mix);

		float xSum = 0, ySum = 0, zSum = 0;

		for (int i = 0; i < particles.length; i++) {
			particles[i].display(this);
			particles[i].advance();
			xSum += particles[i].getPosition().getX();
			ySum += particles[i].getPosition().getY();
			zSum += particles[i].getPosition().getZ();
		}

		stroke(255, 0, 0, 128);

		// FFT
		for (int i = 0; i < fft.specSize(); i++) {
			line(i, 0, i, fft.getBand(i) * 4);
		}

		stroke(255);

		// Scope
		for (int i = 0; i < width - 1; i++) {
			line(i, 50 + song.left.get(i) * 50, i + 1,
					50 + song.left.get(i + 1) * 50);
			line(i, 150 + song.right.get(i) * 50, i + 1,
					150 + song.right.get(i + 1) * 50);
		}

		if (DRAW_AXES) {
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

		centerX = xSum / PARTICLE_COUNT;
		centerY = ySum / PARTICLE_COUNT;
		centerZ = zSum / PARTICLE_COUNT;

		eyeX = mouseX - width / 2;
		eyeY = mouseY - height / 2;

		camera(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);

		// The following next two lines control 3D effect.
		// eyeX += 0.1f;
		// eyeY += 0.1f;
		// eyeZ += 0.1f;
	}

	public void mouseWheel(MouseEvent event) {
		float e = event.getCount();
		eyeZ += e * 100;
	}

	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "IntoDangerzone" });
	}
}