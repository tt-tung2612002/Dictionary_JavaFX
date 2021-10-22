package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MenuController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button switchView;

	@FXML
	private AnchorPane myAnchor;

	private Parent root;
	private Stage stage;
	private Scene scene;

	@FXML
	public void switchToView(ActionEvent event) throws IOException {
		Main.getSceneController().activate("view");
		Main.getStage().show();
	}

}
