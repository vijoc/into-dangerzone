package scenes.julia;

import audio.AudioAnalyser;
import audio.BeatListener;
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
	
	private static final ComplexFunction[] FUNCTIONS = { 
		new ComplexFunction() {
			public Complex f(Complex z, Complex c) {
				return Complex.add(z.squared(), c);
			}
		},
		new ComplexFunction() {
			public Complex f(Complex z, Complex c) {
				return Complex.add(Complex.multiply(z.squared(), z), c);
			}
		},
		new ComplexFunction() {
			public Complex f(Complex z, Complex c) {
				return Complex.add(Complex.multiply(z.squared(), z.squared()), c.squared());
			}
		}
	};
	
	private JuliaSceneRenderer renderer;
	private Camera camera;
	private JuliaSet set;
	private ZcrListener zcrListener;
	private AudioAnalyser audioAnalyser;
	private BeatListener beatListener;
	
	private float minMagnitude = 0.3f;
	private float maxMagnitude = 1.75f;
	
	private float angularVelocity = 0.1f;
	private float magnitude = maxMagnitude;
	
	public JuliaScene(PApplet parent, AudioSource audioSource) {
		super(parent);
		set = new JuliaSet();
		renderer = new JuliaSceneRenderer(parent, set);
		camera = new Camera(parent);
		this.zcrListener = new ZcrListener(audioSource);
		this.audioAnalyser = new AudioAnalyser(parent, audioSource);
		this.beatListener = new BeatListener(audioSource);
		set.setFunction(FUNCTIONS[2]);
		set.setIterations(12);
	}

	@Override
	public void update(float dtSeconds) {
		audioAnalyser.getFft().forward(audioAnalyser.getAudioSource().mix);
		float avgEnergy = audioAnalyser.getFft().calcAvg(0, 200);
		angularVelocity = (float) Math.log(avgEnergy * 60);
		
		float phase = set.getC().phase() + angularVelocity * dtSeconds;
		if(phase > TWO_PI) phase -= TWO_PI;
		
		float zcr = zcrListener.getZCR();
		
		if(beatListener.isKick()) {
			magnitude -= 0.5;
		}
		
		magnitude += (maxMagnitude - magnitude) * dtSeconds;
		
		magnitude = Math.max(magnitude, minMagnitude);
		magnitude = Math.min(magnitude, maxMagnitude);
		
		set.setC(Complex.fromPolar(magnitude, phase));
	}

	@Override
	public void render() {
		renderer.render();
	}

	@Override
	public void activated() {
		updateCamera();
	}
	
	private void updateCamera() {
		float fovRadians = (float) Math.toRadians(75); // ?
		float zDistW = (float) ((parent.width/2.0f) / Math.tan(fovRadians/2.0f));
		float zDistH = (float) ((parent.height/2.0f) / Math.tan(fovRadians/2.0f));
		float zDist = Math.max(zDistW, zDistH);
		camera.setPosition(new Vector3D(0, 0, zDist));
		camera.setCenter(new Vector3D(0,0,0));
		camera.update();
	}

	@Override
	public void deactivated() {
		// TODO Auto-generated method stub
		
	}

}
