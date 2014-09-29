import processing.core.*;
import processing.event.MouseEvent;

@SuppressWarnings("serial")
public class IntoDangerzone extends PApplet {

	public static final int PARTICLE_COUNT = 1500;
	public static final boolean DRAW_AXES = false;
	
	ParticleCloud particleCloud;
	ParticleCloudRenderer particleCloudRenderer;
	PhysicsEngine physicsEngine = new PhysicsEngine();
	
	long t;
	float dt;

	// Camera parameters
	float eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ;

	// Text size parameters for kick, snare and hat
	float kickSize = 16, snareSize = 16, hatSize = 16;

	AudioAnalyser audioAnalyser;

	public void setup() {
		size(1024, 768, P3D);
		background(0);
		audioAnalyser = new AudioAnalyser(this);
		t = System.currentTimeMillis();

		initializeParticles();
		initializeCamera();
	}

	/**
	 * Initialize the state of the particle system.
	 */
	public void initializeParticles() {
		particleCloud = new LayeredParticleCloud(PARTICLE_COUNT, 3);//new SimpleFollowerParticleCloud(PARTICLE_COUNT);
		particleCloud.setExplosionEventProvider(new KickProvider(audioAnalyser));
		
		for(Particle particle : particleCloud.particles) {
			physicsEngine.registerObject(particle);
		}
		
		particleCloudRenderer = new ParticleCloudCubeRenderer(this, particleCloud);
		particleCloudRenderer.setParticleSizeProvider(new SpectrumProvider(audioAnalyser));
	}

	/**
	 * Initialize camera position.
	 */
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
	
	public void drawParticles() {
		particleCloudRenderer.render();
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
		float[] leftWaveform = audioAnalyser.getLeftWaveform(width);
		float[] rightWaveform = audioAnalyser.getRightWaveform(width);
		for (int i = 0; i < width - 1; i++) {
			line(i, 50 + leftWaveform[i] * 50, i + 1,
					50 + leftWaveform[i + 1] * 50);
			line(i, 150 + rightWaveform[i] * 50, i + 1,
					150 + rightWaveform[i + 1] * 50);
		}
	}

	public void drawAxes() {
		stroke(0, 255, 0);
		fill(0, 255, 0);
		textSize(32);
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
		if (audioAnalyser.isKick())
			kickSize = 32;
		if (audioAnalyser.isSnare())
			snareSize = 32;
		if (audioAnalyser.isHat())
			hatSize = 32;
		textSize(kickSize);
		text("KICK", width / 4, height / 2);
		textSize(snareSize);
		text("SNARE", width / 2, height / 2);
		textSize(hatSize);
		text("HAT", 3 * width / 4, height / 2);
		kickSize = constrain((int) (kickSize * 0.95), 16, 32);
		snareSize = constrain((int) (snareSize * 0.95), 16, 32);
		hatSize = constrain((int) (hatSize * 0.95), 16, 32);
	}

	public void draw() {
		ambientLight(50, 50, 50);
		directionalLight(128, 128, 128, 50, 50, -50);
		step();
		render();
	}

	/**
	 * Update physics models, forward audio buffers et cetera.
	 */
	public void step() {
		long newTime = System.currentTimeMillis();
		dt = (newTime - t) / 1000.0f;
		t = newTime;
		
		particleCloud.update();
		
		audioAnalyser.fft.forward(audioAnalyser.song.mix);
		physicsEngine.step(dt);
	}

	/**
	 * Render the scene.
	 */
	public void render() {
		positionCamera();
		background(0);
		drawParticles();
		drawFFT();
		drawScope();
		drawBeats();

		if (DRAW_AXES)
			drawAxes();
	}

	/**
	 * Position the camera.
	 */
	public void positionCamera() {
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