package scenes.scopeAndFFT;

import audio.AudioAnalyser;
import processing.core.PApplet;
import ddf.minim.AudioSource;
import ddf.minim.analysis.FFT;

public class ScopeAndFFTScene extends core.Scene {

	private ScopeAndFFTRenderer renderer;
	private ScopeAndFFT model;
	


	public ScopeAndFFTScene(PApplet applet,
			AudioSource audioSource) {
		super(applet);
		this.model = new ScopeAndFFT(parent, audioSource);
		renderer = new ScopeAndFFTRenderer(parent, model);
	}

	@Override
	public void update(float dtSeconds) {
		model.update();		
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
