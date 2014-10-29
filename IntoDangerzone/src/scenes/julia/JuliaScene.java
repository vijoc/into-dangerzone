package scenes.julia;

import audio.AudioAnalyser;
import audio.ZcrListener;
import math.Complex;
import math.Vector3D;
import graphics.Camera;
import processing.core.PApplet;
import scenes.julia.JuliaSceneRenderer.RenderMode;
import core.Scene;
import ddf.minim.AudioSource;

public class JuliaScene extends Scene {
	
	private static final float TWO_PI = (float) (2 * Math.PI);
	
	private JuliaSceneRenderer renderer;
	private Camera camera;
	private JuliaSet set;
	private ZcrListener zcrListener;
	private AudioAnalyser audioAnalyser;
	
	private float angularVelocity = 0.1f;
	private float magnitude = 2;
	
	private float minMagnitude = 0;
	private float maxMagnitude = 1.5f;
	
	public JuliaScene(PApplet parent, AudioSource audioSource) {
		super(parent);
		set = new JuliaSet();
		renderer = new JuliaSceneRenderer(parent, set);
		camera = new Camera(parent);
		this.zcrListener = new ZcrListener(audioSource);
		this.audioAnalyser = new AudioAnalyser(parent, audioSource);
		set.setFunction(new ComplexFunction() {
			public Complex f(Complex z, Complex c) {
				return Complex.add(Complex.multiply(z.squared(), z), c);
			}
		});
	}

	@Override
	public void update(float dtSeconds) {
		audioAnalyser.getFft().forward(audioAnalyser.getAudioSource().mix);
		float avgEnergy = audioAnalyser.getFft().calcAvg(0, 200);
		angularVelocity = avgEnergy * 100;
		
		float phase = set.getC().phase() + angularVelocity * dtSeconds;
		if(phase > TWO_PI) phase -= TWO_PI;
		
		float zcr = zcrListener.getZCR();
		if(zcr > 0) {
			magnitude += 15 * zcr * dtSeconds;
		}
		
		magnitude -= 0.7 * magnitude * dtSeconds;
		
		magnitude = Math.max(magnitude, minMagnitude);
		magnitude = Math.min(magnitude, maxMagnitude);
		
		set.setC(Complex.fromPolar(magnitude, phase));
	}

	@Override
	public void render() {
		float fovRadians = (float) Math.toRadians(75); // ?
		float zDistW = (float) ((parent.width/2.0f) / Math.tan(fovRadians/2.0f));
		float zDistH = (float) ((parent.height/2.0f) / Math.tan(fovRadians/2.0f));
		float zDist = Math.max(zDistW, zDistH);
		camera.setPosition(new Vector3D(0, 0, zDist));
		camera.setCenter(new Vector3D(0,0,0));
		camera.update();
		renderer.render();
	}

}
