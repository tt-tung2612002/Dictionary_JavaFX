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
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXPopup.PopupHPosition;
import com.jfoenix.controls.JFXPopup.PopupVPosition;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.jfoenix.controls.JFXSnackbarLayout;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import application.Main;
import controller.tospeech.TextToSpeech;
import database.AutoCompleteTextField;
import database.DatabaseManager;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
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
	private AnchorPane addAnchor;

	@FXML
	private AnchorPane changeAnchor;

	@FXML
	private AnchorPane deleteAnchor;

	@FXML
	private JFXListView<String> listWord;

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
	private JFXButton editButton;

	@FXML
	private JFXButton addButton;

	@FXML
	private JFXButton changeButton;

	@FXML
	private JFXButton deleteButton;

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
	private ScrollPane listScrollPane;

	@FXML
	private JFXSnackbar snackbar;

	@FXML
	private Pane favPane;

	@FXML
	private Pane addPane;

	@FXML
	private Pane changePane;

	@FXML
	private Pane deletePane;

	@FXML
	private JFXTimePicker timePicker;

	private ObservableList<String> items = FXCollections.observableArrayList();

	private DatabaseManager databaseManager;

	private TextToSpeech textToSpeech;

	private AutoCompleteTextField textField;

	private int prev = 0;
	private boolean flag = false;
	private boolean editFlag = false;
	private FadeTransition fadeIn = new FadeTransition(Duration.seconds(5));
	ChangeListener<String> listListener = new ChangeListener<String>() {

		@Override
		public void changed(ObservableValue<? extends String> observable,
				String oldValue, String newValue) {
			String searched = listWord.getSelectionModel().getSelectedItem();
			textField.setText(searched);
			WebEngine webEngine = searchResult.getEngine();
			try {
				webEngine.loadContent(
						databaseManager.getFormattedResult(searched));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	};

	@FXML
	public void initialize() {
		// timePicker.setShowing(true);
		textToSpeech = new TextToSpeech();
		listWord.setItems(items);
		try {
			databaseManager = new DatabaseManager();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		JFXRippler ripplerFav = new JFXRippler(favPane);
		ripplerFav.setMaskType(JFXRippler.RipplerMask.CIRCLE);
		ripplerFav.setRipplerFill(Paint.valueOf("#ff0000"));
		ripplerFav.getStyleClass().add("icons-rippler");

		// Add PopUp for AddButton.
		JFXRippler ripplerAdd = new JFXRippler(addPane);
		ripplerAdd.setRipplerRadius(60);
		ripplerAdd.setMaskType(JFXRippler.RipplerMask.CIRCLE);
		ripplerAdd.setRipplerFill(Paint.valueOf("#ff0000"));
		addPane.setVisible(false);
		addAnchor.setVisible(false);
		addButton.setVisible(false);
		JFXPopup popUpAddButton = new JFXPopup(addButton);
		popUpAddButton.getStyleClass().add("jfx-popup-container");

		// Add PopUp for ChangeButton.
		JFXRippler ripplerChange = new JFXRippler(changePane);
		ripplerChange.setRipplerRadius(60);
		ripplerChange.setMaskType(JFXRippler.RipplerMask.CIRCLE);
		ripplerChange.setRipplerFill(Paint.valueOf("#ff0000"));
		changePane.setVisible(false);
		changeAnchor.setVisible(false);
		changeButton.setVisible(false);
		JFXPopup popUpChangeButton = new JFXPopup(changeButton);
		popUpChangeButton.getStyleClass().add("jfx-popup-container");

		// Add PopUp for DeleteButton.
		JFXRippler ripplerDelete = new JFXRippler(deletePane);
		ripplerDelete.setRipplerRadius(30);
		ripplerDelete.setMaskType(JFXRippler.RipplerMask.CIRCLE);
		ripplerDelete.setRipplerFill(Paint.valueOf("#ff0000"));
		deletePane.setVisible(false);
		deleteAnchor.setVisible(false);
		deleteButton.setVisible(false);
		JFXPopup popUpDeleteButton = new JFXPopup(deleteButton);
		popUpDeleteButton.getStyleClass().add("jfx-popup-container");

		editButton.setOnMouseClicked(e -> {
			if (!editFlag) {
				popUpChangeButton.show(ripplerChange, PopupVPosition.TOP,
						PopupHPosition.LEFT);
				popUpAddButton.show(ripplerAdd, PopupVPosition.BOTTOM,
						PopupHPosition.LEFT);
				popUpDeleteButton.show(ripplerDelete, PopupVPosition.TOP,
						PopupHPosition.LEFT);
				addButton.setVisible(true);
				deleteButton.setVisible(true);
				changeButton.setVisible(true);
			} else {
				popUpChangeButton.hide();
				popUpAddButton.hide();
				popUpDeleteButton.hide();

			}
			editFlag = !editFlag;
		});

		snackbar = new JFXSnackbar(myAnchor);
		snackbar.setPrefWidth(600);

		myBadge.setOnMouseClicked(badgeEvent);

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
				listScrollPane.toBack();
				textField.setEffect(boxBlur);
				listWord.setEffect(boxBlur);
				searchResult.setEffect(boxBlur);
				myBadge.setEffect(boxBlur);
				USButton.setEffect(boxBlur);
				UKButton.setEffect(boxBlur);
			} else {
				listScrollPane.toFront();
				textField.setEffect(null);
				listWord.setEffect(null);
				searchResult.setEffect(null);
				myBadge.setEffect(null);
				USButton.setEffect(null);
				UKButton.setEffect(null);
			}
			flag = !flag;
		});

		myHamburger.getStyleClass().add("jfx-hamburger-icon");
		myBadge.getStyleClass().add("fav");
		addButton.getStyleClass().add("editButton");
		deleteButton.getStyleClass().add("editButton");
		changeButton.getStyleClass().add("editButton");
		USButton.getStyleClass().add("sound-button");
		UKButton.getStyleClass().add("sound-button");
		searchPlusButton.getStyleClass().add("my-button");
		notiButton.getStyleClass().add("my-button");
		infoButton.getStyleClass().add("my-button");
		favButton.getStyleClass().add("my-button");
		settingsButton.getStyleClass().add("my-button");
		editButton.getStyleClass().add("my-button");
		myAnchor.getStyleClass().add("bodybg");
		listWord.getStyleClass().add("custom-jfx-list-view");
		List<String> dictWord = databaseManager.getDictWord();
		items.addAll(dictWord);
		textField = databaseManager.getSearchedWord().getTextField();
		textField.setEntries(dictWord);
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
						if (searched == null) {
							return;
						}
						webEngine.loadContent(searched);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});
		listWord.getSelectionModel().selectedItemProperty()
				.addListener(listListener);
		listWord.setFixedCellSize(40);
		myAnchor.getChildren().add(textField);
		favAnchor.getChildren().add(ripplerFav);
		addAnchor.getChildren().add(ripplerAdd);
		deleteAnchor.getChildren().add(ripplerDelete);
		changeAnchor.getChildren().add(ripplerChange);
	}

	@FXML
	void PressedUS(ActionEvent event) {
		if (textField.getText() == null) {
			return;
		}
		String word = textField.getText();
		if (word.length() == 0) {
			return;
		}

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

	@FXML
	public void switchToMenu(ActionEvent event) throws IOException {
		Main.getSceneManager().activate("menu");
	}

	EventHandler<MouseEvent> badgeEvent = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent myClick) {
			int value = Integer.parseInt(myBadge.getText());
			if (myClick.getButton() == MouseButton.PRIMARY) {
				value++;
			} else if (myClick.getButton() == MouseButton.SECONDARY) {
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
		}
	};

	public AutoCompleteTextField getTextField() {
		return textField;
	}
}
