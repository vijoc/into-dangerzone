package scenes.julia;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.Random;

import audio.AudioAnalyser;
import audio.BeatListener;
import audio.TriggeredBeatListener;
import audio.ZcrListener;
import math.Complex;
import math.Vector3D;
import graphics.Camera;
import processing.core.PApplet;
import scenes.julia.JuliaSceneRenderer.RenderMode;
import core.InputProvider;
import core.KickProvider;
import core.PositiveEdgeTrigger;
import core.Scene;
import ddf.minim.AudioSource;

public class JuliaScene extends Scene implements KeyEventDispatcher {
	
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
	private TriggeredBeatListener beatListener;
	
	private float minMagnitude = 0.75f;
	private float maxMagnitude = 1.25f;
	private float magnitudeChange = -0.01f;
	
	private float angularVelocity = 0.1f;
	private float magnitude = maxMagnitude;
	
	private int angularDirection = 1;
	private Random rand = new Random();
	
	private InputProvider<Boolean> explosionProvider;

	private int fnIndex;
	
	public JuliaScene(PApplet parent, AudioSource audioSource) {
		super(parent);
		set = new JuliaSet();
		renderer = new JuliaSceneRenderer(parent, set);
		camera = new Camera(parent);
		this.zcrListener = new ZcrListener(audioSource);
		this.audioAnalyser = new AudioAnalyser(parent, audioSource);
		this.beatListener = new TriggeredBeatListener(audioSource, 100);
		set.setFunction(FUNCTIONS[fnIndex]);
		set.setIterations(15);
	}

	@Override
	public void update(float dtSeconds) {
		audioAnalyser.getFft().forward(audioAnalyser.getAudioSource().mix);
		float avgEnergy = audioAnalyser.getFft().calcAvg(120, 2000);
		angularVelocity = avgEnergy*angularDirection / (float) Math.PI;
		
		float phase = set.getC().phase() + angularVelocity * dtSeconds;
		
		/*magnitude += magnitudeChange * dtSeconds;
		if(magnitude < minMagnitude) {
			magnitudeChange = 0.01f;
		}
		if(magnitude > maxMagnitude) {
			magnitudeChange = -0.01f;
		}*/
		
		if(beatListener.kick()) {
			if(rand.nextFloat() < 0.2f) { angularDirection *= -1; }
			magnitude -= (maxMagnitude * audioAnalyser.getFft().calcAvg(0, 120) - magnitude) / 20;
		}
		
		magnitude += (maxMagnitude - magnitude) / 20;
		
		magnitude = Math.min(magnitude, maxMagnitude);
		magnitude = Math.max(magnitude, minMagnitude);
		
		set.setC(Complex.fromPolar(magnitude, phase));
 
		beatListener.reset();
	}

	@Override
	public void render() {
		renderer.render();
	}

	@Override
	public void activated() {
		updateCamera();
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
	}

	@Override
	public void deactivated() {
		// TODO Auto-generated method stub
		KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(this);
		
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
	public boolean dispatchKeyEvent(KeyEvent e) {
		switch(e.getID()) {
		case KeyEvent.KEY_PRESSED:
			keyPress(e.getKeyCode());
			break;
		}
		return false;
	}
	
	private void keyPress(int code) {
		switch(code) {
		case KeyEvent.VK_SPACE:
			set.setFunction(FUNCTIONS[nextFunctionIndex()]);
			break;
		case KeyEvent.VK_D:
			renderer.toggleDebug();
			break;
		case KeyEvent.VK_UP:
			set.setIterations(set.getIterations()+1);
			break;
		case KeyEvent.VK_DOWN:
			set.setIterations(set.getIterations()-1);
			break;
		case KeyEvent.VK_M:
			renderer.toggleMode();
			break;
		case KeyEvent.VK_C:
			renderer.toggleColorMode();
			break;
		}
	}

	private int nextFunctionIndex() {
		int next = fnIndex + 1;
		if(next >= FUNCTIONS.length) next -= FUNCTIONS.length;
		fnIndex = next;
		return next;
	}

}
