import processing.core.*;
import ddf.minim.*;
import ddf.minim.analysis.FFT;

@SuppressWarnings("serial")
public class IntoDangerzone extends PApplet {

	Minim minim;
	AudioPlayer song;
	AudioInput input;
	FFT fft;

	public void setup() {
		size(700, 200);
		background(0);
		
		minim = new Minim(this);
		
		song = minim.loadFile("test.mp3");
		song.play();
		
		fft = new FFT(song.bufferSize(), song.sampleRate());
	}

	public void draw() {
		background(0);
		
		fft.forward(song.mix);

		stroke(255, 0, 0, 128);
		
		for (int i = 0; i < fft.specSize(); i++) {
			line(i, height, i, height - fft.getBand(i) * 4);
		}

		stroke(255);

		for (int i = 0; i < width - 1; i++) {
			line(i, 50 + song.left.get(i) * 50, i + 1,
					50 + song.left.get(i + 1) * 50);
			line(i, 150 + song.right.get(i) * 50, i + 1,
					150 + song.right.get(i + 1) * 50);
		}
	}

	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "IntoDangerzone" });
	}
}