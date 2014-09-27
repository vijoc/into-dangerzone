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
	float kickSize, snareSize, hatSize;
	int numberOfBands;
	
	
	public AudioAnalyser(PApplet applet) {
		minim = new Minim(applet);
		input = minim.getLineIn();

		song = minim.loadFile("test.mp3");
		song.play();

		fft = new FFT(song.bufferSize(), song.sampleRate());
		numberOfBands = fft.specSize();

		// Beat detection
		beat = new BeatDetect(song.bufferSize(), song.sampleRate());
		beat.setSensitivity(50);
		kickSize = snareSize = hatSize = 16;
		bl = new BeatListener(beat, song);
		
	}
	
	public float[] getSpectrum() {
		float[] spectrum = new float[numberOfBands];
		for (int i = 0; i < numberOfBands; i++){
			spectrum[i] = fft.getBand(i);
		}
		return spectrum;
	}
	
	public AudioInput getAudioInput() {
		return input;
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
