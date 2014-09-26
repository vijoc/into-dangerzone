import processing.core.*;
import ddf.minim.*;
import ddf.minim.analysis.FFT;

@SuppressWarnings("serial")
public class IntoDangerzone extends PApplet {

	Minim minim;
	AudioPlayer song;
	AudioInput input;
	FFT fft;
	
	// Camera parameters
	float eyeX, eyeY, eyeZ,
		  centerX, centerY, centerZ,
		  upX, upY, upZ;

	public void setup() {
		size(700, 200, P3D);
		background(0);
		
		eyeX = width/2.0f;
		eyeY = height/2.0f;
		eyeZ = (float) ((height/2.0f) / Math.tan(PI*30.0 / 180.0f));
		centerX = width/2.0f;
		centerY = height/2.0f;
		centerZ = 0;
		upX = 0;
		upY = 1;
		upZ = 0;
		
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
		
		camera(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
		
		// The following next two lines control 3D effect.
		eyeZ = eyeZ + 0.1f;
		eyeX = eyeX + 0.1f;
	}

	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "IntoDangerzone" });
	}
}