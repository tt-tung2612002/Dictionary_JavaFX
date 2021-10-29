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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class IntroController implements Initializable {

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
	private JFXSpinner spinner1;

	@FXML
	private JFXSpinner spinner2;

	@FXML
	private JFXSpinner spinner3;

	@FXML
	private JFXSpinner spinner4;

	@FXML
	private JFXSpinner spinner5;

	@FXML
	private JFXSpinner spinner6;

	@FXML
	private JFXSpinner spinner7;

	@FXML
	private JFXSpinner spinner8;

	@FXML
	private JFXSpinner spinner9;

	@FXML
	private JFXSpinner spinner10;

	@FXML
	private JFXSpinner percentageDisplay;

	@FXML
	public void switchToView(ActionEvent event) throws IOException {
		Main.getSceneManager().getStage("menu").hide();
		Main.getSceneManager().activate("view");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Stage viewStage = new Stage(StageStyle.DECORATED);
		Parent root = null;

		FXMLLoader loader =
				new FXMLLoader(
						getClass().getResource("/application/View.fxml"));
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Scene scene = new Scene(root);
		scene.getStylesheets()
				.addAll(getClass().getResource("/Home.css").toExternalForm());
		viewStage.setTitle("Dictionary");
		viewStage.setScene(scene);
		// FXMLLoader has to be loaded for controller to be
		// initialized.
		ViewController viewController = loader.getController();
		Main.getControllerManager().addViewController(viewController);
		Main.getSceneManager().addStage("view", viewStage);
		viewStage.getIcons().add(new Image("dictionary.png"));
		spinner1.getStyleClass().add("materialDesign-green");
		spinner2.getStyleClass().add("materialDesign-yellow");
		spinner3.getStyleClass().add("materialDesign-orange");
		spinner4.getStyleClass().add("materialDesign-orange");
		spinner5.getStyleClass().add("materialDesign-red");
		spinner6.getStyleClass().add("materialDesign-cyan");
		spinner7.getStyleClass().add("materialDesign-blue");
		spinner8.getStyleClass().add("materialDesign-purple");
		spinner9.getStyleClass().add("materialDesign-brown");
		spinner10.getStyleClass().add("materialDesign-pink");
		percentageDisplay.getStyleClass().add("percentage");
		myAnchor.getStyleClass().add("intro");
		// myAnchor.setFill(Color.TRANSPARENT);
		Timeline timeline =
				new Timeline(
						new KeyFrame(Duration.ZERO, new KeyValue(
								percentageDisplay.progressProperty(), 0)),
						new KeyFrame(Duration.seconds(0.5), new KeyValue(
								percentageDisplay.progressProperty(), 0.1)),
						new KeyFrame(Duration.seconds(1), new KeyValue(
								percentageDisplay.progressProperty(), 0.2)),
						new KeyFrame(Duration.seconds(1.5), new KeyValue(
								percentageDisplay.progressProperty(), 0.3)),
						new KeyFrame(Duration.seconds(2), new KeyValue(
								percentageDisplay.progressProperty(), 0.4)),
						new KeyFrame(Duration.seconds(2.5), new KeyValue(
								percentageDisplay.progressProperty(), 0.5)),
						new KeyFrame(Duration.seconds(3), new KeyValue(
								percentageDisplay.progressProperty(), 0.6)),
						new KeyFrame(Duration.seconds(3.5), new KeyValue(
								percentageDisplay.progressProperty(), 0.7)),
						new KeyFrame(Duration.seconds(4), new KeyValue(
								percentageDisplay.progressProperty(), 0.8)),
						new KeyFrame(Duration.seconds(4.5), new KeyValue(
								percentageDisplay.progressProperty(), 0.9)),
						new KeyFrame(Duration.seconds(5), new KeyValue(
								percentageDisplay.progressProperty(), 1)));
		timeline.setCycleCount(1);
		timeline.play();
		new IntroView().start();
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
						Main.getSceneManager().activate("view");
						myAnchor.getScene().getWindow().hide();

					}
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
