package audio;

import ddf.minim.AudioListener;
import ddf.minim.AudioSource;
import ddf.minim.analysis.BeatDetect;

public class BeatListener implements AudioListener {
	private BeatDetect beat;
	private AudioSource source;
	
	public BeatListener(AudioSource source) {
		this(source, 50);
	}

	public BeatListener(AudioSource source, int sensitivity) {
		this.source = source;
		this.source.addListener(this);
		beat = new BeatDetect(source.bufferSize(), source.sampleRate());
		beat.setSensitivity(sensitivity);
	}

	public void samples(float[] samps) {
		beat.detect(source.mix);
	}

	public void samples(float[] sampsL, float[] sampsR) {
		beat.detect(source.mix);
	}
	
	public boolean isKick() {
		return beat.isKick();
	}

	public boolean isSnare() {
		return beat.isSnare();
	}

	public boolean isHat() {
		return beat.isHat();
	}
	
	public boolean isOnset() {
		return beat.isOnset();
	}
}
