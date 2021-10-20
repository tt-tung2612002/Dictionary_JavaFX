package database;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

public class SearchedResult {
	private int x_;
	private int y_;
	private int width_;
	private int height_;
	private Border bd_;
	private JEditorPane txtWord_;
	private JPanel panelBd_;
	private JScrollPane scp_;
	private StyleSheet styleSheet;
	private HTMLEditorKit htmlEditorKit;

	public SearchedResult() {
		this.x_ = 280;
		this.y_ = 80;
		this.width_ = 480;
		this.height_ = 450;
		bd_ = BorderFactory.createLoweredBevelBorder();
		styleSheet = new StyleSheet();
		htmlEditorKit = new HTMLEditorKit();
		styleSheet.addRule("*{box-sizing: border-box;}");

		styleSheet.addRule("body {margin:10 px 30 px;color:#1d2a57;}");

		styleSheet.addRule(".head-item {margin-top:30 px;margin-bottom:0 px;font-size:20 px;}");

		styleSheet.addRule(".head {font-size:1.5 rem;}");

		styleSheet.addRule(".container-icon {border-bottom:1px solid black;padding:10 px 0;}");

		styleSheet.addRule(
				".icon {;display:flex;flex-direction:row;justify-content:flex-end;height:30 px;width:100 vw;padding-right:60 px;}");

		styleSheet.addRule(".icon-images {height:40 px;}");

		styleSheet.addRule(".content {padding:0 px 15 px;border-bottom:2px solid#fdc702;}");

		styleSheet.addRule("li {font-size:13 px;}");
		htmlEditorKit.setStyleSheet(styleSheet);
		txtWord_ = new JEditorPane();
		txtWord_.setContentType("text/html");
		txtWord_.setEditable(false);
		txtWord_.setEditorKit(htmlEditorKit);
		panelBd_ = new JPanel(new BorderLayout());
		panelBd_.setBorder(bd_);
		scp_ = new JScrollPane(txtWord_);
		addRW();
	}

	public JPanel addRW() {
		panelBd_.add(scp_);
		panelBd_.setBounds(x_, y_, width_, height_);
		return panelBd_;
	}

	public JEditorPane getJPane() {
		return txtWord_;
	}
}
