package scenes.gameoflife;

import math.Vector3D;
import graphics.Camera;
import core.Scene;
import ddf.minim.AudioSource;
import processing.core.PApplet;

public class GameOfLifeScene extends Scene {
	
	private float generationTimer;
	private float stepTime;
	
	private GameOfLife gol;
	private GameOfLifeRenderer golRenderer;
	
	private Camera camera;


	public GameOfLifeScene(PApplet parent, AudioSource audioSource, float stepTime, int columns, int rows) {
		super(parent);
		
		gol = new GameOfLife(columns, rows);
		gol.seedRandom();
		
		this.golRenderer = new GameOfLifeRenderer(parent, gol);
		initializeStepTimer(stepTime);
		this.camera = new Camera(parent);
	}
	
	public GameOfLifeScene(PApplet parent, AudioSource audioSource, int columns, int rows) {
		this(parent, audioSource, 0.05f, columns, rows);		
	}

	@Override
	public void update(float dtSeconds) {
		generationTimer -= dtSeconds;
		if(generationTimer < 0) {
			generationTimer = stepTime;
			gol.stepGeneration();
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

}
