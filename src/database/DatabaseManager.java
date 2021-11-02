package database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import database.filedatabase.DictionaryData;
import database.filedatabase.Word;
import database.sqlserver.Server;

public class DatabaseManager {
	private Server server;
	private Server server2;
	private SearchedWord searchedWord;
	private List<List<String>> dictListEE;
	private List<String> favoriteList;
	private DictionaryData dictionaryData;
	private DictionaryType dictType;

	public enum DictionaryType {
		ENG_ENG, ENG_VI, ENG_VI_API
	}

	public DatabaseManager() throws SQLException {
		server = new Server("wordnet");
		server.connect();
		server2 = new Server("tables");
		server2.connect();
		dictionaryData = new DictionaryData();
		dictListEE = server.getList();
		searchedWord = new SearchedWord();
		favoriteList = server2.getFavourite();
		dictType = DictionaryType.ENG_ENG;
	}

	public String typeFormatter(String type) {
		if (dictType == DictionaryType.ENG_ENG) {
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
		} else {
			if (type.equals("n")) {
				type = "DANH TỪ";
			} else if (type.equals("a")) {
				type = "TÍNH TỪ";
			} else if (type.equals("r")) {
				type = "TRẠNG TỪ";
			} else if (type.equals("v")) {
				type = "ĐỘNG TỪ";
			} else if (type.equals("s")) {
				type = "TÍNH TỪ";
			}
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

	public String getFormattedResult(String searchedWord, DictionaryType dict)
			throws SQLException {
		if (dict == DictionaryType.ENG_VI) {
			return getResultFromFile(searchedWord);
		}
		if (dict == DictionaryType.ENG_VI_API) {
			return getResultFromAPI(searchedWord);
		}
		List<List<String>> ans = server.getSearchWord(searchedWord);
		if (ans.size() == 0) {
			return null;
		}
		String word = ans.get(0).get(0);
		List<String> types = new ArrayList<String>();
		List<String> meanings = new ArrayList<String>();
		List<List<String>> examples = new ArrayList<List<String>>();
		List<List<String>> synonyms = new ArrayList<List<String>>();
		List<String> adjTypes = new ArrayList<String>();
		for (int i = 0; i < ans.size(); i++) {
			String type = typeFormatter(ans.get(i).get(1));
			String meaning = ans.get(i).get(2);
			String example = ans.get(i).get(3);
			String synonym = ans.get(i).get(4);
			String adjType = ans.get(i).get(5);
			types.add(type);
			adjTypes.add(adjType != null ? adjType : null);
			meanings.add(meaning);

			examples.add(
					example != null ? Arrays.asList(example.split(",")) : null);
			synonyms.add(
					synonym != null ? Arrays.asList(synonym.split(",")) : null);
		}
		String pronounciation = server2.getPronounciation(searchedWord);
		String formattedResult =
				resultFormatter(word, types, pronounciation, meanings, examples,
						synonyms, adjTypes);
		return formattedResult;

	}

	public String resultFormatter(String word, List<String> types,
			String pronounciation, List<String> meanings,
			List<List<String>> exampless, List<List<String>> synonymss,
			List<String> adjTypes) {
		String images =
				"<div style=\"position: absolute; top: 15px; left: 700px;\"><img src=\"https://i.ibb.co/L5ykRV0/facebook.png\"\r\n"
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
						+ word + "</b> trong Tiếng "
						+ (dictType == DictionaryType.ENG_ENG ? "Anh" : "Việt")
						+ "</i></p></div></section><section><div style='line-height: 150%;padding: 0px 15px; border-bottom: 3px solid #fdc702;'><p style='margin: 5px 0; font-size: 24px;'><b>"
						+ word + "</b></p>"
						+ "<p style='margin: 5px 0; font-size: 20px;'><b>"
						+ pronounciation + "</b></p>"
						+ " <div style=\"position: relative; left: 0px\">\r\n"
						+ "                    <img src=\"https://upload.wikimedia.org/wikipedia/commons/thumb/2/21/Speaker_Icon.svg/1024px-Speaker_Icon.svg.png\"\r\n"
						+ "                        alt=\"volumn\" width=\"24px\" height=\"24px\">\r\n"
						+ "                </div>"
						+ "</div></section><section>";
		String currentType = null;
		if (types != null) {
			currentType = types.get(0);
		}

		formatted +=
				"<p style=\"margin: 5px 5px; font-size: 24px;\"><b>"
						+ currentType + "</b></p>\r\n" + "            <ol>";
		for (int i = 0; i < types.size(); i++) {
			if (types != null) {
				if (types.get(i) != null) {
					String type = types.get(i);
					if (!type.equals(currentType)) {
						currentType = type;
						formatted +=
								"</ol> <div style=\"line-height: 300%;padding: 0px 15px; border-bottom: 3px solid #fdc702;\">\r\n"
										+ "\r\n" + "            </div>"
										+ "<p style=\"margin: 5px 0; font-size: 24px;\"><b>"
										+ type + "</b></p>\r\n"
										+ "            <ol>";
					}
				}
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
					synonym + meaningFormatter(meanings.get(i))
							+ (adjTypes != null && adjTypes.get(i) != null
									? " (" + adjTypes.get(i) + ")"
									: "")
							+ "</h3>\r\n"
							+ "<ul style=\"margin:  5px 5px 5px 5px;\">";
			if (exampless != null && exampless.size() != 0) {
				if (exampless.get(i) != null) {
					List<String> examples = exampless.get(i);
					if (examples != null) {
						for (String example : examples) {
							formatted +=
									"<li style='font-style: italic;margin: 5px 5px 10px 5px; font-size: 22px; list-style-type: disc;'><i>"
											+ exampleFormatter(example)
											+ "</i></li>";
						}
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

	public String getResultFromFile(String searched) throws SQLException {
		int pos = dictionaryData.findPosition(searched);
		if (pos == -1) {
			return null;
		}
		String word = dictionaryData.getWordTarget().get(pos);
		if (word.equals(searched) == false) {
			return null;
		}
		List<String> types = dictionaryData.getWordTypes().get(pos);
		List<String> meanings = dictionaryData.getWordMeanings().get(pos);
		List<List<String>> exampless =
				dictionaryData.getWordExampless().get(pos);
		String pronounciation = server2.getPronounciation(searched);

		return resultFormatter(word, types, pronounciation, meanings, exampless,
				null, null);
	}

	public String getResultFromAPI(String searched) throws SQLException {
		String meaning = null;
		try {
			meaning = translate("en", "vi", searched);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<List<String>> ans = server.getSearchWord(searched);
		if (ans.size() == 0) {
			return null;
		}
		List<String> types = new ArrayList<String>();
		List<String> meanings = new ArrayList<String>();
		List<List<String>> examples = new ArrayList<List<String>>();
		List<String> adjTypes = new ArrayList<String>();
		String type = typeFormatter(ans.get(0).get(1));
		String example = ans.get(0).get(3);
		String adjType = ans.get(0).get(5);
		types.add(type);
		meanings.add(meaningFormatter(meaning));
		if (example != null) {
			examples.add(Arrays.asList(example.split(",")));
		} else {
			examples.add(null);
		}

		if (adjType != null) {
			adjTypes.add(adjType);
		} else {
			adjTypes.add(null);
		}

		String pronounciation = server2.getPronounciation(searched);
		String formattedResult =
				resultFormatter(searched, types, pronounciation, meanings,
						examples, null, adjTypes);
		return formattedResult;
	}

	public String translate(String langFrom, String langTo, String text)
			throws IOException {
		String urlStr =
				"https://script.google.com/macros/s/AKfycby1KXAnwL5Nm1WY3m_CZ4BENAfQZCqcQ8Vyzcam8TEIsu9pL0DUz_E55nzYGSy0Av795g/exec"
						+ "?q=" + URLEncoder.encode(text, "UTF-8") + "&target="
						+ langTo + "&source=" + langFrom;
		System.out.println(urlStr);
		URL url = new URL(urlStr);
		StringBuilder response = new StringBuilder();
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		BufferedReader in =
				new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
	}

	public SearchedWord getSearchedWord() {
		return searchedWord;
	}

	public DictionaryData getDictionaryData() {
		return dictionaryData;
	}

	public List<List<String>> getDictList() {
		return dictListEE;
	}

	public List<String> getDictWordEE() {
		List<String> ans = new ArrayList<String>();
		for (int i = 0; i < dictListEE.size(); i++) {
			ans.add(dictListEE.get(i).get(0));
		}
		return ans;
	}

	public int findPositionFavourite(String w) {
		int n = favoriteList.size() - 1;
		int i = 0;
		if (favoriteList.size() == 0) {
			return 0;
		}
		if (w.compareTo(favoriteList.get(0)) <= 0) {
			return 0;
		}

		while (i < n) {
			if (w.compareTo(favoriteList.get(n)) == 0) {
				return n;
			}

			if (w.compareTo(favoriteList.get(n)) > 0) {
				return n + 1;
			}

			String w1 = favoriteList.get((i + n) / 2);
			int compare = w.compareTo(w1);

			if (compare == 0) {
				return (i + n) / 2;

			} else if (compare > 0) {
				i = (i + n) / 2 + 1;
				n--;
			} else {
				n = (i + n) / 2;
			}
		}
		if (w.compareTo(favoriteList.get(i)) > 0) {
			return i + 1;
		}
		return i;
	}

	public void changeDictionary(DictionaryType dictType_) {
		this.dictType = dictType_;
	}

	public DictionaryType getDictType() {
		return dictType;
	}

	public int addFavourite(String word) {
		server2.addFavourite(word);
		if (favoriteList.size() == 0) {
			favoriteList.add(0, word);
			return 0;
		}
		int index = findPositionFavourite(word);
		if (index == favoriteList.size()) {
			favoriteList.add(index, word);
			return index;
		}
		if (favoriteList.get(index).compareTo(word) != 0) {
			favoriteList.add(index, word);
			return index;
		}
		return -1;

	}

	public int removeFavourite(String word) {
		server2.removeFavourite(word);
		if (favoriteList.size() == 0) {
			return -1;
		}
		int index = findPositionFavourite(word);
		if (index == -1) {
			return -1;
		}
		if (favoriteList.get(index).compareTo(word) == 0) {
			favoriteList.remove(index);
			return index;
		}
		return -1;
	}

	public int addWord(String word, String type, String meaning) {
		server2.addWord(word, type, meaning);
		return dictionaryData.addWord(new Word(word, type, meaning));
	}

	public int changeWord(String word, String type, String meaning) {
		server2.changeWord(word, type, meaning);
		return dictionaryData.changeWord(new Word(word, type, meaning));
	}

	public int deleteWord(String word) {
		server2.deleteWord(word);
		return dictionaryData.deleteWord(word);
	}

	public List<String> getFavourite() {
		return favoriteList;
	}
}
