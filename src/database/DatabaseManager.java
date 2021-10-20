package database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.filedatabase.DictionaryData;
import database.sqlserver.server;

public class DatabaseManager {
	private server server;
	private server server2;
	private server server3;
	private SearchedWord searchedWord;
	private ListWord listWords;
	private SearchedResult searchedResult;
	private List<List<String>> dictList;
	private DictionaryData dictionaryData;
	private boolean flag = true;

	public DatabaseManager() throws SQLException {
		server = new server("wn_pro_mysql");
		server.connect();
		server2 = new server("tables");
		server3 = new server("wordnet");
		server2.connect();
		server3.connect();
		dictionaryData = new DictionaryData();
		dictList = server3.getList();
		searchedWord = new SearchedWord();
		listWords = new ListWord();
		listWords.putData(dictionaryData.getWordTarget_());
		searchedResult = new SearchedResult();
	}

	public void printListWord() {

		// get data from server
		if (flag) {
			for (int i = 0; i < dictList.size(); i++) {
				listWords.getModel().addElement(dictList.get(i).get(0));
			}
		}
		// get data from file
		else {
			for (int i = 0; i < listWords.getMapData().size(); i++) {
				listWords.getModel().addElement(listWords.getMapData().get(i));
			}
		}
	}

	public String typeFormatter(String type) {
		if (type.equals("n")) {
			type = "noun";
		} else if (type.equals("a")) {
			type = "adjective";
		} else if (type.equals("r")) {
			type = "adverb";
		} else if (type.equals("v")) {
			type = "verb";
		} else if (type.equals("s")) {
			type = "adjective";
		}
		return type;
	}

	public String meaningFormatter(String meaning) {
		meaning = meaning.substring(0, 1).toUpperCase() + meaning.substring(1);
		return meaning;
	}

	public List<String> getExamples(String[] meaning_and_examples) {
		List<String> examples = new ArrayList<String>();
		for (int i = 1; i < meaning_and_examples.length; i++) {
			examples.add(meaning_and_examples[i]);
		}
		return examples;
	}

	public String resultFormatter(String word, String type, String pronounciation, String meaning,
			List<String> examples) {
		String images = "<div style=\"position: absolute; top: 15px; left: 409px;\"><img src=\"https://i.ibb.co/L5ykRV0/facebook.png\"\r\n"
				+ "            alt=\"facebook\" /><img src=\"https://i.ibb.co/xzLfZyq/instagram.png\" alt=\"instagram\" /> <img\r\n"
				+ "            src=\"https://i.ibb.co/s2kPLsP/twitter.png\" alt=\"twitter\" /></div>";
		String formatted = "<!DOCTYPE html><html lang='en' style='background-color: white;font-size:15px; height: 450px; width:480px; font-family: Arial, Helvetica, sans-serif;'><head><meta charset='UTF-8'><meta http-equiv='X-UA-Compatible' content='IE=edge'><meta name='viewport' content='width=device-width, initial-scale=1.0'><title>Tu dien</title></head><body style='color: #1d2a57; margin:0px 5px; padding: 0px;'>"
				+ images
				+ "<section><div style='font-size: 20px; border-bottom: 3px solid #fdc702; padding: 5px 0;'><p style='margin-top: 5px; margin-bottom: 10px; font-size: 20px;'><i>Ý nghĩa của <b>"
				+ word
				+ "</b> trong tiếng anh</i></p></div></section><section><div style='line-height: 150%;padding: 0px 15px; border-bottom: 3px solid #fdc702;'><p style='margin: 5px 0; font-size: 15px;'><b>"
				+ word + "</b></p><p style='margin: 5px 0; font-size: 10px;'><b>" + type + "</b></p>"
				+ "<p style='margin: 5px 0; font-size: 10px;'><b>" + pronounciation + "</b></p>"
				+ "</div></section><section><h3 style='line-height: 150%;margin: 5px 0px; font-size: 15px;'>" + meaning
				+ "</h3><ul style='line-height: 150%;margin: 0px 5px 5px 15px'>";
		for (var example : examples) {
			formatted += "<li style='margin: 0px 5px 5px 15px; font-size: 12px; list-style-type: disc;'><i>" + example
					+ "</i></li>";
		}
		formatted += "</ul></section></body></html>";
		return formatted;
	}

	public void printClickedWord() throws SQLException {
		if (flag) {
			var ans = dictList.get(listWords.getJList().getSelectedIndex());
			String word = ans.get(0);
			String type = typeFormatter(ans.get(1));
			String pronounciation = server2.getPronounciation(word);
			String[] meaning_and_examples = ans.get(2).split("; ");
			String meaning = meaning_and_examples[0];
			meaning = meaningFormatter(meaning);
			List<String> examples = getExamples(meaning_and_examples);
			String formattedResult = resultFormatter(word, type, pronounciation, meaning, examples);
			searchedResult.getJPane().setText(formattedResult);
		} else
			searchedResult.getJPane()
					.setText(dictionaryData.getWordInfo_().get(listWords.getJList().getSelectedIndex()));
	}

	public void printSearchedResult(String searchedWord) throws SQLException {
		var ans = server.getSearchWord(searchedWord);
		if (ans.size() == 0)
			return;
		String word = ans.get(0);
		String type = typeFormatter(ans.get(1));
		String pronounciation = server2.getPronounciation(searchedWord);
		String[] meaning_and_examples = ans.get(2).split("; ");
		String meaning = meaning_and_examples[0];
		meaning = meaningFormatter(meaning);
		List<String> examples = getExamples(meaning_and_examples);
		String formattedResult = resultFormatter(word, type, pronounciation, meaning, examples);
		searchedResult.getJPane().setText(formattedResult);
	}

	public String getFormattedResult(String searchedWord) throws SQLException {
		var ans = server.getSearchWord(searchedWord);
		if (ans.size() == 0)
			return null;
		String word = ans.get(0);
		String type = typeFormatter(ans.get(1));
		String pronounciation = server2.getPronounciation(searchedWord);
		String[] meaning_and_examples = ans.get(2).split("; ");
		String meaning = meaning_and_examples[0];
		meaning = meaningFormatter(meaning);
		List<String> examples = getExamples(meaning_and_examples);
		String formattedResult = resultFormatter(word, type, pronounciation, meaning, examples);
		return formattedResult;
	}

	public SearchedWord getSearchedWord() {
		return searchedWord;
	}

	public ListWord getListWords() {
		return listWords;
	}

	public SearchedResult getSearchedResult() {
		return searchedResult;
	}

	public DictionaryData getDictionaryData() {
		return dictionaryData;
	}

	public List<List<String>> getDictList() {
		return dictList;
	}

	public List<String> getDictWord() {
		List<String> ans = new ArrayList<String>();
		for (int i = 0; i < dictList.size(); i++) {
			ans.add(dictList.get(i).get(0));
		}
		return ans;
	}

	public server getFirstServer() {
		return server;
	}
}
