package scenes.scope;

import ddf.minim.AudioSource;
import graphics.Renderer;
import processing.core.PApplet;
import processing.core.PConstants;

public class ScopeRenderer extends Renderer {
	
	private PApplet applet;
	private AudioSource audioSource;
	
	public ScopeRenderer(PApplet applet, AudioSource audioSource) {
		super(applet);
		this.audioSource = audioSource;
		this.applet = applet;
	}

	@Override
	public void render() {
		applet.background(0);
	}
}
