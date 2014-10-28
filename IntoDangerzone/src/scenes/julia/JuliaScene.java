package scenes.julia;

import math.Vector3D;
import graphics.Camera;
import processing.core.PApplet;
import core.ConstantProvider;
import core.InputProvider;
import core.Scene;

public class JuliaScene extends Scene {
	
	private JuliaSceneRenderer renderer;
	private Camera camera;
	private JuliaSet set;
	
	private InputProvider<Float> realProvider = new ConstantProvider<Float>(0.5f);

	public JuliaScene(PApplet parent) {
		super(parent);
		set = new JuliaSet();
		renderer = new JuliaSceneRenderer(parent, set);
		camera = new Camera(parent);
	}
	
	public JuliaScene(Papplet parent, AudioSource audioSource) {
		this(parent);
		this.realProvider = new SpectrumListener()
	}

	@Override
	public void update(float dtSeconds) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		float fovRadians = (float) Math.toRadians(75); // ?
		float zDistW = (float) ((parent.width/2.0f) / Math.tan(fovRadians/2.0f));
		float zDistH = (float) ((parent.height/2.0f) / Math.tan(fovRadians/2.0f));
		float zDist = Math.max(zDistW, zDistH);
		camera.setPosition(new Vector3D(0, 0, zDist));
		camera.setCenter(new Vector3D(0,0,0));
		camera.update();
		renderer.render();
	}
	
	public void setRealProvider(InputProvider<Float> provider) {
		this.realProvider = provider;
	}

}
