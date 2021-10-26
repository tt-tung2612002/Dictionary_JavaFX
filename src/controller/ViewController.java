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
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.jfoenix.controls.JFXSnackbarLayout;
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
import javafx.scene.control.ListView;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;

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
	private AnchorPane favAnchor;

	@FXML
	private ListView<String> ListWord;

	@FXML
	private JFXButton USButton;

	@FXML
	private JFXButton UKButton;

	@FXML
	private JFXButton searchPlusButton;

	@FXML
	private JFXButton notiButton;

	@FXML
	private JFXButton infoButton;

	@FXML
	private JFXButton favButton;

	@FXML
	private JFXButton settingsButton;

	@FXML
	private JFXBadge myBadge;

	@FXML
	private StackPane leftDrawerPane;

	@FXML
	JFXDrawersStack drawersStack;

	@FXML
	private JFXHamburger myHamburger;

	@FXML
	private StackPane myStackPane;

	@FXML
	private JFXSnackbar snackbar;

	@FXML
	private Pane favPane;

	private ObservableList<String> items = FXCollections.observableArrayList();

	private DatabaseManager databaseManager;

	private TextToSpeech textToSpeech;

	private AutoCompleteTextField textField;

	private int prev = 0;

	private boolean flag = false;

	public void switchToMenu(ActionEvent event) throws IOException {
		Main.getSceneManager().activate("menu");
		Main.getStage().show();
		Main.getControllerManager().getMenuController().getTextField()
				.setText("hello");
	}

	@SuppressWarnings("unused")
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
		JFXRippler rippler = new JFXRippler(favPane);
		rippler.setMaskType(JFXRippler.RipplerMask.CIRCLE);
		rippler.setRipplerFill(Paint.valueOf("#ff0000"));
		rippler.getStyleClass().add("icons-rippler");

		snackbar = new JFXSnackbar(myAnchor);
		snackbar.setPrefWidth(600);
		myBadge.setOnMouseClicked((click) -> {
			int value = Integer.parseInt(myBadge.getText());
			if (click.getButton() == MouseButton.PRIMARY) {
				value++;
			} else if (click.getButton() == MouseButton.SECONDARY) {
				value--;
			}

			if (value == 0) {
				myBadge.setEnabled(false);
			} else {
				myBadge.setEnabled(true);
			}
			String word = textField.getText();
			if (word.length() == 0)
				return;
			if (value > prev) {
				// add word to favourite if exists.
				myBadge.setText(String.valueOf(value));
				JFXButton button = new JFXButton("CLOSE");
				button.setOnAction(action -> {
					if (snackbar.getCurrentEvent() != null)
						snackbar.close();
				});
				final JFXSnackbarLayout content =
						new JFXSnackbarLayout(
								"Added " + word + " to favourite <3", "CLOSE",
								action -> {
									snackbar.close();
								});
				snackbar.enqueue(
						new SnackbarEvent(content, Duration.seconds(2)));
			} else {
				// delete word from favourite if exists.
				myBadge.setText(String.valueOf(value));
				JFXButton button = new JFXButton("CLOSE");
				button.setOnAction(action -> {
					if (snackbar.getCurrentEvent() != null)
						snackbar.close();
				});
				snackbar.fireEvent(new SnackbarEvent(new JFXSnackbarLayout(
						"Deleted " + word + " from favourite :( ", "CLOSE",
						action -> {
							if (snackbar.getCurrentEvent() != null)
								snackbar.close();
						}), Duration.seconds(2), null));
			}
			prev = value;
		});

		JFXDrawer leftDrawer = new JFXDrawer();
		leftDrawer.setSidePane(leftDrawerPane);
		// leftDrawer.setDirection(DrawerDirection.LEFT);
		leftDrawer.setDefaultDrawerSize(268);
		leftDrawer.setResizeContent(true);
		leftDrawer.setOverLayVisible(false);
		leftDrawer.setResizableOnDrag(true);
		// fakeAnchor.getChildren().add(textField);
		drawersStack.setContent(fakeAnchor);
		leftDrawer.setId("LEFT");

		BoxBlur boxBlur = new BoxBlur();
		HamburgerBackArrowBasicTransition burgerTask =
				new HamburgerBackArrowBasicTransition(myHamburger);
		burgerTask.setRate(-1);
		myHamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
			burgerTask.setRate(burgerTask.getRate() * -1);
			burgerTask.play();
			drawersStack.toggle(leftDrawer);
			if (!flag) {
				textField.setEffect(boxBlur);
				ListWord.setEffect(boxBlur);
				searchResult.setEffect(boxBlur);
				myBadge.setEffect(boxBlur);
				USButton.setEffect(boxBlur);
				UKButton.setEffect(boxBlur);
			} else {
				textField.setEffect(null);
				ListWord.setEffect(null);
				searchResult.setEffect(null);
				myBadge.setEffect(null);
				USButton.setEffect(null);
				UKButton.setEffect(null);
			}
			flag = !flag;
		});

		myHamburger.getStyleClass().add("jfx-hamburger-icon");
		myBadge.getStyleClass().add("fav");
		USButton.getStyleClass().add("my-button");
		UKButton.getStyleClass().add("my-button");
		searchPlusButton.getStyleClass().add("my-button");
		notiButton.getStyleClass().add("my-button");
		infoButton.getStyleClass().add("my-button");
		favButton.getStyleClass().add("my-button");
		settingsButton.getStyleClass().add("my-button");
		myAnchor.getStyleClass().add("bodybg");

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
		favAnchor.getChildren().add(rippler);
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
