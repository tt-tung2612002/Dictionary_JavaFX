package controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class HelpController implements Initializable {

	@FXML
	private AnchorPane myAnchor;

	@FXML
	void switchToView(ActionEvent event) {
		myAnchor.getScene().getWindow().hide();
		Main.getSceneManager().activate("view");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}
}
