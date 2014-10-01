import java.awt.event.KeyEvent;

import processing.core.*;
import processing.event.MouseEvent;

@SuppressWarnings("serial")
public class IntoDangerzone extends PApplet {

	public static final int PARTICLE_COUNT = 1500;
	public static final boolean DRAW_AXES = false;
	
	public static final float DT_THRESHOLD = 0.25f;
	public static final float DT_STEP = 0.01f;
	
	ParticleCloud particleCloud;
	ParticleCloudRenderer particleCloudRenderer;
	PhysicsEngine physicsEngine = new PhysicsEngine();
	
	long t;
	float dt;
	float dt_accumulator = 0.0f;
	
	private Camera camera;

	// Text size parameters for kick, snare and hat
	float kickSize = 16, snareSize = 16, hatSize = 16;

	AudioAnalyser audioAnalyser;

	public void setup() {
		size(1024, 768, P3D);
		background(0);
		audioAnalyser = new AudioAnalyser(this);

		initializeParticles();
		initializeCamera();
		
		t = System.currentTimeMillis();
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
		Vector3D cameraPos = new Vector3D(0,0,1200);
		Vector3D cameraCenter = new Vector3D(0,0,0);
		
		camera = new Camera(this, cameraPos, cameraCenter, 0, 1, 0);
		camera.setPositionXIncrementEventProvider(new KeyboardInputProvider(KeyEvent.VK_RIGHT));
		camera.setPositionXDecrementEventProvider(new KeyboardInputProvider(KeyEvent.VK_LEFT));
		camera.setPositionYDecrementEventProvider(new KeyboardInputProvider(KeyEvent.VK_UP, KeyEvent.VK_SHIFT));
		camera.setPositionYIncrementEventProvider(new KeyboardInputProvider(KeyEvent.VK_DOWN, KeyEvent.VK_SHIFT));
		camera.setPositionZDecrementEventProvider(new KeyboardInputProvider(new int[] {KeyEvent.VK_UP, KeyEvent.VK_SHIFT}));
		camera.setPositionZIncrementEventProvider(new KeyboardInputProvider(new int[] {KeyEvent.VK_DOWN, KeyEvent.VK_SHIFT}));
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
		
		if(dt > DT_THRESHOLD) dt = DT_THRESHOLD;
		
		dt_accumulator += dt;
		
		while(dt_accumulator >= DT_STEP) {
			particleCloud.update();
			audioAnalyser.fft.forward(audioAnalyser.song.mix);
			physicsEngine.step(DT_STEP);
			dt_accumulator -= DT_STEP;
		}
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
		//camera.setPositionX(mouseX - width / 2);
		//camera.setPositionY(mouseY - height / 2);
		camera.update();
	}

	public void mouseWheel(MouseEvent event) {
		camera.setPositionZ(camera.getPosition().getZ() + event.getCount() * 100);
	}

	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "IntoDangerzone" });
	}
}
