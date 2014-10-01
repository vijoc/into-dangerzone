package core;
import audio.AudioAnalyser;

public class SpectrumProvider implements InputProvider<float[]> {
	
	private AudioAnalyser audioAnalyser;

	public SpectrumProvider(AudioAnalyser audioAnalyser) {
		this.audioAnalyser = audioAnalyser;
	}

	@Override
	public float[] readInput() {
		return audioAnalyser.getSpectrum();
	}
}