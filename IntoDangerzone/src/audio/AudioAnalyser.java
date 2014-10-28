package audio;
import processing.core.*;
import ddf.minim.*;
import ddf.minim.analysis.FFT;

public class AudioAnalyser {

	Minim minim;
	AudioPlayer song;
	AudioInput input;
	AudioSource audioSource;
	FFT fft;
	int numberOfBands;
	boolean readLineInput = false;

	public AudioAnalyser(PApplet applet, AudioSource audioSource) {
		minim = new Minim(applet);

		this.audioSource = audioSource;
		song = minim.loadFile("test.mp3");

		input = minim.getLineIn();

		fft = new FFT(audioSource.bufferSize(), audioSource.sampleRate());
		numberOfBands = fft.specSize();
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
			waveform[i] = audioSource.mix.get(i);
		}
		return waveform;
	}
	
	public float getZCR() {
		float zcr = 0;
		boolean overZero = false;
		float zeroCrossings = 0.f;
		for (int i = 0; i < audioSource.bufferSize(); i++){
			if (
					((audioSource.mix.get(i) > 0) && overZero) 
					|| ((audioSource.mix.get(i) <= 0) && !overZero)){
				zeroCrossings++;
				overZero = !overZero;
			}
		}
		zcr = zeroCrossings / audioSource.bufferSize();
		return zcr;
	}

	public float[] getLeftWaveform(int length) {
		float[] waveform = new float[length];
		for (int i = 0; i < length - 1; i++) {
			waveform[i] = audioSource.left.get(i);
		}
		return waveform;
	}

	public float[] getRightWaveform(int length) {
		float[] waveform = new float[length];
		for (int i = 0; i < length - 1; i++) {
			waveform[i] = audioSource.right.get(i);
		}
		return waveform;
	}

	public AudioInput getAudioInput() {
		return input;
	}
	
	public AudioSource getAudioSource() {
		return audioSource;
	}

}
