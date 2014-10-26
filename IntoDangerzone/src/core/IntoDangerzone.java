package core;
import graphics.Camera;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import audio.AudioAnalyser;
import math.Vector3D;
import processing.core.*;
import processing.event.MouseEvent;
import scenes.lTree.*;
import scenes.gameoflife.GameOfLifeScene;

@SuppressWarnings("serial")
public class IntoDangerzone extends PApplet {
	
	/** Whether to draw the xyz-axes */
	public static final boolean DRAW_AXES = false;
	
	private Camera camera;
	private AudioAnalyser audioAnalyser;
	
	private long currentTime;
	
	private ArrayList<Scene> scenes = new ArrayList<Scene>();
	private int activeScene = 0;
	
	private GameOfLifeScene golScene;
	private LTree lTreeScene;

	// Text size parameters for kick, snare and hat
	private float kickSize = 16, snareSize = 16, hatSize = 16;
	
	@Override
	public void setup() {
		size(1024, 768, P3D);
		background(0);
		audioAnalyser = new AudioAnalyser(this);
		initializeCamera();
		initializeScenes();
		initializeTimer();
	}
	
	@Override
	public void draw() {
		ambientLight(50, 50, 50);
		directionalLight(128, 128, 128, 50, 50, -50);
		step();
		render();
	}
	
	/**
	 * Whether to draw the sketch as full screen (presentation mode).
	 */
	@Override
	public boolean sketchFullScreen() {
		return false;
	}

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
	
	private void initializeTimer() {
		currentTime = System.currentTimeMillis();
	}
	
	private void initializeScenes() {
		golScene = new GameOfLifeScene(this, 0.2f, width/2, height/2);
		scenes.add(golScene);
		
		lTreeScene = new LTree(this, audioAnalyser);
		scenes.add(lTreeScene);
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
		float dtSeconds = (float) ((newTime - currentTime) / 1000.0);
		updateActiveScene(dtSeconds);
		currentTime = newTime;
		audioAnalyser.getFft().forward(audioAnalyser.getSong().mix);
	}

	/**
	 * Render the scene.
	 */
	public void render() {
		camera.update();
		background(0);
		drawFFT();
		drawScope();
		drawBeats();
		drawZCR();
		renderActiveScene();

		if (DRAW_AXES)
			drawAxes();
	}
	
	private void updateActiveScene(float dtSeconds) {
		scenes.get(activeScene).update(dtSeconds);
	}
	
	private void renderActiveScene() {
		scenes.get(activeScene).render();
	}

	public void mouseWheel(MouseEvent event) {
		//camera.setPositionZ(camera.getPosition().getZ() + event.getCount() * 100);
	}

	public static void main(String args[]) {
		PApplet.main(new String[] { "core.IntoDangerzone" });
	}
}
