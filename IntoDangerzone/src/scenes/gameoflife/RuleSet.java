package scenes.gameoflife;

import java.util.Arrays;

public class RuleSet {
	
	private int[] birthCounts = {};
	private int[] survivalCounts = {};
	
	/**
	 * Construct a default, empty RuleSet.
	 */
	public RuleSet() {
		
	}
	
	/**
	 * Construct a RuleSet with given birth rules and survival rules.
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
