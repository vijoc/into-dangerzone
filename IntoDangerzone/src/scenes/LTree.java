package scenes;

import audio.AudioAnalyser;
import processing.core.PApplet;

public class LTree extends core.Scene {
	AudioAnalyser audioAnalyser;
	float curlx = 0;
	float curly = 0;
	float f = (float) (Math.sqrt(2) / 2.0f);
	float delay = 10;
	float growth = 0;
	float growthTarget = 0;
	float curlXTarget = 0;
	float curlYTarget = 0;

	public LTree(PApplet parent, AudioAnalyser audioAnalyser) {
		super(parent);
		this.audioAnalyser = audioAnalyser;
	}

	@Override
	public void update(float dtSeconds) {
		if (audioAnalyser.isKick()) {
			curlXTarget = parent.random(100);
		}
		if (audioAnalyser.isSnare()) {
			curlYTarget = parent.random(100);
		}
	}

	@Override
	public void render() {
		parent.background(250);
		parent.stroke(0);
		curlx += (PApplet
				.radians((float) (curlXTarget)) - curlx)
				/ delay;
		curly += (PApplet
				.radians((float) (curlYTarget)) - curly)
				/ delay;
		parent.translate(parent.width / 2, parent.height / 3 * 2);
		parent.line(0, 0, 0, parent.height / 2);
		branch(parent.height / 4., 17);
		growth += (growthTarget / 10 - growth + 1.) / delay;
	}

	private void branch(double d, int num) {
		d *= f;
		num -= 1;
		if ((d > 1) && (num > 0)) {
			parent.pushMatrix();
			parent.rotate(curlx);
			parent.line(0, 0, 0, (float) -d);
			parent.translate(0, (float) -d);
			branch(d, num);
			parent.popMatrix();

			d *= growth;
			parent.pushMatrix();
			parent.rotate(curlx - curly);
			parent.line(0, 0, 0, (float) -d);
			parent.translate(0, (float) -d);
			branch(d, num);
			parent.popMatrix();
		}

	}
}
