package scenes.scopeAndFFT;

import audio.AudioAnalyser;
import ddf.minim.AudioSource;
import processing.core.PApplet;
import processing.core.PGraphics;
import graphics.Renderer;

public class ScopeAndFFTRenderer extends Renderer {

	private PApplet applet;
	private AudioSource audioSource;
	private AudioAnalyser audioAnalyser;
	private float[] waveform;
	PGraphics context;

	int width;
	int height;

	public ScopeAndFFTRenderer(PApplet parent, AudioSource audioSource) {
		super(parent);
		this.applet = parent;
		this.audioSource = audioSource;
		this.audioAnalyser = new AudioAnalyser(parent, audioSource);

		width = applet.getWidth();
		height = applet.getHeight();
	}

	@Override
	public void render() {
		applet.loadPixels();
		waveform = audioAnalyser.getWaveform(audioSource.bufferSize());
		int waveformLength = waveform.length;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				applet.pixels[j*width + i] = applet.color((i*j)%256);
			}
		}
		applet.updatePixels();
	}

}
