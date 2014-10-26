package scenes.gameoflife;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

class GameOfLife {
	
	private static Integer[] DEFAULT_BIRTH_COUNTS = { 3 };
	private static Integer[] DEFAULT_SURVIVAL_COUNTS = { 2, 3 };
	
	private ArrayList<Integer> birthCounts = new ArrayList<Integer>(Arrays.asList(DEFAULT_BIRTH_COUNTS));
	private ArrayList<Integer> survivalCounts = new ArrayList<Integer>(Arrays.asList(DEFAULT_SURVIVAL_COUNTS));
	
	private int rowCount;
	private int colCount;
	
	private boolean states[][];
	
	public GameOfLife(int colCount, int rowCount) {
		this.colCount = colCount;
		this.rowCount = rowCount;
	}
	
	public void seed() {
		states = new boolean[colCount][rowCount];
		Random rand = new Random();
		for(int i = 0; i < colCount; i++) {
			for(int j = 0; j < rowCount; j++) {
				setState(i, j, rand.nextBoolean());
			}
		}
	}
	
	public void nextGeneration() {
		boolean nextStates[][] = new boolean[colCount][rowCount];
		for(int col = 0; col < colCount; col++) {
			for(int row = 0; row < rowCount; row++) {
				nextStates[col][row] = getNextState(col, row);
			}
		}
		states = nextStates;
	}
	
	private boolean getNextState(int x, int y) {
		int neighbourCount = countNeighbours(x,y);
		boolean currentState = getCellState(x,y);
		
		if(currentState == true) {
			return survivalCounts.contains(neighbourCount);
		} else {
			return birthCounts.contains(neighbourCount);
		}
	}
	
	private void setState(int x, int y, boolean state) {
		states[x][y] = state;
	}
	
	private int countNeighbours(int x, int y) {
		// TODO: Cleanup!
		int sum = 0;	
		int row, col;
		
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				col = x + i;
				row = y + i;
				if(col < 0) col = colCount-1;
				if(col > colCount-1) col = 0;
				if(row < 0) row = rowCount-1;
				if(row > rowCount-1) row = 0;
				
				if(getCellState(col, row)) sum += 1;
			}
		}
		
		return sum;
	}
	
	public boolean[][] getStates() {
		return states;
	}
	
	public boolean getCellState(int x, int y) {
		return states[x][y];
	}
	
	public void setBirthRules(Collection<Integer> birthCounts) {
		this.birthCounts = new ArrayList<Integer>(birthCounts);
	}
	
	public void setSurvivalRules(Collection<Integer> survivalCounts) {
		this.survivalCounts = new ArrayList<Integer>(survivalCounts);
	}

	public int getColumnCount() {
		return colCount;
	}
	
	public int getRowCount() {
		return rowCount;
	}
	
}
