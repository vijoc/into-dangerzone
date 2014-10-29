package scenes.gameoflife;

import java.util.Arrays;

public class RuleSet {

	/**
	 * Known good rule sets
	 */
	public static final RuleSet AMOEBA = new RuleSet(new int[] { 3, 5, 7 },
			new int[] { 1, 3, 5, 8 });
	public static final RuleSet ASSIMILATION = new RuleSet(
			new int[] { 3, 4, 5 }, new int[] { 4, 5, 6, 7 });
	public static final RuleSet COAGULATIONS = new RuleSet(
			new int[] { 3, 7, 8 }, new int[] { 2, 3, 5, 6, 7, 8 });
	public static final RuleSet CORAL = new RuleSet(new int[] { 3 }, new int[] {
			4, 5, 6, 7, 8 });
	public static final RuleSet DAY_AND_NIGHT = new RuleSet(new int[] { 3, 6,
			7, 8 }, new int[] { 3, 4, 6, 7, 8 });
	public static final RuleSet DIAMOEBA = new RuleSet(new int[] { 3, 5, 6, 7,
			8 }, new int[] { 5, 6, 7, 8 });
	public static final RuleSet GNARL = new RuleSet(new int[] { 1 },
			new int[] { 1 });
	public static final RuleSet GOL = new RuleSet(new int[] { 3 }, new int[] {
			2, 3 });
	public static final RuleSet HIGH_LIFE = new RuleSet(new int[] { 3, 6 },
			new int[] { 2, 3 });
	public static final RuleSet LIFE_WITHOUT_DEATH = new RuleSet(
			new int[] { 3 }, new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 });
	public static final RuleSet LONG_LIFE = new RuleSet(new int[] { 3, 4, 5 },
			new int[] { 5 });
	public static final RuleSet MAZE = new RuleSet(new int[] { 3 }, new int[] {
			1, 2, 3, 4, 5 });
	public static final RuleSet MAZECTRIC = new RuleSet(new int[] { 3 },
			new int[] { 1, 2, 3, 4 });
	public static final RuleSet MOVE = new RuleSet(new int[] { 3, 6, 8 },
			new int[] { 2, 4, 5 });
	public static final RuleSet PSEUDO_LIFE = new RuleSet(
			new int[] { 3, 5, 7 }, new int[] { 2, 3, 8 });
	public static final RuleSet REPLICATOR = new RuleSet(
			new int[] { 1, 3, 5, 7 }, new int[] { 1, 3, 5, 7 });
	public static final RuleSet SEEDS_TWO = new RuleSet(new int[] { 2 },
			new int[] {});
	public static final RuleSet SERVIETTES = new RuleSet(new int[] { 2, 3, 4 },
			new int[] {});
	public static final RuleSet STAINS = new RuleSet(new int[] { 3, 6, 7, 8 },
			new int[] { 2, 3, 5, 6, 7, 8 });
	public static final RuleSet THREE_FOUR_LIFE = new RuleSet(
			new int[] { 3, 4 }, new int[] { 3, 4 });
	public static final RuleSet TWO_X_TWO = new RuleSet(new int[] { 3, 6 },
			new int[] { 1, 2, 5 });
	public static final RuleSet WALLED_CITIES = new RuleSet(new int[] { 4, 5,
			6, 7, 8 }, new int[] { 2, 3, 4, 5 });

	private int[] birthCounts = {};
	private int[] survivalCounts = {};

	/**
	 * Construct a default, empty RuleSet.
	 */
	public RuleSet() {

	}

	/**
	 * Construct a RuleSet with given birth rules and survival rules.
	 * 
	 * @param birthCounts
	 * @param survivalCounts
	 */
	public RuleSet(int[] birthCounts, int[] survivalCounts) {
		setBirthRules(birthCounts);
		setSurvivalRules(survivalCounts);
	}

	public boolean survivalRulesContain(int count) {
		return Arrays.binarySearch(survivalCounts, count) >= 0;
	}

	public boolean birthRulesContain(int count) {
		return Arrays.binarySearch(birthCounts, count) >= 0;
	}

	public void setBirthRules(int... counts) {
		this.birthCounts = counts;
		Arrays.sort(birthCounts);
	}

	public void setSurvivalRules(int... counts) {
		this.survivalCounts = counts;
		Arrays.sort(survivalCounts);
	}
}
