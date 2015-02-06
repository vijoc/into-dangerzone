package scenes.scopeAndFFT;

import processing.core.PApplet;
import graphics.Renderer;

public class ScopeAndFFTRenderer extends Renderer {

	private ScopeAndFFT model;

	int width;
	int height;

	public ScopeAndFFTRenderer(PApplet parent, ScopeAndFFT model) {
		super(parent);
		this.model = model;

		width = parent.getWidth();
		height = parent.getHeight();
	}

	@Override
	public void render() {
		parent.loadPixels();

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				// TODO 2055 -> 255 :D
				parent.pixels[j * width + i] = parent
						.color(model.getWaveform(i) + model.getSpectrum(j));
			}
		}
		parent.updatePixels();
	}

}
