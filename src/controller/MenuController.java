package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class MenuController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button switchView;

	@FXML
	private AnchorPane myAnchor;

	@FXML
	private TextField field;

	@FXML
	public void switchToView(ActionEvent event) throws IOException {
		Main.getSceneManager().activate("view");
		Main.getStage().show();
		Main.getControllerManager().getViewController().getTextField()
				.setText("hello");
	}

	void initialize() {
		field.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {

				}
			}
		});
	}

	public TextField getTextField() {
		return field;
	}

}
