package scenes.scopeAndFFT;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import processing.core.PApplet;
import ddf.minim.AudioSource;

public class ScopeAndFFTScene extends core.Scene implements KeyEventDispatcher {
	
	public static final float SPECTRUM_SCALING_FACTOR_STEP = 0.1f;
	public static final float WAVEFORM_SCALING_FACTOR_STEP = 0.1f;

	private ScopeAndFFTRenderer renderer;
	private ScopeAndFFT model;
	
	public ScopeAndFFTScene(PApplet applet,
			AudioSource audioSource) {
		super(applet);
		this.model = new ScopeAndFFT(parent, audioSource);
		renderer = new ScopeAndFFTRenderer(parent, model);
	}

	@Override
	public void update(float dtSeconds) {
		model.update();		
	}

	@Override
	public void render() {
		renderer.render();		
	}

	@Override
	public void activated() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
			.addKeyEventDispatcher(this);
		
	}

	@Override
	public void deactivated() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
			.removeKeyEventDispatcher(this);
		
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		switch(e.getID()) {
		case KeyEvent.KEY_PRESSED:
			keyPress(e.getKeyCode());
		}
		return false;
	}
	
	private void keyPress(int keycode) {
		switch(keycode) {
		case KeyEvent.VK_DOWN:
			model.changeSpectrumScalingFactor(-SPECTRUM_SCALING_FACTOR_STEP);
			break;
		case KeyEvent.VK_UP:
			model.changeSpectrumScalingFactor(SPECTRUM_SCALING_FACTOR_STEP);
			break;
		case KeyEvent.VK_LEFT:
			model.changeWaveformScalingFactor(-WAVEFORM_SCALING_FACTOR_STEP);
			break;
		case KeyEvent.VK_RIGHT:
			model.changeWaveformScalingFactor(WAVEFORM_SCALING_FACTOR_STEP);
			break;
		case KeyEvent.VK_SPACE:
			model.setFolding(!model.getFolding());
		}
	}

}
