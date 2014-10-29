package scenes.gameoflife;

import processing.core.PApplet;
import graphics.Renderer;

public class GameOfLifeRenderer extends Renderer {

	private GameOfLife gol;

	public GameOfLifeRenderer(PApplet parent, GameOfLife gol) {
		super(parent);
		this.gol = gol;
	}

	@Override
	public void render() {
		render(0);
	}
	
	/**
	 * 
	 * @param opacity must be between 0-255 inclusive
	 */
	public void render(int shade){
		int cols = gol.getColumnCount();
		int rows = gol.getRowCount();

		float cellWidth = parent.width / cols;
		float cellHeight = parent.height / rows;
		
		parent.pushMatrix();
		
		parent.translate(-parent.width/2, -parent.height / 2);

		parent.background(255, 255, 255);
		parent.color(shade);
		parent.fill(shade);
		
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				if (gol.getCellState(i, j)) {
					parent.rect(i * cellWidth, j * cellHeight, cellWidth,
							cellHeight);
				}
			}
		}
		parent.popMatrix();
	}

}
