package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import database.AutoCompleteTextField;
import database.DatabaseManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

	private ObservableList<String> items = FXCollections.observableArrayList();

	private DatabaseManager databaseManager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ListWord.setItems(items);
		try {
			databaseManager = new DatabaseManager();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<String> dictWord = databaseManager.getDictWord();
		items.addAll(dictWord);
		AutoCompleteTextField textField = databaseManager.getSearchedWord().getTextField();
		textField.setEntries(dictWord);
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
}
