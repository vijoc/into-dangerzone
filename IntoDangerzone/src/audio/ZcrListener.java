package audio;

import ddf.minim.AudioListener;
import ddf.minim.AudioSource;

public class ZcrListener implements AudioListener {
	
	private float zcr;
	private AudioSource audioSource;
	
	public ZcrListener(AudioSource audioSource) {
		this.audioSource = audioSource;
		audioSource.addListener(this);
	}

	@Override
	public void samples(float[] arg0) {
		updateZcr();
		
	}

	@Override
	public void samples(float[] arg0, float[] arg1) {
		updateZcr();
	}
	
	private void updateZcr() {
		boolean overZero = false;
		float zeroCrossings = 0.f;
		for (int i = 0; i < audioSource.bufferSize(); i++) {
			if (((audioSource.mix.get(i) > 0) && overZero)
					|| ((audioSource.mix.get(i) <= 0) && !overZero)) {
				zeroCrossings++;
				overZero = !overZero;
			}
		}
		this.zcr = zeroCrossings / audioSource.bufferSize();
	}
	
	public float getZCR() {
		return zcr;
	}

}
