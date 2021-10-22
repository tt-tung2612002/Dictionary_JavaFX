package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import controller.SceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

	public static Stage primaryStage;
	public static SceneController screenController;
	public static Scene scene;

	@Override
	public void start(Stage primaryStage_) throws Exception {
		Parent root =
				FXMLLoader.load(
						getClass().getClassLoader().getResource("View.fxml"));
		scene = new Scene(root, 800, 600);
		screenController = new SceneController(scene);
		screenController.addScreen("view", root);
		screenController.addScreen("menu", FXMLLoader
				.load(getClass().getClassLoader().getResource("Menu.fxml")));
		screenController.activate("view");
		primaryStage = primaryStage_;
		primaryStage.getIcons().add(new Image("dictionary.png"));
		primaryStage.setTitle("Dictionary");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static SceneController getSceneController() {
		return screenController;
	}

	public static Stage getStage() {
		return primaryStage;
	}

	public static String translate(String langFrom, String langTo, String text)
			throws IOException {
		// INSERT YOU URL HERE
		String urlStr =
				"https://script.google.com/macros/s/AKfycbw2qKkvobro8WLNZUKi2kGwGwEO4W8cBavcKqcuCIGhGBBtVts/exec"
						+ "?q=" + URLEncoder.encode(text, "UTF-8") + "&target="
						+ langTo + "&source=" + langFrom;
		URL url = new URL(urlStr);
		StringBuilder response = new StringBuilder();
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		BufferedReader in =
				new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
	}

	public static void main(String[] args) {
		launch(args);
		System.exit(0);
	}

}
