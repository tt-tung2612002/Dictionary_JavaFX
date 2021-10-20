package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

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

public class SearchResultController implements Initializable {

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
	private Button USButton;
	@FXML
	private Button UKButton;

	private ObservableList<String> items = FXCollections.observableArrayList();

	private DatabaseManager databaseManager;

	private TextToSpeech tts;

	private AutoCompleteTextField textField;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tts = new TextToSpeech();
//		tts.getAvailableVoices().stream().forEach(voice -> System.out.println("Voice: " + voice));
		// cmu-bdl-hsmm, dfki-spike-hsmm

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
						String searched = databaseManager.getFormattedResult(textField.getText());
						webEngine.loadContent(searched);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});
		ListWord.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String searched = ListWord.getSelectionModel().getSelectedItem();
				WebEngine webEngine = searchResult.getEngine();
				try {
					webEngine.loadContent(databaseManager.getFormattedResult(searched));
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
		String word = textField.getText();

		// female uk accent
		tts.setVoice("cmu-bdl-hsmm");
		tts.speak(word, 2.0f, false, true);
		tts.stopSpeaking();
	}

	@FXML
	void PressedUK(ActionEvent event) {
		String word = textField.getText();

		// male us accent
		tts.setVoice("dfki-prudence-hsmm");
		tts.speak(word, 2.0f, false, true);
		tts.stopSpeaking();
	}
}
