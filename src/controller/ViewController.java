package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXBadge;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import application.Main;
import controller.tospeech.TextToSpeech;
import database.AutoCompleteTextField;
import database.DatabaseManager;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class ViewController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private WebView searchResult;

	@FXML
	private AnchorPane myAnchor;

	@FXML
	private AnchorPane fakeAnchor;

	@FXML
	private ListView<String> ListWord;

	@FXML
	private Button switchMenu;

	@FXML
	private JFXButton USButton;

	@FXML
	private JFXButton UKButton;

	@FXML
	private JFXButton notiButton;

	@FXML
	private JFXBadge myBadge;

	@FXML
	private StackPane leftDrawerPane;

	@FXML
	JFXDrawersStack drawersStack;

	@FXML
	private JFXHamburger myHamburger;

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

	private void setupBadge(JFXBadge buttonWithBadge,
			StringProperty badgeNumber, BooleanProperty badgeEnabled) {
		buttonWithBadge.textProperty().bind(badgeNumber);
		buttonWithBadge.setEnabled(badgeEnabled.get());
		badgeEnabled.addListener((observable, oldValue, newValue) -> {
			buttonWithBadge.setEnabled(newValue);
			buttonWithBadge.refreshBadge();
		});

		buttonWithBadge.setPosition(Pos.TOP_RIGHT);
		buttonWithBadge.setMinHeight(34);
		buttonWithBadge.setMaxHeight(34);
	}

	@FXML
	public void initialize() {
		textToSpeech = new TextToSpeech();
		ListWord.setItems(items);
		try {
			databaseManager = new DatabaseManager();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JFXRippler rippler = new JFXRippler();
		rippler.setMaskType(JFXRippler.RipplerMask.CIRCLE);
		rippler.setRipplerFill(Paint.valueOf("#ff0000"));
		// rippler.getChildren().add(USButton);

		JFXDrawer leftDrawer = new JFXDrawer();
		leftDrawer.setSidePane(leftDrawerPane);
		// leftDrawer.setDirection(DrawerDirection.LEFT);
		leftDrawer.setDefaultDrawerSize(260);
		leftDrawer.setResizeContent(true);
		leftDrawer.setOverLayVisible(false);
		leftDrawer.setResizableOnDrag(true);
		// fakeAnchor.getChildren().add(textField);
		drawersStack.setContent(fakeAnchor);
		leftDrawer.setId("LEFT");
		HamburgerBackArrowBasicTransition burgerTask =
				new HamburgerBackArrowBasicTransition(myHamburger);
		burgerTask.setRate(-1);
		myHamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
			burgerTask.setRate(burgerTask.getRate() * -1);
			burgerTask.play();
			drawersStack.toggle(leftDrawer);
		});
		myHamburger.getStyleClass().add("jfx-hamburger-icon");
		USButton.getStyleClass().add("my-button");

		List<String> dictWord = databaseManager.getDictWord();
		items.addAll(dictWord);
		textField = databaseManager.getSearchedWord().getTextField();
		textField.setEntries(dictWord);
		// textField.setFocusTraversable(false);
		textField.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent key) {
				if (key.getCode().equals(KeyCode.ENTER)) {
					try {
						// textField.validate();
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
		myAnchor.getChildren().add(rippler);
	}

	@FXML
	void PressedUS(ActionEvent event) {
		if (textField.getText() == null)
			return;
		String word = textField.getText();
		if (word.length() == 0)
			return;

		// female uk accent
		textToSpeech.setVoice("cmu-bdl-hsmm");
		textToSpeech.speak(word, 2.0f, false, true);
	}

	@FXML
	void PressedUK(ActionEvent event) {
		if (textField.getText() == null)
			return;
		String word = textField.getText();
		if (word.length() == 0)
			return;

		// male us accent
		textToSpeech.setVoice("dfki-prudence-hsmm");
		textToSpeech.speak(word, 2.0f, false, true);
	}

	public AutoCompleteTextField getTextField() {
		return textField;
	}
}
