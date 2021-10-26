package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXSpinner;

import application.Main;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

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
	private JFXSpinner blueSpinner;

	@FXML
	private JFXSpinner greenSpinner;

	@FXML
	public void switchToView(ActionEvent event) throws IOException {
		Main.getSceneManager().activate("view");
		Main.getStage().show();
	}

	void initialize() {
		blueSpinner = new JFXSpinner();
		greenSpinner = new JFXSpinner();
		greenSpinner.setLayoutX(662);
		greenSpinner.setLayoutY(52);
		blueSpinner.setLayoutX(647);
		blueSpinner.setLayoutY(33);
		blueSpinner.setPrefSize(80, 88);
		Timeline timeline =
				new Timeline(
						new KeyFrame(Duration.ZERO,
								new KeyValue(blueSpinner.progressProperty(), 0),
								new KeyValue(greenSpinner.progressProperty(),
										0)),
						new KeyFrame(Duration.seconds(0.5),
								new KeyValue(greenSpinner.progressProperty(),
										0.5)),
						new KeyFrame(Duration.seconds(2),
								new KeyValue(blueSpinner.progressProperty(), 1),
								new KeyValue(greenSpinner.progressProperty(),
										1)));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
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

	public class IntroView extends Thread {
		@Override
		public void run() {
			try {
				Thread.sleep(5000);
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						try {
							FXMLLoader loader =
									new FXMLLoader(getClass()
											.getResource("View.fxml"));
							Parent parent = loader.load();
							Main.getSceneManager().addScreen("view", parent);
							Main.getControllerManager()
									.addViewController(loader.getController());
							Main.getSceneManager().activate("view");
							Main.getStage().show();

						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
