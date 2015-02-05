package scenes.scopeAndFFT;

import audio.AudioAnalyser;
import ddf.minim.AudioSource;
import ddf.minim.analysis.FFT;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.opengl.PShader;
import graphics.Renderer;

public class ScopeAndFFTRenderer extends Renderer {

	private PApplet applet;
	private AudioSource audioSource;
	private AudioAnalyser audioAnalyser;
	private float[] waveform;
	private FFT spectrum;
	PGraphics context;
	PShader safshader;

	int width;
	int height;

	public ScopeAndFFTRenderer(PApplet parent, AudioSource audioSource) {
		super(parent);
		this.applet = parent;
		this.audioSource = audioSource;
		this.audioAnalyser = new AudioAnalyser(parent, audioSource);

		width = applet.getWidth();
		height = applet.getHeight();

		safshader = applet.loadShader("saffrag.glsl", "safvert.glsl");
		applet.noStroke();
	}

	@Override
	public void render() {
		applet.shader(safshader);
		applet.background(0);
		waveform = audioAnalyser.getWaveform(audioSource.bufferSize());
		int waveformLength = waveform.length;
		spectrum = audioAnalyser.getFft();
		spectrum.forward(waveform);
		float[] realSpectrum = spectrum.getSpectrumReal();

		applet.translate(-width / 2, -height / 2);
		
		safshader.set("resolution", (float) width, (float) height);
		safshader.set("bufferLength", waveformLength);
		safshader.set("fft", realSpectrum);
		safshader.set("waveform", waveform);
		//safshader.set("width", (float) width);
		//safshader.set("height", (float) height);
		applet.rect(0, 0, width, height);
	}

}
