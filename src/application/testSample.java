package application;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class testSample extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		// Parent root = FXMLLoader.load(getClass().getResource("test.fxml"));
		//
		// Scene scene = new Scene(root);
		//
		// stage.setScene(scene);
		// stage.show();
		final VBox pane = new VBox();
		pane.setSpacing(30);
		pane.setStyle("-fx-background-color:WHITE;-fx-padding:40;");

		pane.getChildren().add(new TextField());

		JFXTextField field = new JFXTextField();
		field.setLabelFloat(true);
		field.setPromptText("Type Something");
		pane.getChildren().add(field);

		JFXTextField disabledField = new JFXTextField();
		disabledField.setLabelFloat(true);
		disabledField.setPromptText("I'm disabled..");
		disabledField.setDisable(true);
		pane.getChildren().add(disabledField);

		JFXTextField validationField = new JFXTextField();

		validationField.setPromptText("With Validation..");
		RequiredFieldValidator validator = new RequiredFieldValidator();
		validator.setMessage("Input Required");

		// validator.setIcon(warnIcon);
		validationField.getValidators().add(validator);
		validationField.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				validationField.validate();
			}
		});
		pane.getChildren().add(validationField);

		JFXPasswordField passwordField = new JFXPasswordField();
		passwordField.setLabelFloat(true);
		passwordField.setPromptText("Password");
		validator = new RequiredFieldValidator();
		validator.setMessage("Password Can't be empty");

		// validator.setIcon(warnIcon);
		passwordField.getValidators().add(validator);
		passwordField.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				passwordField.validate();
			}
		});
		pane.getChildren().add(passwordField);

		final Scene scene = new Scene(pane, 600, 400, Color.WHITE);
		scene.getStylesheets().add(getClass()
				.getResource("/jfoenix-components.css").toExternalForm());
		stage.setTitle("JFX TextField Demo ");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
