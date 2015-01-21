package scenes.scope;

import ddf.minim.AudioSource;
import processing.core.PApplet;

public class ScopeScene extends core.Scene {

	private ScopeRenderer renderer;

	public ScopeScene(PApplet parent, AudioSource audioSource) {
		super(parent);
		renderer = new ScopeRenderer(parent, audioSource);
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
