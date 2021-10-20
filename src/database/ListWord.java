package database;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;

@SuppressWarnings({ "rawtypes", "unchecked", "unused" })

// in the view
public class ListWord {
	private String word_;
	private String[] listWord_;
	private int index_;
	private int row_;
	private int colum_;
	private int x_;
	private int y_;
	private int width_;
	private int height_;
	private DefaultListModel<String> model;
	private JList<String> myList_;
	private JScrollPane scroll_;
	private Map<Integer, String> mapData_;

	public ListWord() {
		this.row_ = 500;
		this.colum_ = 200;
		this.x_ = 20;
		this.y_ = 80;
		this.width_ = 250;
		this.height_ = 450;
		model = new DefaultListModel<>();
		myList_ = new JList<>(model);
		scroll_ = new JScrollPane(myList_);
		scroll_.setBounds(x_, y_, width_, height_);
		scroll_.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
		mapData_ = new HashMap<>();
	}

	public int getIndex_() {
		return index_;
	}

	public void setIndex_(int index_) {
		this.index_ = index_;
	}

	public JScrollPane addLW() {
		return scroll_;
	}

	public void putData(Map dict) {
		mapData_.putAll(dict);

	}

	public Map<Integer, String> getMapData() {
		return mapData_;
	}

	public int getCount() {
		return myList_.getSelectedIndex();
	}

	public JList<String> getJList() {
		return myList_;
	}

	public DefaultListModel<String> getModel() {
		return model;
	}
}
