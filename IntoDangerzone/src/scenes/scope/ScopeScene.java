package scenes.scope;

import audio.AudioAnalyser;
import ddf.minim.AudioSource;
import processing.core.PApplet;

public class ScopeScene extends core.Scene {

	private ScopeRenderer renderer;
	private AudioAnalyser audioAnalyser;

	public ScopeScene(PApplet parent, AudioSource audioSource) {
		super(parent);
		renderer = new ScopeRenderer(parent, audioSource);
		this.audioAnalyser = new AudioAnalyser(parent, audioSource);
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
