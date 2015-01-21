package scenes.scope;

import ddf.minim.AudioSource;
import graphics.Renderer;
import processing.core.PApplet;
import processing.core.PConstants;

public class ScopeRenderer extends Renderer {
	
	private PApplet applet;
	private AudioSource audioSource;
	private float[] lBuffer;
	
	public ScopeRenderer(PApplet applet, AudioSource audioSource) {
		super(applet);
		this.audioSource = audioSource;
		this.applet = applet;
	}

	@Override
	public void render() {
		applet.background(0);
		lBuffer = audioSource.left.toArray();
		applet.stroke(255);
		for(int i = 0; i < lBuffer.length-1; i++) {
			applet.line(i-applet.width/2, lBuffer[i]*1000, i-applet.width/2, lBuffer[i+1]*1000);
		}
	}
}
