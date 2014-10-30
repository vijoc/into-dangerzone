package scenes.lTree;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.Random;

import math.Vector3D;
import graphics.Camera;
import ddf.minim.AudioSource;
import audio.BeatListener;
import processing.core.PApplet;

public class LTree extends core.Scene implements KeyEventDispatcher {

	private enum RenderingMode {
		LINES, POINTS
	};

	AudioSource audioSource;
	BeatListener beatListener;

	private Camera camera;

	float curlX = 0;
	float curlY = 0;
	int branchNumber = 15;

	float branchRatio = 0;
	float branchRatioTarget = 0.75f;

	float delay = 50;

	float growth = 0.15f;
	float growthTarget = 1.1f;

	float curlXTarget = 0;
	float curlYTarget = 0;

	float chanceOfChangeFromLines = 0.1f;
	float chanceOfChangeFromPoints = 0.4f;
	Random rand;

	RenderingMode renderingMode;

	public LTree(PApplet parent, AudioSource audioSource) {
		super(parent);
		this.audioSource = audioSource;
		this.beatListener = new BeatListener(audioSource);
		this.camera = new Camera(parent);
		this.rand = new Random();
		this.renderingMode = RenderingMode.LINES;
	}

	@Override
	public void update(float dtSeconds) {
		if (beatListener.isKick()) {
			curlYTarget += rand.nextFloat() * 50 - 25;
			if (rand.nextFloat() < getRenderingChangeChance())
				changeRenderingMode();
		}
		if (beatListener.isSnare()) {
			curlXTarget += rand.nextFloat() * 50 - 25;
		}
		if (beatListener.isHat()) {
			if (branchRatio < branchRatioTarget)
				branchRatio += 0.00025;
			if (growth < growthTarget)
				growth += 0.0005;
		}
	}

	@Override
	public void render() {
		clearBackground();
		parent.translate(0, 0);
		parent.stroke(0, 0, 0, 50);
		curlX += (PApplet.radians((float) (curlXTarget)) - curlX) / delay;
		curlY += (PApplet.radians((float) (curlYTarget)) - curlY) / delay;
		branch(parent.height / 3, branchNumber);
	}

	private void branch(double length, int branchNum) {
		length *= branchRatio;
		branchNum -= 1;
		if ((length > 1) && (branchNum > 0)) {
			parent.pushMatrix();
			parent.rotate(curlX);
			renderBranch(length);

			parent.translate(0, (float) -length);
			branch(length, branchNum);
			parent.popMatrix();

			length *= growth;
			parent.pushMatrix();
			parent.rotate(curlX - curlY);
			renderBranch(length);

			parent.translate(0, (float) -length);
			branch(length, branchNum);
			parent.popMatrix();
		}
	}

	private void clearBackground() {
		switch (renderingMode) {
		case POINTS:
			parent.fill(255, 10);
			parent.noStroke();
			parent.rectMode(PApplet.RADIUS);
			parent.rect(0, 0, parent.width, parent.height);
			parent.rectMode(PApplet.CORNER);
			break;
		case LINES:
			parent.background(255, 255, 255);
			break;
		default:
			break;
		}
	}

	private void renderBranch(double length) {
		switch (renderingMode) {
		case POINTS:
			parent.point(0, 0);
			break;
		case LINES:
			parent.line(0, 0, 0, (float) -length);
			break;
		default:
			break;
		}
	}

	public void changeRenderingMode() {
		switch (renderingMode) {
		case LINES:
			renderingMode = RenderingMode.POINTS;
			break;
		case POINTS:
			renderingMode = RenderingMode.LINES;
			break;
		default:
			break;
		}
	}

	private float getRenderingChangeChance() {
		float result = 1.0f;
		switch (renderingMode) {
		case LINES:
			result = chanceOfChangeFromLines;
			break;
		case POINTS:
			result = chanceOfChangeFromPoints;
			break;
		default:
			break;
		}
		return result;
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		switch (e.getID()) {
		case KeyEvent.KEY_PRESSED:
			keyPress(e.getKeyCode());
			break;
		}
		return false;
	}

	private void keyPress(int code) {
		switch (code) {
		case KeyEvent.VK_SPACE:
			changeRenderingMode();
			break;
		case KeyEvent.VK_D:
			break;
		case KeyEvent.VK_UP:
			curlXTarget += 10;
			break;
		case KeyEvent.VK_DOWN:
			curlXTarget -= 10;
			break;
		case KeyEvent.VK_RIGHT:
			curlYTarget += 10;
			break;
		case KeyEvent.VK_LEFT:
			curlYTarget -= 10;
			break;
		}
	}

	@Override
	public void activated() {
		updateCamera();
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(this);
	}

	private void updateCamera() {
		float fovRadians = (float) Math.toRadians(45); // ?
		float zDistW = (float) ((parent.width / 2.0f) / Math
				.tan(fovRadians / 2.0f));
		float zDistH = (float) ((parent.height / 2.0f) / Math
				.tan(fovRadians / 2.0f));
		float zDist = Math.max(zDistW, zDistH);
		camera.setPosition(new Vector3D(0, 0, zDist));
		camera.setCenter(new Vector3D(0, 0, 0));
		camera.update();
	}

	@Override
	public void deactivated() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.removeKeyEventDispatcher(this);

	}
}
