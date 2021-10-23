package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import controller.tospeech.TextToSpeech;
import database.AutoCompleteTextField;
import database.DatabaseManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class ViewController implements Initializable {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private WebView searchResult;

	@FXML
	private AnchorPane myAnchor;

	@FXML
	private ListView<String> ListWord;

	@FXML
	private Button switchMenu;

	@FXML
	private Button USButton;
	@FXML
	private Button UKButton;

	private ObservableList<String> items = FXCollections.observableArrayList();

	private DatabaseManager databaseManager;

	private TextToSpeech textToSpeech;

	private AutoCompleteTextField textField;

	public void switchToMenu(ActionEvent event) throws IOException {
		Main.getSceneManager().activate("menu");
		Main.getStage().show();
		Main.getControllerManager().getMenuController().getTextField()
				.setText("hello");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// var menuController = (MenuController) FXMLLoader.getController();
		textToSpeech = new TextToSpeech();
		ListWord.setItems(items);
		try {
			databaseManager = new DatabaseManager();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		List<String> dictWord = databaseManager.getDictWord();
		items.addAll(dictWord);
		textField = databaseManager.getSearchedWord().getTextField();
		textField.setEntries(dictWord);
		textField.setFocusTraversable(false);
		textField.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent key) {
				if (key.getCode().equals(KeyCode.ENTER)) {
					try {
						WebEngine webEngine = searchResult.getEngine();
						String searched =
								databaseManager.getFormattedResult(
										textField.getText());
						if (searched == null)
							return;
						// webEngine.setUserStyleSheetLocation(
						// getClass().getResource("/Home.css").toString());
						// webEngine.setUserStyleSheetLocation(getClass().getResource("/nicepage.css").toString());
						// webEngine.load(getClass().getResource("/new.html").toExternalForm());
						webEngine.loadContent(searched);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});
		ListWord.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<String>() {

					@Override
					public void changed(
							ObservableValue<? extends String> observable,
							String oldValue, String newValue) {
						String searched =
								ListWord.getSelectionModel().getSelectedItem();
						textField.setText(searched);
						WebEngine webEngine = searchResult.getEngine();
						try {
							webEngine.loadContent(databaseManager
									.getFormattedResult(searched));
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}

				});
		ListWord.setFixedCellSize(40);
		myAnchor.getChildren().add(textField);
	}

	@FXML
	void PressedUS(ActionEvent event) {
		if (textField.getText() == null)
			return;
		String word = textField.getText();

		// female uk accent
		textToSpeech.setVoice("cmu-bdl-hsmm");
		textToSpeech.speak(word, 2.0f, false, true);
	}

	@FXML
	void PressedUK(ActionEvent event) {
		if (textField.getText() == null)
			return;
		String word = textField.getText();

		// male us accent
		textToSpeech.setVoice("dfki-prudence-hsmm");
		textToSpeech.speak(word, 2.0f, false, true);
	}

	public AutoCompleteTextField getTextField() {
		return textField;
	}
}
