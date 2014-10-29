package audio;

import ddf.minim.AudioListener;
import ddf.minim.AudioSource;
import ddf.minim.analysis.BeatDetect;

public class TriggeredBeatListener implements AudioListener {
	
	private BeatDetect beat;
	private AudioSource source;
	
	private boolean kickTriggered;
	private boolean snareTriggered;
	private boolean hatTriggered;

	public TriggeredBeatListener(AudioSource source, int sensitivity) {
		this.source = source;
		source.addListener(this);
		beat = new BeatDetect(source.bufferSize(), source.sampleRate());
		beat.setSensitivity(sensitivity);
	}
	
	public TriggeredBeatListener(AudioSource source) {
		this(source, 50);
	}
	
	@Override
	public void samples(float[] arg0) {
		beat.detect(source.mix);
		updateTriggered();
	}

	@Override
	public void samples(float[] arg0, float[] arg1) {
		beat.detect(source.mix);
		updateTriggered();
	}
	
	public boolean kick() { return kickTriggered; }
	public boolean snare() { return snareTriggered; }
	public boolean hat() { return hatTriggered; }
	
	public void reset() {
		kickTriggered = false;
		snareTriggered = false;
		hatTriggered = false;
	}
	
	private void updateTriggered() {
		if(beat.isKick()) kickTriggered = true;
		if(beat.isSnare()) snareTriggered = true;
		if(beat.isHat()) hatTriggered = true;
	}

}
