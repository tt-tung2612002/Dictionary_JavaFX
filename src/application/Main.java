package application;

import java.sql.SQLException;

import controller.ControllerManager;
import controller.EditController;
import controller.HelpController;
import controller.IntroController;
import controller.SceneManager;
import database.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	public static SceneManager sceneManager;
	public static ControllerManager controllerManager;
	public static DatabaseManager databaseManager;

	@Override
	public void start(Stage primaryStage_) throws Exception {
		controllerManager = new ControllerManager();
		sceneManager = new SceneManager();
		// databaseManager = new DatabaseManager();

		// load intro scene
		FXMLLoader loader =
				new FXMLLoader(getClass().getResource("Intro.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets()
				.addAll(getClass().getResource("/Home.css").toExternalForm());
		Stage introStage = new Stage(StageStyle.TRANSPARENT);
		scene.setFill(Color.TRANSPARENT);
		introStage.setScene(scene);
		introStage.setTitle("intro");
		sceneManager.addStage("intro", introStage);
		IntroController introController = loader.getController();
		controllerManager.addIntroController(introController);

		// load edit scene
		loader = new FXMLLoader(getClass().getResource("Edit.fxml"));
		Scene editScene = new Scene(loader.load());
		editScene.getStylesheets()
				.addAll(getClass().getResource("/Home.css").toExternalForm());
		Stage editStage = new Stage(StageStyle.UNDECORATED);
		editStage.setScene(editScene);
		EditController editController = loader.getController();
		controllerManager.addEditController(editController);
		sceneManager.addStage("edit", editStage);

		// load help scene
		loader = new FXMLLoader(getClass().getResource("Help.fxml"));
		Scene helpScene = new Scene(loader.load());
		helpScene.getStylesheets()
				.addAll(getClass().getResource("/Home.css").toExternalForm());
		Stage helpStage = new Stage(StageStyle.UNDECORATED);
		helpStage.setScene(helpScene);
		HelpController helpController = loader.getController();
		controllerManager.addHelpController(helpController);
		sceneManager.addStage("help", helpStage);

		sceneManager.activate("intro");
	}

	public static ControllerManager getControllerManager() {
		return controllerManager;
	}

	public static SceneManager getSceneManager() {
		return sceneManager;
	}

	public static void initializeDatabaseManager() throws SQLException {
		databaseManager = new DatabaseManager();
	}

	public static DatabaseManager getDatabaseManager() {
		return databaseManager;
	}

	public static void main(String[] args) {
		launch(args);
		System.exit(0);
	}

}
