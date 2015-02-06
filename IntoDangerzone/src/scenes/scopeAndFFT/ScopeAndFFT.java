package scenes.scopeAndFFT;

import processing.core.PApplet;
import audio.AudioAnalyser;
import ddf.minim.AudioSource;
import ddf.minim.analysis.FFT;

public class ScopeAndFFT {
	
	public static final float MIN_WAVEFORM_SCALING_FACTOR = 0.1f;
	public static final float MAX_WAVEFORM_SCALING_FACTOR = 10.f;
	public static final float MIN_SPECTRUM_SCALING_FACTOR = 0.1f;
	public static final float MAX_SPECTRUM_SCALING_FACTOR = 10.f;
	
	private AudioSource audioSource;
	private AudioAnalyser audioAnalyser;

	private FFT spectrum;
	private float[] waveform;
	private float[] realSpectrum;
	
	private float waveformScalingFactor = 1.0f;
	private float spectrumScalingFactor = 1.0f;
	
	private int waveformBufferLength;
	private int spectrumBufferLength;
	
	public ScopeAndFFT(PApplet applet, AudioSource audioSource) {
		this.audioSource = audioSource;
		this.audioAnalyser = new AudioAnalyser(applet, audioSource);
	}
	
	public void update() {
		waveform = audioAnalyser.getWaveform(audioSource.bufferSize());
		waveformBufferLength = waveform.length;
		
		spectrum = audioAnalyser.getFft();
		spectrum.forward(waveform);
		realSpectrum = spectrum.getSpectrumReal();
		spectrumBufferLength = realSpectrum.length;
	}
	
	public float getWaveform(int index) {
		return 2055 * waveform[index % waveformBufferLength];
	}
	
	public float getSpectrum(int index) {
		return 255 * realSpectrum[index % spectrumBufferLength];
	}
	
}
