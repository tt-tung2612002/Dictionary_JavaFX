package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.jfoenix.controls.JFXSnackbarLayout;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleNode;
import com.jfoenix.validation.RequiredFieldValidator;

import application.Main;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class EditController implements Initializable {

	@FXML
	private AnchorPane myAnchor;

	@FXML
	private JFXTabPane editTabPane;

	@FXML
	private JFXTextField addWordField, addTypeField, addMeaningField;

	@FXML
	private JFXTextField deleteWordField;

	@FXML
	private JFXTextField changeWordField, changeTypeField, changeMeaningField;

	@FXML
	private JFXButton confirmAddButton, confirmDeleteButton,
			confirmChangeButton;

	@FXML
	void switchToView(ActionEvent event) {
		myAnchor.getScene().getWindow().hide();
		Main.getSceneManager().activate("view");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		JFXToggleNode node = new JFXToggleNode();
		Text text = GlyphsDude.createIcon(FontAwesomeIcon.WARNING);
		text.getStyleClass().add("warning");
		node.setGraphic(text);
		RequiredFieldValidator validator = new RequiredFieldValidator();
		validator.setMessage("Input Required!");
		validator.setIcon(node);
		addWordField.getValidators().add(validator);
		addTypeField.getValidators().add(validator);
		addMeaningField.getValidators().add(validator);
		deleteWordField.getValidators().add(validator);
		changeWordField.getValidators().add(validator);
		changeTypeField.getValidators().add(validator);
		changeMeaningField.getValidators().add(validator);

		addWordField.textProperty()
				.addListener((observable, oldValue, newValue) -> {
					addWordField.validate();
				});

		addTypeField.textProperty()
				.addListener((observable, oldValue, newValue) -> {
					addTypeField.validate();
				});

		addMeaningField.textProperty()
				.addListener((observable, oldValue, newValue) -> {
					addMeaningField.validate();
				});

		deleteWordField.textProperty()
				.addListener((observable, oldValue, newValue) -> {
					deleteWordField.validate();
				});

		changeWordField.textProperty()
				.addListener((observable, oldValue, newValue) -> {
					changeWordField.validate();
				});

		changeTypeField.textProperty()
				.addListener((observable, oldValue, newValue) -> {
					changeTypeField.validate();
				});

		changeMeaningField.textProperty()
				.addListener((observable, oldValue, newValue) -> {
					changeMeaningField.validate();
				});
		confirmAddButton.setOnAction(e -> {
			if (addWordField.getText().length() == 0
					|| addTypeField.getText().length() == 0
					|| addMeaningField.getText().length() == 0) {
				JFXSnackbarLayout content = new JFXSnackbarLayout(
						"Please fill in all fields before adding a word!",
						null, null);
				Main.getControllerManager().getViewController().getSnackbar()
						.enqueue(new SnackbarEvent(content,
								Duration.seconds(1.5)));
				return;
			}
			addWord();
		});

		confirmChangeButton.setOnAction(e -> {
			if (changeWordField.getText().length() == 0
					|| changeTypeField.getText().length() == 0
					|| changeMeaningField.getText().length() == 0) {
				JFXSnackbarLayout content = new JFXSnackbarLayout(
						"Please fill in all fields before changing a word!",
						null, null);
				Main.getControllerManager().getViewController().getSnackbar()
						.enqueue(new SnackbarEvent(content,
								Duration.seconds(1.5)));
				return;
			}
			changeWord();
		});

		confirmDeleteButton.setOnAction(e -> {
			if (deleteWordField.getText().length() == 0) {
				JFXSnackbarLayout content = new JFXSnackbarLayout(
						"Please fill in the field before trying to delete a word!",
						null, null);
				Main.getControllerManager().getViewController().getSnackbar()
						.enqueue(new SnackbarEvent(content,
								Duration.seconds(1.5)));
				return;
			}
			deleteWord();
		});

		addWordField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if (key.getCode().equals(KeyCode.ENTER)) {
					if (addWordField.getText().length() == 0) {
						addWordField.validate();
						JFXSnackbarLayout content = new JFXSnackbarLayout(
								"Please fill in all fields before adding a word!",
								null, null);
						Main.getControllerManager().getViewController()
								.getSnackbar().enqueue(new SnackbarEvent(
										content, Duration.seconds(1.5)));
						return;
					}
					confirmAddButton.fire();
				}
			}
		});
		addTypeField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if (key.getCode().equals(KeyCode.ENTER)) {
					if (addTypeField.getText().length() == 0) {
						addTypeField.validate();
						JFXSnackbarLayout content = new JFXSnackbarLayout(
								"Please fill in all blanks before adding a word!",
								null, null);
						Main.getControllerManager().getViewController()
								.getSnackbar().enqueue(new SnackbarEvent(
										content, Duration.seconds(1.5)));
						return;
					}
					confirmAddButton.fire();
				}

			}
		});
		addMeaningField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if (key.getCode().equals(KeyCode.ENTER)) {
					if (addMeaningField.getText().length() == 0) {
						addMeaningField.validate();
						JFXSnackbarLayout content = new JFXSnackbarLayout(
								"Please fill in all blanks before adding a word!",
								null, null);
						Main.getControllerManager().getViewController()
								.getSnackbar().enqueue(new SnackbarEvent(
										content, Duration.seconds(1.5)));
						return;
					}
					confirmAddButton.fire();
				}

			}
		});
		deleteWordField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if (key.getCode().equals(KeyCode.ENTER)) {
					if (deleteWordField.getText().length() == 0) {
						deleteWordField.validate();
						JFXSnackbarLayout content = new JFXSnackbarLayout(
								"Please fill in the blank before trying to delete a word!",
								null, null);
						Main.getControllerManager().getViewController()
								.getSnackbar().enqueue(new SnackbarEvent(
										content, Duration.seconds(1.5)));
						return;
					}
					confirmDeleteButton.fire();
				}
			}
		});
		changeWordField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if (key.getCode().equals(KeyCode.ENTER)) {
					if (changeWordField.getText().length() == 0) {
						changeWordField.validate();
						JFXSnackbarLayout content = new JFXSnackbarLayout(
								"Please fill in all blanks before changing a word definition!",
								null, null);
						Main.getControllerManager().getViewController()
								.getSnackbar().enqueue(new SnackbarEvent(
										content, Duration.seconds(1.5)));
						return;
					}
					confirmChangeButton.fire();
				}
			}
		});
		changeTypeField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if (key.getCode().equals(KeyCode.ENTER)) {
					if (changeTypeField.getText().length() == 0) {
						changeTypeField.validate();
						JFXSnackbarLayout content = new JFXSnackbarLayout(
								"Please fill in all blanks before changing a word definition!",
								null, null);
						Main.getControllerManager().getViewController()
								.getSnackbar().enqueue(new SnackbarEvent(
										content, Duration.seconds(1.5)));
						return;
					}
					confirmChangeButton.fire();
				}
			}
		});
		changeMeaningField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if (key.getCode().equals(KeyCode.ENTER)) {
					if (changeMeaningField.getText().length() == 0) {
						changeMeaningField.validate();
						JFXSnackbarLayout content = new JFXSnackbarLayout(
								"Please fill in all blanks before changing a word definition!",
								null, null);
						Main.getControllerManager().getViewController()
								.getSnackbar().enqueue(new SnackbarEvent(
										content, Duration.seconds(1.5)));
						return;
					}
					confirmChangeButton.fire();
				}
			}
		});

		editTabPane.getStyleClass().add("jfx-tab-pane");

	}

	public JFXTabPane getEditTabPane() {
		return editTabPane;
	}

	public void addWord() {
		String word = addWordField.getText();
		String type = addTypeField.getText();
		String meaning = addMeaningField.getText();
		int index = Main.getDatabaseManager().addWord(word, type, meaning);

		JFXSnackbarLayout content = null;
		if (index >= 0) {
			content = new JFXSnackbarLayout(
					"Added " + word + " to dictionary <3!", null, null);
			Main.getControllerManager().getViewController().getItemsEV()
					.add(index, word);
		} else {
			content = new JFXSnackbarLayout(
					word + " has already been added to dictionary <3!",
					null, null);
		}
		Main.getControllerManager().getViewController().getSnackbar()
				.enqueue(new SnackbarEvent(content, Duration.seconds(1.5)));
	}

	public void deleteWord() {
		String word = deleteWordField.getText();
		int index = Main.getDatabaseManager().deleteWord(word);

		JFXSnackbarLayout content = null;
		if (index >= 0) {
			content = new JFXSnackbarLayout(
					"Deleted " + word + " from dictionary 💔 !", null,
					null);
			Main.getControllerManager().getViewController().getItemsEV()
					.remove(index);
		} else {
			content = new JFXSnackbarLayout(
					word + " doesn't exist in the dictionary 💔 :(",
					null, null);
		}
		Main.getControllerManager().getViewController().getSnackbar()
				.enqueue(new SnackbarEvent(content, Duration.seconds(1.5)));
	}

	public void changeWord() {
		String word = changeWordField.getText();
		String type = changeTypeField.getText();
		String meaning = changeMeaningField.getText();
		int index = 0;
		index = Main.getDatabaseManager().changeWord(word, type, meaning);

		JFXSnackbarLayout content = null;
		if (index >= 0) {
			content = new JFXSnackbarLayout(
					"Changed " + word
							+ " definition from dictionary <3 !",
					null, null);
		} else {
			content = new JFXSnackbarLayout(
					word + " doesn't exist in the dictionary 💔 :(",
					null, null);
		}
		Main.getControllerManager().getViewController().getSnackbar()
				.enqueue(new SnackbarEvent(content, Duration.seconds(1.5)));
	}

}
