package scenes.gameoflife;

import math.Vector3D;
import graphics.Camera;
import audio.BeatListener;
import core.ConstantProvider;
import core.InputProvider;
import core.KickProvider;
import core.PositiveEdgeTrigger;
import core.Scene;
import ddf.minim.AudioSource;
import processing.core.PApplet;

public class GameOfLifeScene extends Scene {
	
	private enum StepMode {
		EVENTED, TIMED
	}
	
	private StepMode stepMode = StepMode.EVENTED;
	
	private float generationTimer;
	private float stepTime;
	
	private GameOfLife gol;
	private GameOfLifeRenderer golRenderer;
	
	private Camera camera;
	private InputProvider<Boolean> stepEventProvider = new ConstantProvider<Boolean>(false);

	public GameOfLifeScene(PApplet parent, float stepTime, int columns, int rows) {
		this(parent, null, columns, rows);
		
		gol = new GameOfLife(columns, rows);
		gol.seedRandom();
		
		this.golRenderer = new GameOfLifeRenderer(parent, gol);
		
		stepMode = StepMode.TIMED;
		initializeStepTimer(stepTime);
	}
	
	public GameOfLifeScene(PApplet parent, AudioSource audioSource, int columns, int rows) {
		super(parent);
		gol = new GameOfLife(columns, rows);
		gol.seedRandom();
		golRenderer = new GameOfLifeRenderer(parent, gol);
		setStepEventProvider(new PositiveEdgeTrigger(new KickProvider(new BeatListener(audioSource))));
		this.camera = new Camera(parent);
	}

	@Override
	public void update(float dtSeconds) {
		if(stepMode == StepMode.TIMED) {
			generationTimer -= dtSeconds;
			if(generationTimer < 0) {
				generationTimer = stepTime;
				gol.stepGeneration();
			}
		} else {
			if(stepEventProvider.readInput()) {
				gol.stepGeneration();
			}
		}
	}

	@Override
	public void render() {
		updateCamera();
		golRenderer.render();
	}
	
	private void updateCamera() {
		float fovRadians = (float) Math.toRadians(75); // ?
		float zDistW = (float) ((parent.width/2.0f) / Math.tan(fovRadians/2.0f));
		float zDistH = (float) ((parent.height/2.0f) / Math.tan(fovRadians/2.0f));
		float zDist = Math.max(zDistW, zDistH);
		camera.setPosition(new Vector3D(0, 0, zDist));
		camera.setCenter(new Vector3D(0,0,0));
		camera.update();
	}
	
	private void initializeStepTimer(float stepTime) {
		this.stepTime = stepTime;
		generationTimer = stepTime;
	}
	
	public void setStepEventProvider(InputProvider<Boolean> provider) {
		stepEventProvider = provider;
	}

}
