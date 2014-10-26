package scenes.gameoflife;

import core.Scene;
import processing.core.PApplet;

public class GameOfLifeScene extends Scene {
	
	private float generationTimer;
	private float stepTime;
	
	private GameOfLife gol;
	private GameOfLifeRenderer golRenderer;
	

	public GameOfLifeScene(PApplet applet, float stepTime, int columns, int rows) {
		super(applet);
		this.stepTime = stepTime;
		generationTimer = stepTime;
		gol = new GameOfLife(columns, rows);
		gol.seed();
		golRenderer = new GameOfLifeRenderer(applet, gol);
	}
	
	@Override
	public void update(float dtSeconds) {
		generationTimer -= dtSeconds;
		if(generationTimer < 0) {
			generationTimer += stepTime;
			gol.nextGeneration();
		}
	}

	@Override
	public void render() {
		golRenderer.render();
	}

}
