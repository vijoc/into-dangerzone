package core;
import audio.AudioAnalyser;


public class KickProvider implements InputProvider<Boolean> {

	private AudioAnalyser audioAnalyser;
	
	public KickProvider(AudioAnalyser audioAnalyser) {
		this.audioAnalyser = audioAnalyser;
	}

	@Override
	public Boolean readInput() {
		return Boolean.valueOf(audioAnalyser.isKick());
	}

}
