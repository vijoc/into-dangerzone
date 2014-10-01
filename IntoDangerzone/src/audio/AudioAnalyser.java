package audio;
import processing.core.*;
import ddf.minim.*;
import ddf.minim.analysis.BeatDetect;
import ddf.minim.analysis.FFT;

public class AudioAnalyser {

	Minim minim;
	AudioPlayer song;
	AudioInput input;
	FFT fft;
	BeatDetect beat;
	BeatListener bl;
	int numberOfBands;
	boolean readLineInput = false;

	public AudioAnalyser(PApplet applet) {
		minim = new Minim(applet);

		song = minim.loadFile("test.mp3");
		song.play();

		input = minim.getLineIn();

		fft = new FFT(song.bufferSize(), song.sampleRate());
		numberOfBands = fft.specSize();

		// Beat detection
		beat = new BeatDetect(song.bufferSize(), song.sampleRate());
		beat.setSensitivity(50);
		bl = new BeatListener(beat, song);

	}

	public float[] getSpectrum() {
		float[] spectrum = new float[numberOfBands];
		for (int i = 0; i < numberOfBands; i++) {
			spectrum[i] = fft.getBand(i);
		}
		return spectrum;
	}
	
	public FFT getFft() {
		return fft;
	}
	
	public AudioPlayer getSong() {
		return song;
	}

	public float[] getWaveform(int length) {
		float[] waveform = new float[length];
		for (int i = 0; i < length - 1; i++) {
			waveform[i] = song.mix.get(i);
		}
		return waveform;

		// Scope for line in
		/*
		 * AudioInput in = audioAnalyser.getAudioInput(); for(int i = 0; i <
		 * in.bufferSize() - 1; i++) { line( i, 50 + in.left.get(i)*50, i+1, 50
		 * + in.left.get(i+1)*50 ); line( i, 150 + in.right.get(i)*50, i+1, 150
		 * + in.right.get(i+1)*50 ); }
		 */
	}
	
	public float getZCR() {
		float zcr = 0;
		boolean overZero = false;
		float zeroCrossings = 0.f;
		for (int i = 0; i < song.bufferSize(); i++){
			if (
					((song.mix.get(i) > 0) && overZero) 
					|| ((song.mix.get(i) <= 0) && !overZero)){
				zeroCrossings++;
				overZero = !overZero;
			}
		}
		zcr = zeroCrossings / song.bufferSize();
		return zcr;
	}

	public float[] getLeftWaveform(int length) {
		float[] waveform = new float[length];
		for (int i = 0; i < length - 1; i++) {
			waveform[i] = song.left.get(i);
		}
		return waveform;
	}

	public float[] getRightWaveform(int length) {
		float[] waveform = new float[length];
		for (int i = 0; i < length - 1; i++) {
			waveform[i] = song.right.get(i);
		}
		return waveform;
	}

	public AudioInput getAudioInput() {
		return input;
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

	class BeatListener implements AudioListener {
		private BeatDetect beat;
		private AudioPlayer source;

		BeatListener(BeatDetect beat, AudioPlayer source) {
			this.source = source;
			this.source.addListener(this);
			this.beat = beat;
		}

		public void samples(float[] samps) {
			beat.detect(source.mix);
		}

		public void samples(float[] sampsL, float[] sampsR) {
			beat.detect(source.mix);
		}
	}
}
