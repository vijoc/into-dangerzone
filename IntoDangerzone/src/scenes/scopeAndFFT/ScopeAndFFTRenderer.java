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
	}

	@Override
	public void render() {
		applet.shader(safshader);
		waveform = audioAnalyser.getWaveform(audioSource.bufferSize());
		int waveformLength = waveform.length;
		spectrum = audioAnalyser.getFft();
		spectrum.forward(waveform);
		float[] realSpectrum = spectrum.getSpectrumReal();
		/*
		 * applet.loadPixels(); for (int i = 0; i < width; i++) { for (int j =
		 * 0; j < height; j++) { // TODO 2055 -> 255 :D applet.pixels[j * width
		 * + i] = applet .color(2055 * waveform[i % waveformLength] + 255
		 * realSpectrum[j%realSpectrum.length]); } } applet.updatePixels();
		 */
		applet.translate(-width / 2, -height / 2);
		safshader.set("fft", realSpectrum);
		safshader.set("waveform", waveform);

		applet.rect(0, 0, width, height);
	}

}
