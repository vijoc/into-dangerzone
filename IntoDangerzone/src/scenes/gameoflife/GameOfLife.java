package scenes.gameoflife;

import java.util.Random;

class GameOfLife {

	/** Default birth rules */
	public static final int[] DEFAULT_BIRTH_COUNTS = { 3 };

	/** Default survival rules */
	public static final int[] DEFAULT_SURVIVAL_COUNTS = { 2, 3 };

	private RuleSet ruleSet = new RuleSet(DEFAULT_BIRTH_COUNTS,
			DEFAULT_SURVIVAL_COUNTS);

	private int rowCount;
	private int colCount;
	private int generation;

	private float density = 0.0f;
	private float initialDensity = 0.0f;

	private final int maxRowIndex;
	private final int maxColIndex;

	/** Cell states as boolean array, indexed by x,y so first index is column */
	private boolean states[][];

	/**
	 * Construct a new GameOfLife with given dimensions.
	 * 
	 * @param colCount
	 *            number of columns, i.e. the x dimension
	 * @param rowCount
	 *            number of rows, i.e. the y dimension
	 */
	public GameOfLife(int colCount, int rowCount) {
		this.colCount = colCount;
		this.rowCount = rowCount;

		maxRowIndex = rowCount - 1;
		maxColIndex = colCount - 1;

		states = new boolean[colCount][rowCount];
		generation = 0;
	}

	/**
	 * Seed a random state.
	 */
	public void seedRandom() {
		Random rand = new Random();
		for (int i = 0; i < colCount; i++) {
			for (int j = 0; j < rowCount; j++) {
				if (rand.nextFloat() < initialDensity)
					setCellState(i, j, true);
			}
		}
	}

	/**
	 * Step forward a generation.
	 */
	public void stepGeneration() {
		states = calculateNextStates();
		generation++;
		calculateDensity();
	}

	private void calculateDensity() {
		int liveCells = 0;
		for (boolean[] row : states) {
			for (boolean cell : row) {
				if (cell)
					liveCells++;
			}
		}
		density = liveCells / ((float) rowCount * colCount);
	}

	/**
	 * Get the state of the cell at coordinates (x,y).
	 * 
	 * @param x
	 *            column coordinate
	 * @param y
	 *            row coordinate
	 * @return
	 */
	public boolean getCellState(int x, int y) {
		return states[wrapColumn(x)][wrapRow(y)];
	}

	/**
	 * Set the state of the cell at coordinates (x,y).
	 * 
	 * @param x
	 *            column coordinate
	 * @param y
	 *            row coordinate
	 * @param state
	 *            new state
	 */
	public void setCellState(int x, int y, boolean state) {
		states[wrapColumn(x)][wrapRow(y)] = state;
	}

	/**
	 * Insert a shape specified by boolean[x][y] array
	 * 
	 * @param shape
	 * @param x
	 *            leftmost x coordinate
	 * @param y
	 *            upmost y coordinate
	 */
	public void insertShape(boolean[][] shape, int x, int y) {
		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[0].length; j++) {
				setCellState(x + i, y + j, shape[i][j]);
			}
		}
	}

	public int getColumnCount() {
		return colCount;
	}

	public int getRowCount() {
		return rowCount;
	}

	public RuleSet getRuleSet() {
		return ruleSet;
	}

	public void setRuleSet(RuleSet ruleSet) {
		this.ruleSet = ruleSet;
	}

	private boolean[][] calculateNextStates() {
		boolean buffer[][] = new boolean[colCount][rowCount];
		for (int x = 0; x < colCount; x++) {
			for (int y = 0; y < rowCount; y++) {
				buffer[x][y] = calculateNextState(x, y);
			}
		}
		return buffer;
	}

	private boolean calculateNextState(int x, int y) {
		int neighbourCount = countAliveNeighbours(x, y);

		if (getCellState(x, y) == true) {
			return ruleSet.survivalRulesContain(neighbourCount);
		} else {
			return ruleSet.birthRulesContain(neighbourCount);
		}
	}

	private int countAliveNeighbours(int x, int y) {
		int sum = 0;

		int col, row;
		for (int i = -1; i <= 1; i++) {
			col = wrapColumn(x + i);

			for (int j = -1; j <= 1; j++) {
				if (i == 0 && j == 0)
					continue; // skip self

				row = wrapRow(y + j);

				if (getCellState(col, row))
					sum += 1;
			}
		}

		return sum;
	}

	private int wrapColumn(int x) {
		return wrapAround(x, maxColIndex);
	}

	private int wrapRow(int y) {
		return wrapAround(y, maxRowIndex);
	}

	/**
	 * A helper to wrap the coordinates around. Given a number and a maximum,
	 * wraps the number in the range [0..maximum] inclusive.
	 * 
	 * @param number
	 * @param max
	 * @return
	 */
	private static int wrapAround(int number, int max) {
		if (number >= 0 && number <= max) // within bounds, return self
			return number;
		else if (number < 0) // less than zero, wrap around
			return max + number;
		else
			// greater than max, wrap around
			return number - max;
	}

	public int getGeneration() {
		return generation;
	}

	public float getDensity() {
		return density;
	}
}
