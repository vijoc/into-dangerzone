package graphics;
import processing.core.PApplet;

public abstract class Renderer {
	protected PApplet parent;
	
	public Renderer(PApplet parent) {
		this.parent = parent;
	}
	
	public abstract void render();
}
