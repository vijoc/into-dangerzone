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
				parent.pixels[j * width + i] = parent
						.color(255 * (model.getWaveform(i) + model.getSpectrum(j)));
			}
		}
		parent.updatePixels();
	}

}
