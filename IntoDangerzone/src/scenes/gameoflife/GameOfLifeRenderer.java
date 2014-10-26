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
		int cols = gol.getColumnCount();
		int rows = gol.getRowCount();
		
		float cellWidth = (parent.width*2) / cols;
		float cellHeight = (parent.height*2) / rows;
		
		for(int i = 0; i < cols; i++) {
			for(int j = 0; j < rows; j++) {
				if(gol.getCellState(i, j)) {
					parent.pushMatrix();
					parent.translate(-parent.width, -parent.height);
					parent.color(255,255,255);
					parent.fill(255,255,255);
					parent.rect(i*cellWidth, j*cellHeight, cellWidth, cellHeight);
					parent.popMatrix();
				}
			}
		}
		
	}
	
}
