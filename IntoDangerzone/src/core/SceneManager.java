package core;

import java.util.ArrayList;

public class SceneManager {

	ArrayList<Scene> scenes = new ArrayList<Scene>();
	private Scene activeScene;
	
	public void addScene(Scene scene) {
		scenes.add(scene);
	}
	
	public Scene getActiveScene() {
		return activeScene;
	}
	
	public void updateActiveScene(float dt) {
		if(activeScene == null) return;
		activeScene.update(dt);
	}
	
	public void renderActiveScene() {
		if(activeScene == null) return;
		activeScene.render();
	}
	
	public void setActiveScene(int index) {
		if(index < 0 || index >= scenes.size()) return;
		
		activeScene = scenes.get(index);
	}
	
	public void setActiveScene(Scene scene) {
		int index = scenes.indexOf(scene);
		if(index >= 0) setActiveScene(index);
	}
	
	
}
