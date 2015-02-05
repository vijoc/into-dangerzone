package scenes.scope;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import math.Vector2D;
import audio.AudioAnalyser;
import ddf.minim.AudioSource;
import processing.core.PApplet;
import scenes.gameoflife.RuleSet;
import util.Pair;

public class ScopeScene extends core.Scene implements KeyEventDispatcher {

	private ScopeRenderer renderer;
	private AudioAnalyser audioAnalyser;

	public ScopeScene(PApplet parent, AudioSource audioSource) {
		super(parent);
		renderer = new ScopeRenderer(parent, audioSource);
		this.audioAnalyser = new AudioAnalyser(parent, audioSource);
	}

	@Override
	public void update(float dtSeconds) {
		// TODO Auto-generated method stub

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
		switch (e.getID()) {
		case KeyEvent.KEY_PRESSED:
			keyPress(e.getKeyCode());
			break;
		}
		return false;
	}

	private void keyPress(int code) {
		switch (code) {
		case KeyEvent.VK_RIGHT:
			renderer.addPair();
			break;
		case KeyEvent.VK_LEFT:
			renderer.removePair();
			break;
		case KeyEvent.VK_UP:
			renderer.increaseScaling();
			break;
		case KeyEvent.VK_DOWN:
			renderer.decreaseScaling();
			break;
		case KeyEvent.VK_SPACE:
			renderer.flipRender();
			break;
		}
	}

}
