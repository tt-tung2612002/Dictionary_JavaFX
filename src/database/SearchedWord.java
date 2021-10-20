package database;

import javax.swing.JTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SearchedWord {
	private int x_;
	private int y_;
	private int width_;
	private int height_;
	private JTextField searchBox;
	AutoCompleteTextField textField = new AutoCompleteTextField();

	public SearchedWord() {
		this.x_ = 150;
		this.y_ = 35;
		this.width_ = 500;
		this.height_ = 25;
		searchBox = new JTextField("Search in English");
		searchBox.setBounds(x_, y_, width_, height_);
		textField.setText("Search In English");
		textField.setLayoutX(150);
		textField.setLayoutY(35);
		textField.setPrefSize(500, 30);
		textField.setFont(Font.font("Fira Code", FontWeight.MEDIUM, 14));
		textField.setAlignment(Pos.CENTER_LEFT);
		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue) {
					textField.setText(null);
				} else {
					textField.setText("Search in English");
				}
			}
		});
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