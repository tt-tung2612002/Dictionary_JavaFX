package database;

import javax.swing.JTextField;

import com.jfoenix.controls.JFXToggleNode;
import com.jfoenix.validation.RequiredFieldValidator;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SearchedWord {
	private int x;
	private int y;
	private int width;
	private int height;
	private JTextField searchBox;
	AutoCompleteTextField textField = new AutoCompleteTextField();

	public SearchedWord() {
		this.x = 490;
		this.y = 50;
		this.width = 300;
		this.height = 30;
		textField.setPromptText("Search In English");
		textField.setLabelFloat(true);
		textField.setLayoutX(x);
		textField.setLayoutY(y);
		textField.setPrefSize(width, height);

		JFXToggleNode node = new JFXToggleNode();
		Text text = GlyphsDude.createIcon(FontAwesomeIcon.WARNING);
		text.getStyleClass().add("warning");
		node.setGraphic(text);
		RequiredFieldValidator validator = new RequiredFieldValidator();
		validator.setMessage("Input Required!");
		validator.setIcon(node);
		// validator.getIcon();
		// validator.setAwsomeIcon(
		// new Icon(AwesomeIcon.WARNING, "2em", ";", "error"));

		textField.setFont(Font.font("Fira Code", FontWeight.LIGHT, 18));
		textField.setAlignment(Pos.CENTER_LEFT);
		textField.getValidators().add(validator);
		textField.textProperty()
				.addListener((observable, oldValue, newValue) -> {
					textField.validate();
				});

		// textField.focusedProperty().addListener(new ChangeListener<Boolean>()
		// {
		// @Override
		// public void changed(ObservableValue<? extends Boolean> arg0, Boolean
		// oldPropertyValue,
		// Boolean newPropertyValue) {
		// if (newPropertyValue) {
		// textField.setText(null);
		// } else {
		// textField.setText("Search in English");
		// }
		// }
		// });
	}

	public JTextField addCW() {
		return searchBox;
	}

	public JTextField getSearchBox() {
		return searchBox;
	}

	public AutoCompleteTextField getTextField() {
		return textField;
	}
}