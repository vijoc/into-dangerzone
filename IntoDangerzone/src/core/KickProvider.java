package core;
import audio.BeatListener;


public class KickProvider implements InputProvider<Boolean> {
	
	private BeatListener listener;
	
	public KickProvider(BeatListener listener) {
		this.listener = listener;
	}

	@Override
	public Boolean readInput() {
		return Boolean.valueOf(listener.isKick());
	}

}
