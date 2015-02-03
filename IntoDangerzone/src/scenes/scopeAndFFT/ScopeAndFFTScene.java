package scenes.scopeAndFFT;

import audio.AudioAnalyser;
import processing.core.PApplet;
import ddf.minim.AudioSource;

public class ScopeAndFFTScene extends core.Scene {

	private ScopeAndFFTRenderer renderer;

	public ScopeAndFFTScene(PApplet applet,
			AudioSource audioSource) {
		super(applet);
		renderer = new ScopeAndFFTRenderer(applet, audioSource);
	}

	@Override
	public void update(float dtSeconds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		renderer.render();		
	}

	@Override
	public void activated() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deactivated() {
		// TODO Auto-generated method stub
		
	}

}
