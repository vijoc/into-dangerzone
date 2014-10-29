package scenes.lTree;

import ddf.minim.AudioSource;
import audio.BeatListener;
import processing.core.PApplet;

public class LTree extends core.Scene {
	AudioSource audioSource;
	BeatListener beatListener;
	float curlX = 0;
	float curlY = 0;
	float f = 0; // (float) (Math.sqrt(2) / 2.0f);
	float delay = 50;
	float growth = 0;
	float growthTarget = -8;
	float curlXTarget = 0;
	float curlYTarget = 0;

	public LTree(PApplet parent, AudioSource audioSource) {
		super(parent);
		this.audioSource = audioSource;
		this.beatListener = new BeatListener(audioSource);
	}

	@Override
	public void update(float dtSeconds) {
		if (beatListener.isKick()) {
			curlYTarget += parent.random(200) - 100;
			curlXTarget += parent.random(200) - 100;
		}
		if (beatListener.isSnare()) {
			// curlYTarget += parent.random(200)-100;
		}
		if (beatListener.isHat()) {
			if (growthTarget < -3)
				growthTarget += 0.01;
			if (f < 0.9)
				f += 0.001;
		}
	}

	@Override
	public void render() {
		parent.background(250, 250, 250, 10);
		parent.stroke(0, 0, 0, 25);
		curlX += (PApplet.radians((float) (curlXTarget)) - curlX) / delay;
		curlY += (PApplet.radians((float) (curlYTarget)) - curlY) / delay;
		parent.translate(0, parent.height / 2);
		parent.point(0,0);
//		parent.line(0, 0, 0, parent.height);
		branch(parent.height * 0.5, 17);
		growth += (growthTarget / 10 - growth + 1.) / delay;
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
		parent.text("fps: " + parent.frameRate, parent.width/2, 320);
	}

	private void branch(double d, int num) {
		d *= f;
		num -= 1;
		if ((d > 1) && (num > 0)) {
			parent.pushMatrix();
			parent.rotate(curlX);
			parent.point(0, 0);
			//parent.line(0, 0, 0, (float) -d);
			parent.translate(0, (float) -d);
			branch(d, num);
			parent.popMatrix();

			d *= growth;
			parent.pushMatrix();
			parent.rotate(curlX - curlY);
			parent.point(0, 0);
			//parent.line(0, 0, 0, (float) -d);
			parent.translate(0, (float) -d);
			branch(d, num);
			parent.popMatrix();
		}

	}

	@Override
	public void activated() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deactivated() {
		// TODO Auto-generated method stub
		
	}
}
