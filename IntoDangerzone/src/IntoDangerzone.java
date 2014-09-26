import processing.core.*;
import ddf.minim.*;

@SuppressWarnings("serial")
public class IntoDangerzone extends PApplet {

	Minim minim;
	AudioPlayer player;
	AudioInput input;
	
	public void setup() {
		size(200, 200);
		background(0);
		minim = new Minim(this);
		player = minim.loadFile("test.mp3");
		input = minim.getLineIn();
		player.play();
	}

	public void draw() {
		stroke(255);
		if (mousePressed) {
			line(mouseX, mouseY, pmouseX, pmouseY);
		}
	}

	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "MyProcessingSketch" });
	}
}