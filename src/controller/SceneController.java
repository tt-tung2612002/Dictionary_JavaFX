package controller;

import java.util.HashMap;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class SceneController {
	private HashMap<String, Parent> screenMap = new HashMap<>();
	private Scene main;

	public SceneController(Scene main) {
		this.main = main;
	}

	public void addScreen(String name, Parent pane) {
		screenMap.put(name, pane);
	}

	public void removeScreen(String name) {
		screenMap.remove(name);
	}

	public void activate(String name) {
		main.setRoot(screenMap.get(name));
	}
}
