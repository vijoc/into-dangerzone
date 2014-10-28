package scenes.julia;

import audio.ZcrListener;
import math.Vector3D;
import graphics.Camera;
import processing.core.PApplet;
import core.Scene;
import ddf.minim.AudioSource;

public class JuliaScene extends Scene {
	
	private JuliaSceneRenderer renderer;
	private Camera camera;
	private JuliaSet set;
	private ZcrListener zcrListener;
	
	public JuliaScene(PApplet parent, AudioSource audioSource) {
		super(parent);
		set = new JuliaSet();
		renderer = new JuliaSceneRenderer(parent, set);
		camera = new Camera(parent);
		this.zcrListener = new ZcrListener(audioSource);
	}

	@Override
	public void update(float dtSeconds) {
		set.setCx((float) Math.log(zcrListener.getZCR()*5));
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

}
