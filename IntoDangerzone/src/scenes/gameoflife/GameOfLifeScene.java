package scenes.gameoflife;

import java.util.ArrayList;
import java.util.Random;

import audio.AudioAnalyser;
import audio.BeatListener;
import math.Vector3D;
import graphics.Camera;
import core.Scene;
import ddf.minim.AudioSource;
import processing.core.PApplet;

public class GameOfLifeScene extends Scene {

	private float generationTimer;
	private float stepDuration;

	private GameOfLife gol;
	private GameOfLifeRenderer golRenderer;

	private Camera camera;

	private Random rand;
	private boolean[][] bomb;

	private AudioAnalyser audioAnalyser;
	private BeatListener beatListener;
	
	/**
	 * Known good rule sets TODO I am a bit unsure where they actually belong
	 */
	private static final RuleSet AMOEBA = new RuleSet(new int[] { 3, 5, 7 },
			new int[] { 1, 3, 5, 8 });
	private static final RuleSet ASSIMILATION = new RuleSet(
			new int[] { 3, 4, 5 }, new int[] { 4, 5, 6, 7 });
	private static final RuleSet CORAL = new RuleSet(new int[] { 3 },
			new int[] { 4, 5, 6, 7, 8 });
	private static final RuleSet DAY_AND_NIGHT = new RuleSet(new int[] { 3, 6,
			7, 8 }, new int[] { 3, 4, 6, 7, 8 });
	private static final RuleSet GNARL = new RuleSet(new int[] { 1 },
			new int[] { 1 });
	private static final RuleSet GOL = new RuleSet(new int[] { 3 }, new int[] {
			2, 3 });
	private static final RuleSet LIFE_WITHOUT_DEATH = new RuleSet(
			new int[] { 3 }, new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 });
	private static final RuleSet MAZE = new RuleSet(new int[] { 3 }, new int[] {
			1, 2, 3, 4, 5 });
	private static final RuleSet MAZECTRIC = new RuleSet(new int[] { 3 },
			new int[] { 1, 2, 3, 4 });
	private static final RuleSet PSEUDO_LIFE = new RuleSet(
			new int[] { 3, 5, 7 }, new int[] { 2, 3, 8 });
	private static final RuleSet REPLICATOR = new RuleSet(new int[] { 1, 3, 5,
			7 }, new int[] { 1, 3, 5, 7 });
	private static final RuleSet STAINS = new RuleSet(new int[] { 3, 6, 7, 8 },
			new int[] { 2, 3, 5, 6, 7, 8 });
	private static final RuleSet TWO_X_TWO = new RuleSet(new int[] { 3, 6 },
			new int[] { 1, 2, 5 });
	private static final RuleSet WALLED_CITIES = new RuleSet(new int[] { 4, 5,
			6, 7, 8 }, new int[] { 2, 3, 4, 5 });
	public static final RuleSet[] RULE_SETS = { AMOEBA, ASSIMILATION, CORAL,
			DAY_AND_NIGHT, GNARL, GOL, LIFE_WITHOUT_DEATH, MAZE, MAZECTRIC,
			PSEUDO_LIFE, REPLICATOR, STAINS, TWO_X_TWO, WALLED_CITIES };


	public GameOfLifeScene(PApplet parent, AudioSource audioSource,
			float stepDuration, int columns, int rows) {
		super(parent);

		this.audioAnalyser = new AudioAnalyser(parent, audioSource);
		this.beatListener = new BeatListener(audioSource);

		gol = new GameOfLife(columns, rows);
		gol.seedRandom();

		this.golRenderer = new GameOfLifeRenderer(parent, gol);
		initializeStepTimer(stepDuration);
		this.camera = new Camera(parent);

		this.rand = new Random();

		this.bomb = new boolean[3][3];
		bomb[0][1] = true;
		bomb[1][0] = true;
		bomb[1][1] = true;
		bomb[1][2] = true;
		bomb[2][0] = true;
	}

	public GameOfLifeScene(PApplet parent, AudioSource audioSource,
			int columns, int rows) {
		this(parent, audioSource, 0.05f, columns, rows);
	}

	@Override
	public void update(float dtSeconds) {
		generationTimer -= dtSeconds;
		if (generationTimer < 0) {
			generationTimer = stepDuration;
			gol.stepGeneration();
			if (beatListener.isKick()) {
				gol.insertShape(bomb, rand.nextInt(gol.getColumnCount()),
						rand.nextInt(gol.getRowCount()));
			}
			if (beatListener.isHat()) {
				RuleSet ruleSet = RULE_SETS[rand.nextInt(RULE_SETS.length)];
				gol.setRuleSet(ruleSet);
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
		float zDistW = (float) ((parent.width / 2.0f) / Math
				.tan(fovRadians / 2.0f));
		float zDistH = (float) ((parent.height / 2.0f) / Math
				.tan(fovRadians / 2.0f));
		float zDist = Math.max(zDistW, zDistH);
		camera.setPosition(new Vector3D(0, 0, zDist));
		camera.setCenter(new Vector3D(0, 0, 0));
		camera.update();
	}

	private void initializeStepTimer(float stepDuration) {
		this.stepDuration = stepDuration;
		generationTimer = stepDuration;
	}

}
