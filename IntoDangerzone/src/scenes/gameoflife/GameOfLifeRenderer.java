package scenes.gameoflife;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import graphics.Renderer;

public class GameOfLifeRenderer extends Renderer {

	private GameOfLife gol;

	public GameOfLifeRenderer(PApplet parent, GameOfLife gol) {
		super(parent);
		this.gol = gol;
	}

	@Override
	public void render() {
		int cols = gol.getColumnCount();
		int rows = gol.getRowCount();

		float cellWidth = parent.width / cols;
		float cellHeight = parent.height / rows;
		
		parent.pushMatrix();
		
		parent.translate(-parent.width/2, -parent.height / 2);

		parent.background(255, 255, 255);
		parent.color(0, 0, 0);
		parent.fill(0, 0, 0);
		
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
