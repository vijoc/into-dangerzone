package core;
import graphics.Camera;

import java.awt.event.KeyEvent;

import ddf.minim.AudioPlayer;
import ddf.minim.AudioSource;
import ddf.minim.Minim;
import audio.AudioAnalyser;
import math.Vector3D;
import processing.core.*;
import processing.event.MouseEvent;
import scenes.julia.JuliaScene;
import scenes.lTree.*;
import scenes.boids.BoidsScene;
import scenes.gameoflife.GameOfLifeScene;

@SuppressWarnings("serial")
public class IntoDangerzone extends PApplet {
	
	public enum AudioMode {
		FILE, LINE_IN
	}
	
	public static AudioMode audioMode = AudioMode.LINE_IN;
	public static String audioFilePath;
	private AudioSource audioSource;
	
	/** Whether to draw the xyz-axes */
	public static final boolean DRAW_AXES = false;
	
	private SceneManager sceneManager = new SceneManager();
	private GameOfLifeScene golScene;
	private LTree lTreeScene;
	private BoidsScene boidsScene;
	private JuliaScene juliaScene;
	
	private Camera camera;
	
	private AudioAnalyser audioAnalyser;
	
	private long currentTime;
	
	@Override
	public void setup() {
		size(1024, 768, P3D);
		background(0);
		initializeAudioSource();
		audioAnalyser = new AudioAnalyser(this, audioSource);
		initializeCamera();
		initializeScenes();
		initializeTimer();
	}
	
	public AudioSource getAudioSource() {
		return audioSource;
	}
	
	@Override
	public void draw() {
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
	 * Initialize the audio source, depending on program arguments.
	 */
	private void initializeAudioSource() {
		if(audioMode == AudioMode.LINE_IN) {
			audioSource = new Minim(this).getLineIn();
		} else {
			Minim minim = new Minim(this);
			AudioPlayer player = minim.loadFile(audioFilePath);
			player.play();
			audioSource = player;
		}
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
	
	private void initializeTimer() {
		currentTime = System.currentTimeMillis();
	}
	
	private void initializeScenes() {
		golScene = new GameOfLifeScene(this, getAudioSource(), width/3, height/3);
		sceneManager.addScene(golScene);
		
		lTreeScene = new LTree(this, getAudioSource());
		sceneManager.addScene(lTreeScene);
		
		boidsScene = new BoidsScene(this, getAudioSource());
		sceneManager.addScene(boidsScene);
		
		juliaScene = new JuliaScene(this, getAudioSource());
		sceneManager.addScene(juliaScene);
		
		sceneManager.setActiveScene(0);
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

	/**
	 * Update physics models, forward audio buffers et cetera.
	 */
	public void step() {
		long newTime = System.currentTimeMillis();
		float dtSeconds = (float) ((newTime - currentTime) / 1000.0);
		sceneManager.updateActiveScene(dtSeconds);
		currentTime = newTime;
		audioAnalyser.getFft().forward(audioAnalyser.getAudioSource().mix);
	}

	/**
	 * Render the scene.
	 */
	public void render() {
		//camera.update();
		background(0);
		sceneManager.renderActiveScene();

		if (DRAW_AXES)
			drawAxes();
	}
	
	@Override
	public void keyPressed() {
		switch(key) {
		case '0': sceneManager.setActiveScene(0); break;
		case '1': sceneManager.setActiveScene(1); break;
		case '2': sceneManager.setActiveScene(2); break;
		case '3': sceneManager.setActiveScene(3); break;
		case '4': sceneManager.setActiveScene(4); break;
		case '5': sceneManager.setActiveScene(5); break;
		case '6': sceneManager.setActiveScene(6); break;
		case '7': sceneManager.setActiveScene(7); break;
		case '8': sceneManager.setActiveScene(8); break;
		case '9': sceneManager.setActiveScene(9); break;
		}
	}

	public void mouseWheel(MouseEvent event) {
		camera.setPositionZ(camera.getPosition().getZ() + event.getCount() * 100);
	}

	public static void main(String args[]) {
		if(args.length > 0) {
			IntoDangerzone.audioMode = AudioMode.FILE;
			IntoDangerzone.audioFilePath = args[0];
		}
		
		
		PApplet.main(new String[] { "core.IntoDangerzone" });
	}
}
