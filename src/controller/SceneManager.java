package controller;

import java.util.HashMap;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class SceneManager {
	private HashMap<String, Parent> screenMap = new HashMap<>();
	private Scene main;

	public SceneManager(Scene main) {
		this.main = main;
		this.main.getStylesheets()
				.addAll(getClass().getResource("/Home.css").toExternalForm());
		// getClass().getResource("/jfoenix-design.css").toExternalForm());
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

	public Parent getParent(String name) {
		return screenMap.get(name);
	}

	public Scene getScene() {
		return main;
	}
}
