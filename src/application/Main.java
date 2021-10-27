package application;

import controller.ControllerManager;
import controller.IntroController;
import controller.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	public static SceneManager sceneManager;
	public static ControllerManager controllerManager;

	@Override
	public void start(Stage primaryStage_) throws Exception {
		controllerManager = new ControllerManager();
		sceneManager = new SceneManager();
		// Stage viewStage = new Stage(StageStyle.DECORATED);
		// FXMLLoader loader = new
		// FXMLLoader(getClass().getResource("View.fxml"));
		//

		// FXMLLoader has to be loaded for controller to be initialized.
		// ViewController viewController = loader.getController();
		// controllerManager.addViewController(viewController);
		// sceneManager.addStage("view", viewStage);
		// viewStage.getIcons().add(new Image("dictionary.png"));
		// viewStage.setTitle("Dictionary");
		// viewStage.setScene(scene);
		// sceneManager.activate("view");

		FXMLLoader loader =
				new FXMLLoader(getClass().getResource("Intro.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets()
				.addAll(getClass().getResource("/Home.css").toExternalForm());
		Stage menuStage = new Stage(StageStyle.UNDECORATED);
		menuStage.setScene(scene);
		menuStage.setTitle("Menu");
		sceneManager.addStage("menu", menuStage);
		IntroController introController = loader.getController();
		controllerManager.addIntroController(introController);
		sceneManager.activate("menu");
	}

	public static ControllerManager getControllerManager() {
		return controllerManager;
	}

	public static SceneManager getSceneManager() {
		return sceneManager;
	}

	public static void main(String[] args) {
		launch(args);
		System.exit(0);
	}

}
