package scenes.lTree;

import java.util.Random;

import math.Vector3D;
import graphics.Camera;
import ddf.minim.AudioSource;
import audio.BeatListener;
import processing.core.PApplet;

public class LTree extends core.Scene {
	AudioSource audioSource;
	BeatListener beatListener;

	private Camera camera;

	float curlX = 0;
	float curlY = 0;
	int branchNumber = 14;
	float f = 0;
	float fTarget = 0.8f;
	float delay = 50;
	float growth = 0.1f;
	float growthTarget = 1.1f;
	float curlXTarget = 0;
	float curlYTarget = 0;
	Random rand;

	public LTree(PApplet parent, AudioSource audioSource) {
		super(parent);
		this.audioSource = audioSource;
		this.beatListener = new BeatListener(audioSource);
		this.camera = new Camera(parent);
		this.rand = new Random();
	}

	@Override
	public void update(float dtSeconds) {
		if (beatListener.isKick()) {
			curlXTarget += rand.nextFloat() * 5 - 10;
		}
		if (beatListener.isSnare()) {
			curlYTarget += rand.nextFloat() * 5 - 10;
		}
		if (beatListener.isHat()) {
			if (f < fTarget)
				f += 0.01;
			if (growth < growthTarget)
				growth += 0.01;
		}
	}

	@Override
	public void render() {
		parent.translate(0, 0);

		parent.background(250, 250, 250);
		parent.stroke(0, 0, 0, 50);
		curlX += (PApplet.radians((float) (curlXTarget)) - curlX) / delay;
		curlY += (PApplet.radians((float) (curlYTarget)) - curlY) / delay;
		branch(parent.height / 3, branchNumber);
		renderDebugTexts();
	}

	private void renderDebugTexts() {
		parent.stroke(0);
		parent.fill(0);
		parent.textSize(32);
		parent.text("curl X: " + curlX, parent.width / 2, 0);
		parent.text("curl Y: " + curlY, parent.width / 2, 64);
		parent.text("growth target: " + growthTarget, parent.width / 2, 128);
		parent.text("f: " + f, parent.width / 2, 192);
		parent.text("growth: " + growth, parent.width / 2, 256);
		parent.text("fps: " + parent.frameRate, parent.width / 2, 320);
	}

	private void branch(double d, int num) {
		d *= f;
		num -= 1;
		if ((d > 1) && (num > 0)) {
			parent.pushMatrix();
			parent.rotate(curlX);
			parent.line(0, 0, 0, (float) -d);
			parent.translate(0, (float) -d);
			branch(d, num);
			parent.popMatrix();

			d *= growth;
			parent.pushMatrix();
			parent.rotate(curlX - curlY);
			parent.line(0, 0, 0, (float) -d);
			parent.translate(0, (float) -d);
			branch(d, num);
			parent.popMatrix();
		}

	}

	@Override
	public void activated() {
		updateCamera();
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
		// TODO Auto-generated method stub

	}
}
