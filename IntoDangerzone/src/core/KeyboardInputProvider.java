package core;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class KeyboardInputProvider implements InputProvider<Boolean>, KeyEventDispatcher {
		
	protected int[] keyCodes;
	protected int[] excluded;
	protected ArrayList<Integer> pressed = new ArrayList<Integer>();
	
	public KeyboardInputProvider(int keyCode) {
		this(new int[] { keyCode }, new int[0]);
	}
	
	public KeyboardInputProvider(int[] keyCodes) {
		this(keyCodes, new int[0]);
	}
	
	public KeyboardInputProvider(int keyCode, int excluded) {
		this(new int[] { keyCode }, new int[] { excluded });
	}
	
	public KeyboardInputProvider(int[] keyCodes, int[] excluded) {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
		this.keyCodes = keyCodes;
		this.excluded = excluded;
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		switch(e.getID()) {
		case KeyEvent.KEY_PRESSED:
			pressed(e.getKeyCode());
			break;
		case KeyEvent.KEY_RELEASED:
			released(e.getKeyCode());
			break;
		}
		return false;
	}

	@Override
	public Boolean readInput() {
		for(int code : keyCodes) {
			if(!pressed.contains(Integer.valueOf(code))) return false;
		}
		for(int code : excluded) {
			if(pressed.contains(Integer.valueOf(code))) return false;
		}
		return true;
	}
	
	private void pressed(int code) {
		if(pressed.contains(Integer.valueOf(code))) return;
		pressed.add(Integer.valueOf(code));
	}
	
	private void released(int code) {
		if(!pressed.contains(Integer.valueOf(code))) return;
		pressed.remove(Integer.valueOf(code));
	}
}
