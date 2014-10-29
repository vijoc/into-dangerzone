package core;

import processing.core.PApplet;

/**
 * A scene is the unit of presentation.
 */
public abstract class Scene {
	
	protected PApplet parent;
	
	public Scene(PApplet parent) {
		this.parent = parent;
	}
	
	/** 
	 * Forward the internal state of the scene by given value.
	 * @param dtSeconds seconds since last call to update.
	 */
	public abstract void update(float dtSeconds);
	
	/**
	 * Render the state of the scene.
	 */
	public abstract void render();
	
	public abstract void activated();
	
	public abstract void deactivated();
	
}
