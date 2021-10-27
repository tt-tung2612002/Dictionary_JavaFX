package controller;

import java.util.HashMap;

import javafx.stage.Stage;

public class SceneManager {
	private HashMap<String, Stage> screenMap = new HashMap<>();

	public void addStage(String name, Stage stage) {
		screenMap.put(name, stage);
	}

	public void removeScreen(String name) {
		screenMap.remove(name);
	}

	public void activate(String name) {
		screenMap.get(name).show();
	}

	public Stage getStage(String name) {
		return screenMap.get(name);
	}

}
