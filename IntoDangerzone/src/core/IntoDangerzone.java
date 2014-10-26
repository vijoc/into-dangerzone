package core;
import graphics.Camera;

import java.awt.event.KeyEvent;

import audio.AudioAnalyser;
import math.Vector3D;
import particles.LayeredParticleCloud;
import particles.Particle;
import particles.ParticleCloud;
import particles.ParticleCloudCubeRenderer;
import particles.ParticleCloudRenderer;
import physics.PhysicsEngine;
import processing.core.*;
import processing.event.MouseEvent;
import scenes.LTree;

@SuppressWarnings("serial")
public class IntoDangerzone extends PApplet {

	
	/** Number of particles drawn */
	//public static final int PARTICLE_COUNT = 1500;
	
	/** Whether to draw the xyz-axes */
	public static final boolean DRAW_AXES = false;
	
	/** Threshold for clamping physics simulation, in seconds */
	public static final float DT_THRESHOLD = 0.25f;
	
	/** Size of one physics step, in seconds */
	public static final float PHYSICS_STEP_SIZE = 0.01f;
	
	private Camera camera;
	private AudioAnalyser audioAnalyser;
	private PhysicsEngine physicsEngine = new PhysicsEngine();
	
	// TODO: These don't belong here. Wrap in RenderMode or something like that.
	private ParticleCloud particleCloud;
	private ParticleCloudRenderer particleCloudRenderer;
	
	// Physics-related time variables
	private long t;						// current time
	private float dt;						// time since previous update
	private float dtAccumulator = 0.0f;	// accumulator for time since previous update

	// Text size parameters for kick, snare and hat
	private float kickSize = 16, snareSize = 16, hatSize = 16;

	private Scene scene;
	
	@Override
	public void setup() {
		size(1024, 768, P3D);
		background(0);
		audioAnalyser = new AudioAnalyser(this);

		scene = new LTree(this, audioAnalyser);
		//initializeParticles();
		//initializeCamera();
		
		t = System.currentTimeMillis();
	}
	
	@Override
	public void draw() {
		//ambientLight(50, 50, 50);
		//directionalLight(128, 128, 128, 50, 50, -50);
//		step();
//		render();
		scene.update(0);
		scene.render();
	}
	
	/**
	 * Whether to draw the sketch as full screen (presentation mode).
	 */
	@Override
	public boolean sketchFullScreen() {
		return false;
	}

	/**
	 * Initialize the state of the particle system.
	 */
	//private void initializeParticles() {
	//	particleCloud = new LayeredParticleCloud(physicsEngine.getPhysicsObjectManager(), PARTICLE_COUNT, 3);
	//	particleCloud.setExplosionEventProvider(new KickProvider(audioAnalyser));
		
	//	particleCloudRenderer = new ParticleCloudCubeRenderer(this, particleCloud);
	//	particleCloudRenderer.setParticleSizeProvider(new SpectrumProvider(audioAnalyser));
	//}

	/**
	 * Initialize camera position.
	 */
	private void initializeCamera() {
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
	
	public AudioAnalyser getAudioAnalyser(){
		return audioAnalyser;
	}
	
	private void drawParticles() {
		particleCloudRenderer.render();
	}

	private void drawFFT() {
		stroke(255, 0, 0, 128);
		float[] spectrum = audioAnalyser.getSpectrum();
		for (int i = 0; i < spectrum.length; i++) {
			line(i, 0, i, spectrum[i] * 10);
		}
	}

	private void drawScope() {
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
	
	public void drawZCR() {
		stroke(255);
		fill(255,0,0);
		float zcr = audioAnalyser.getZCR();
		textSize(32);
		text("ZCR in last frame: " + zcr, 100, -100, 0);
	}

	private void drawAxes() {
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

	private void drawBeats() {
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

	/**
	 * Update physics models, forward audio buffers et cetera.
	 */
	public void step() {
		long newTime = System.currentTimeMillis();
		dt = (newTime - t) / 1000.0f;
		t = newTime;
		
		if(dt > DT_THRESHOLD) dt = DT_THRESHOLD;
		
		dtAccumulator += dt;
		
		while(dtAccumulator >= PHYSICS_STEP_SIZE) {
			//particleCloud.update();
			audioAnalyser.getFft().forward(audioAnalyser.getSong().mix);
			//physicsEngine.step(PHYSICS_STEP_SIZE);
			dtAccumulator -= PHYSICS_STEP_SIZE;
		}
	}

	/**
	 * Render the scene.
	 */
	public void render() {
		//camera.update();
		//background(0);
		//drawParticles();
		//drawFFT();
		//drawScope();
		//drawBeats();
		//drawZCR();

		if (DRAW_AXES)
			drawAxes();
	}

	public void mouseWheel(MouseEvent event) {
		//camera.setPositionZ(camera.getPosition().getZ() + event.getCount() * 100);
	}

	public static void main(String args[]) {
		PApplet.main(new String[] { "core.IntoDangerzone" });
	}
}
