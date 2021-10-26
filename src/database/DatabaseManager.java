package database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import database.filedatabase.DictionaryData;
import database.sqlserver.Server;
import database.sqlserver.server;

public class DatabaseManager {
	private Server server;
	private Server server2;
	private SearchedWord searchedWord;
	private ListWord listWords;
	private SearchedResult searchedResult;
	private List<List<String>> dictList;
	private DictionaryData dictionaryData;
	private boolean flag = true;

	public DatabaseManager() throws SQLException {
		server = new Server("wordnet");
		server.connect();
		server2 = new Server("tables");
		server2.connect();

		// dictionaryData = new DictionaryData();
		dictList = server.getList();
		searchedWord = new SearchedWord();
		// listWords = new ListWord();
		// listWords.putData(dictionaryData.getWordTarget_());
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
		return type.toUpperCase();
	}

	public String meaningFormatter(String meaning) {
		return meaning.substring(0, 1).toUpperCase() + meaning.substring(1)
				+ ".";
	}

	public String exampleFormatter(String example) {
		return example.substring(0, 1).toUpperCase() + example.substring(1)
				+ ".";
	}

	public List<String> getExamples(String[] meaning_and_examples) {
		List<String> examples = new ArrayList<String>();
		for (int i = 1; i < meaning_and_examples.length; i++) {
			examples.add(meaning_and_examples[i]);
		}
		return examples;
	}

	public String resultFormatter(String word, List<String> types,
			String pronounciation, List<String> meanings,
			List<List<String>> exampless, List<List<String>> synonymss) {
		String images =
				"<div style=\"position: absolute; top: 15px; left: 670px;\"><img src=\"https://i.ibb.co/L5ykRV0/facebook.png\"\r\n"
						+ "alt=\"facebook\" /><img src=\"https://i.ibb.co/xzLfZyq/instagram.png\" alt=\"instagram\" /> <img\r\n"
						+ "            src=\"https://i.ibb.co/s2kPLsP/twitter.png\" alt=\"twitter\" /></div>";
		String formatted =
				"<!DOCTYPE html><html lang='en' style='background-color: white;font-size:21px; height: 610px; width:660; font-family: Arial, Helvetica, sans-serif;'><head><meta charset='UTF-8'><meta http-equiv='X-UA-Compatible' content='IE=edge'><meta name='viewport' content='width=device-width, initial-scale=1.0'><title>Tu dien</title><style>\r\n"
						+ "        ol li {\r\n"
						+ "            font-weight: bold;\r\n" + "        }\r\n"
						+ "\r\n" + "        li>p {\r\n"
						+ "            font-weight: normal;\r\n"
						+ "        }\r\n" + "\r\n" + "        ol li>ul>Li {\r\n"
						+ "            font-weight: normal;\r\n"
						+ "        }\r\n"
						+ "    </style></head><body style='color: #1d2a57; margin:0px 5px; padding: 0px;'>"
						+ images
						+ "<section><div style='font-size: 35 px; border-bottom: 3px solid #fdc702; padding: 5px 0;'><p style='margin-top: 5px; margin-bottom: 10px; font-size: 30px;'><i>Ý nghĩa của <b>"
						+ word
						+ "</b> trong tiếng anh</i></p></div></section><section><div style='line-height: 150%;padding: 0px 15px; border-bottom: 3px solid #fdc702;'><p style='margin: 5px 0; font-size: 24px;'><b>"
						+ word + "</b></p>"
						+ "<p style='margin: 5px 0; font-size: 20px;'><b>"
						+ pronounciation + "</b></p>"
						+ " <div style=\"position: relative; left: 0px\">\r\n"
						+ "                    <img src=\"https://upload.wikimedia.org/wikipedia/commons/thumb/2/21/Speaker_Icon.svg/1024px-Speaker_Icon.svg.png\"\r\n"
						+ "                        alt=\"volumn\" width=\"17px\" height=\"17px\">\r\n"
						+ "                </div>"
						+ "</div></section><section>";
		String currentType = types.get(0);
		formatted +=
				"<p style=\"margin: 5px 5px; font-size: 24px;\"><b>"
						+ currentType + "</b></p>\r\n" + "            <ol>";
		for (int i = 0; i < types.size(); i++) {
			String type = types.get(i);
			if (!type.equals(currentType)) {
				currentType = type;
				formatted +=
						"</ol> <div style=\"line-height: 300%;padding: 0px 15px; border-bottom: 3px solid #fdc702;\">\r\n"
								+ "\r\n" + "            </div>"
								+ "<p style=\"margin: 5px 0; font-size: 24px;\"><b>"
								+ type + "</b></p>\r\n" + "            <ol>";
			}
			formatted +=
					"<li style=\"margin: 0px 5px 5px 15px; font-size: 20px;list-style-type:decimal;\">"
							+ "                    <h3 style=\"line-height: 150%;margin: 5px 0px; font-size: 21px;\">";
			String synonym = new String();
			if (synonymss != null) {
				List<String> synonyms = synonymss.get(i);
				if (synonyms != null) {
					for (int j = 0; j < synonyms.size() && j < 4; j++)
						synonym += synonyms.get(j) + ", ";
				}
			}

			synonym =
					synonym.length() == 0 ? ""
							: "(" + synonym.substring(0, synonym.length() - 2)
									+ ") ";
			formatted +=
					synonym + meanings.get(i) + "</h3>\r\n"
							+ "<ul style=\"margin:  5px 5px 5px 5px;\">";
			if (exampless != null) {
				List<String> examples = exampless.get(i);
				if (examples != null) {
					for (String example : examples) {
						formatted +=
								"<li style='margin: 5px 5px 10px 5px; font-size: 22px; list-style-type: disc;'><i>"
										+ exampleFormatter(example)
										+ "</i></li>";
					}
				}
			}

			formatted += "</ul>\r\n" + "                </li>";
		}
		formatted +=
				"</ol><p>&nbsp;</p> \r\n" + "<p></p> \r\n" + "\r\n"
						+ "        </section>\r\n" + "    </div>\r\n"
						+ "</body>\r\n" + "\r\n" + "</html>";
		return formatted;
	}

	public String getFormattedResult(String searchedWord) throws SQLException {
		List<List<String>> ans = server.getSearchWord(searchedWord);
		if (ans.size() == 0)
			return null;
		String word = ans.get(0).get(0);
		List<String> types = new ArrayList<String>();
		List<String> meanings = new ArrayList<String>();
		List<List<String>> examples = new ArrayList<List<String>>();
		List<List<String>> synonyms = new ArrayList<List<String>>();

		for (int i = 0; i < ans.size(); i++) {
			String type = typeFormatter(ans.get(i).get(1));
			String meaning = ans.get(i).get(2);
			String example = ans.get(i).get(3);
			String synonym = ans.get(i).get(4);
			types.add(type);
			meanings.add(meaningFormatter(meaning));
			if (example != null) {
				examples.add(Arrays.asList(example.split(",")));
			} else
				examples.add(null);

			if (synonym != null)
				synonyms.add(Arrays.asList(synonym.split(",")));
			else
				synonyms.add(null);
		}
		String pronounciation = server2.getPronounciation(searchedWord);
		String formattedResult =
				resultFormatter(word, types, pronounciation, meanings, examples,
						synonyms);
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
