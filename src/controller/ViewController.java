package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXBadge;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawer.DrawerDirection;
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
	private AnchorPane myAnchor, fakeAnchor, favAnchor, addAnchor, changeAnchor,
			deleteAnchor;

	@FXML
	private JFXListView<String> listWord;

	@FXML
	private JFXButton USButton, UKButton;

	@FXML
	private JFXButton searchPlusButton, notiButton, helpButton, favButton,
			editButton, settingsButton, helpClose;

	@FXML
	private JFXButton addButton, changeButton, deleteButton;

	@FXML
	private JFXButton nodeButton, EEButton, EVButton, APIButton;

	@FXML
	private JFXBadge myBadge;

	@FXML
	private StackPane leftDrawerPane, myStackPane;

	@FXML
	private JFXDrawersStack drawersStack;

	@FXML
	private JFXHamburger myHamburger;

	@FXML
	private ScrollPane listScrollPane;

	@FXML
	private JFXSnackbar snackbar;

	@FXML
	private Pane favPane, addPane, changePane, deletePane;

	@FXML
	private JFXTimePicker timePicker;

	private ObservableList<String> itemsEE =
			FXCollections.observableArrayList();
	private ObservableList<String> itemsEV =
			FXCollections.observableArrayList();
	private ObservableList<String> itemsFavorite =
			FXCollections.observableArrayList();

	private DatabaseManager databaseManager;

	private TextToSpeech textToSpeech;

	private AutoCompleteTextField textField;

	private JFXPopup popUpAddButton, popUpDeleteButton, popUpChangeButton;

	private JFXRippler ripplerAdd, ripplerDelete, ripplerChange;

	private List<String> dictWordEE, dictWordEV, favorites;

	private boolean flag = false;

	private boolean editFlag = false;

	ChangeListener<String> listListener = new ChangeListener<String>() {

		@Override
		public void changed(ObservableValue<? extends String> observable,
				String oldValue, String newValue) {
			String searched = listWord.getSelectionModel().getSelectedItem();
			if (searched == null) {
				return;
			}
			textField.setText(searched);

			WebEngine webEngine = searchResult.getEngine();
			try {
				webEngine.loadContent(databaseManager.getFormattedResult(
						searched, databaseManager.getDictType()));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	};

	@FXML
	void changeToEV(ActionEvent event) {
		databaseManager.changeDictionary(DatabaseManager.DictionaryType.ENG_VI);
		listWord.setItems(itemsEV);
		listWord.refresh();
	}

	@FXML
	void changeToAPI(ActionEvent event) {
		databaseManager
				.changeDictionary(DatabaseManager.DictionaryType.ENG_VI_API);
	}

	@FXML
	void changeToEE(ActionEvent event) {
		databaseManager
				.changeDictionary(DatabaseManager.DictionaryType.ENG_ENG);
		listWord.setItems(itemsEE);
		listWord.refresh();
	}

	@FXML
	void changeToFavorite(ActionEvent event) {
		listWord.setItems(itemsFavorite);
		listWord.refresh();
	}

	@FXML
	public void initialize() {
		textToSpeech = new TextToSpeech();
		try {
			Main.initializeDatabaseManager();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		databaseManager = Main.getDatabaseManager();

		// data from available dictionaries to ListView.
		favorites = databaseManager.getFavourite();
		dictWordEE = databaseManager.getDictWordEE();
		dictWordEV = databaseManager.getDictionaryData().getWordTarget();
		itemsEE.addAll(dictWordEE);
		itemsEV.addAll(dictWordEV);
		itemsFavorite.addAll(favorites);
		listWord.setItems(itemsEE);
		myBadge.setText(String.valueOf(favorites.size()));

		// add animation for favorite icon.
		JFXRippler ripplerFav = new JFXRippler(favPane);
		ripplerFav.setMaskType(JFXRippler.RipplerMask.CIRCLE);
		ripplerFav.setRipplerFill(Paint.valueOf("#ff0000"));
		ripplerFav.getStyleClass().add("icons-rippler");

		// Add PopUp for AddButton.
		ripplerAdd = new JFXRippler(addPane);
		ripplerAdd.setRipplerRadius(60);
		ripplerAdd.setMaskType(JFXRippler.RipplerMask.CIRCLE);
		ripplerAdd.setRipplerFill(Paint.valueOf("#ff0000"));
		addPane.setVisible(false);
		addAnchor.setVisible(false);
		addButton.setVisible(false);
		popUpAddButton = new JFXPopup(addButton);
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
		addButton.setOnMouseClicked(e -> {
			Main.getSceneManager().activate("edit");
			Main.getControllerManager().getEditController().getEditTabPane()
					.getSelectionModel().select(0);
		});
		changeButton.setOnMouseClicked(e -> {
			Main.getSceneManager().activate("edit");
			Main.getControllerManager().getEditController().getEditTabPane()
					.getSelectionModel().select(1);
		});
		deleteButton.setOnMouseClicked(e -> {
			Main.getSceneManager().activate("edit");
			Main.getControllerManager().getEditController().getEditTabPane()
					.getSelectionModel().select(2);
		});
		helpButton.setOnMouseClicked(e -> {
			Main.getSceneManager().activate("help");
		});

		snackbar = new JFXSnackbar(myAnchor);
		snackbar.setPrefWidth(600);

		myBadge.setOnMouseClicked(badgeEvent);

		// animation for menuBar to pop up from left side.
		JFXDrawer leftDrawer = new JFXDrawer();
		leftDrawer.setSidePane(leftDrawerPane);
		leftDrawer.setDirection(DrawerDirection.LEFT);
		leftDrawer.setDefaultDrawerSize(268);
		leftDrawer.setResizeContent(true);
		leftDrawer.setOverLayVisible(false);
		leftDrawer.setResizableOnDrag(true);
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
				timePicker.setEffect(boxBlur);
			} else {
				listScrollPane.toFront();
				textField.setEffect(null);
				listWord.setEffect(null);
				searchResult.setEffect(null);
				myBadge.setEffect(null);
				USButton.setEffect(null);
				UKButton.setEffect(null);
				timePicker.setEffect(null);
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
		helpButton.getStyleClass().add("my-button");
		favButton.getStyleClass().add("my-button");
		settingsButton.getStyleClass().add("my-button");
		editButton.getStyleClass().add("my-button");
		nodeButton.getStyleClass().add("node-button");
		EVButton.getStyleClass().add("subnode-button");
		EEButton.getStyleClass().add("subnode-button");
		APIButton.getStyleClass().add("subnode-button");
		listWord.getStyleClass().add("custom-jfx-list-view");
		timePicker.getStyleClass().add("jfx-time-picker");
		myAnchor.getStyleClass().add("bodybg");

		textField = databaseManager.getSearchedWord().getTextField();
		textField.setEntries(dictWordEE);
		textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if (key.getCode().equals(KeyCode.ENTER)) {
					try {
						WebEngine webEngine = searchResult.getEngine();
						String searched =
								databaseManager.getFormattedResult(
										textField.getText(),
										databaseManager.getDictType());
						if (textField.getText().length() == 0) {
							textField.validate();
							JFXSnackbarLayout content =
									new JFXSnackbarLayout(
											"Please type a word in English!",
											null, null);
							snackbar.enqueue(new SnackbarEvent(content,
									Duration.seconds(1.5)));
							return;
						}
						if (searched == null) {
							JFXSnackbarLayout content =
									new JFXSnackbarLayout(
											"No match found for \""
													+ textField.getText()
													+ "\" in English",
											null, null);
							snackbar.enqueue(new SnackbarEvent(content,
									Duration.seconds(1.5)));
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
		if (textField.getText() == null) {
			return;
		}
		String word = textField.getText();
		if (word.length() == 0) {
			return;
		}

		// male us accent
		textToSpeech.setVoice("dfki-prudence-hsmm");
		textToSpeech.speak(word, 2.0f, false, true);
	}

	@FXML
	public void switchToMenu(ActionEvent event) throws IOException {
		Main.getSceneManager().activate("intro");
	}

	EventHandler<MouseEvent> badgeEvent = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent myClick) {
			int value = Integer.parseInt(myBadge.getText());
			String word = textField.getText();
			if (word.length() == 0) {
				return;
			}
			if (myClick.getButton() == MouseButton.PRIMARY) {
				// add word to favorite if exists.
				int index = databaseManager.addFavourite(word);
				JFXSnackbarLayout content = null;
				if (index >= 0) {
					value++;
					content =
							new JFXSnackbarLayout(
									"Added " + word + " to favourite <3!", null,
									null);
					itemsFavorite.add(index, word);
				} else {
					content =
							new JFXSnackbarLayout(word
									+ " has already been added to favourite <3!",
									null, null);
				}
				myBadge.setText(String.valueOf(value));
				snackbar.enqueue(
						new SnackbarEvent(content, Duration.seconds(1.5)));
			} else if (myClick.getButton() == MouseButton.SECONDARY) {
				// delete word from favorite if exists.
				if (itemsFavorite.size() == 0) {
					JFXSnackbarLayout content =
							new JFXSnackbarLayout(
									"There is no word in favourite yet 💔 :(",
									null, null);
					snackbar.enqueue(
							new SnackbarEvent(content, Duration.seconds(1.5)));
					return;
				}
				int index = databaseManager.removeFavourite(word);
				JFXSnackbarLayout content = null;
				if (index >= 0) {
					value--;
					content =
							new JFXSnackbarLayout(
									"Deleted " + word + " from favourite 💔 :(",
									null, null);
					itemsFavorite.remove(index);
				} else {
					System.out.println(itemsFavorite);
					content =
							new JFXSnackbarLayout(
									"Can't find " + word
											+ " in favourite 💔 :(",
									null, null);
				}
				myBadge.setText(String.valueOf(value));
				snackbar.enqueue(
						new SnackbarEvent(content, Duration.seconds(1.5)));
			}
		}
	};
	EventHandler<MouseEvent> editEvent = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent e) {
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
		}
	};

	public JFXSnackbar getSnackbar() {
		return snackbar;
	}

	public AutoCompleteTextField getTextField() {
		return textField;
	}

	public JFXListView<String> getListWord() {
		return listWord;
	}

	public ObservableList<String> getItemsEV() {
		return itemsEV;
	}
}
