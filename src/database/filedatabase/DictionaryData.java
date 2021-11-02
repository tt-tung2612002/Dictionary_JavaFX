package database.filedatabase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import database.sqlserver.Server;

public class DictionaryData {
	private ArrayList<Word> dictList = new ArrayList<Word>();
	List<String> wordTarget = new ArrayList<String>();
	List<List<String>> wordTypes = new ArrayList<List<String>>();
	List<List<String>> wordMeanings = new ArrayList<List<String>>();
	List<List<List<String>>> wordExampless =
			new ArrayList<List<List<String>>>();

	public DictionaryData() {
		try {
			Server server = new Server("tables");
			server.connect();
			List<List<String>> myDictionary = server.getMyDictionary();
			BufferedReader reader =
					new BufferedReader(
							new FileReader("resources/dictionaries.txt"));

			for (String next = "", line = reader.readLine(); line
					!= null; line = next) {
				next = reader.readLine();
				String word = "";
				List<String> meanings = new ArrayList<String>();
				List<String> types = new ArrayList<String>();
				List<List<String>> exampless = new ArrayList<List<String>>();
				List<String> examples = new ArrayList<String>();

				if (line.charAt(0) == '#') {
					String part[] = line.split("/", 2);
					word = part[0].trim().substring(1);
				}
				if (next != null) {
					next = next.trim();
				}
				while (next != null && next.charAt(0) != '#') {

					// line is the current type of the word.
					if (next.charAt(0) == '*') {
						types.add(next.substring(1));
						next = reader.readLine();
						if (next != null) {
							next = next.trim();
						} else {
							break;
						}
					} else {
						types.add(null);
					}

					// line is the current meaning of the word.
					if (next.charAt(0) == '-') {
						meanings.add(next.substring(2));
						next = reader.readLine();
						if (next != null) {
							next = next.trim();
						} else {
							break;
						}
					}

					// line is the current example of the word.
					if (next.charAt(0) == '=') {
						while (next != null && next.charAt(0) == '=') {
							examples.add(next.substring(1));
							next = reader.readLine();
							if (next != null) {
								next = next.trim();
							}
						}
						exampless.add(examples);
						examples = new ArrayList<String>();
					} else {
						exampless.add(null);
					}
				}

				dictList.add(new Word(word, types, meanings, exampless));
			}
			reader.close();
			for (int i = 0; i < myDictionary.size(); i++) {
				String word = myDictionary.get(i).get(0);
				List<String> type = new ArrayList<String>();
				type.add(myDictionary.get(i).get(1));
				List<String> meaning = new ArrayList<String>();
				meaning.add(myDictionary.get(i).get(2));
				dictList.add(new Word(word, type, meaning, null));
			}
			Collections.sort(dictList);
			try {
				for (Word word : dictList) {
					wordTarget.add(word.getWord_target());
					wordTypes.add(word.getTypes());
					wordMeanings.add(word.getMeanings());
					List<List<String>> examples = word.getExamples();
					if (examples != null) {
						wordExampless.add(word.getExamples());
					} else {
						wordExampless.add(null);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// finding position of a word if dictionary has it or the position of the
	// word in front of it
	public int findPosition(String w) {
		if (this.getDictData().size() == 0) {
			return 0;
		}
		int n = this.getDictData().size() - 1;
		int i = 0;
		if (w.compareTo(this.getDictData().get(0).getWord_target()) <= 0)
			return 0;

		while (i < n) {
			if (w.compareTo(this.getDictData().get(n).getWord_target()) == 0)
				return n;

			if (w.compareTo(this.getDictData().get(n).getWord_target()) > 0)
				return n + 1;// find "e" in "a", "b", "d" will return 4 pos
								// after "d"

			Word w1 = this.getDictData().get((i + n) / 2);
			int compare = w.compareTo(w1.getWord_target());

			if (compare == 0) {
				return (i + n) / 2;

			} else if (compare > 0) {
				i = (i + n) / 2 + 1;
				n--;
			} else {
				n = (i + n) / 2;
			}
		}
		if (w.compareTo(this.getDictData().get(i).getWord_target()) > 0) {
			return i + 1;
		}
		return i;
	}

	// add word to dictionary if it doesn't exist.
	public int addWord(Word word) {
		int i = this.findPosition(word.getWord_target());
		if (this.dictList.get(i).compareTo(word) != 0) {
			dictList.add(i, word);
			wordTarget.add(i, word.getWord_target());
			wordTypes.add(i, word.getTypes());
			wordMeanings.add(i, word.getMeanings());
			wordExampless.add(i, word.getExamples());
			return i;
		}
		return -1;

	}

	// change word from dictionary if it exists.
	public int changeWord(Word word) {
		int i = this.findPosition(word.getWord_target());
		if (this.dictList.get(i).compareTo(word) == 0) {
			this.dictList.set(i, word);
			wordTarget.set(i, word.getWord_target());
			wordTypes.set(i, word.getTypes());
			wordMeanings.set(i, word.getMeanings());
			wordExampless.set(i, word.getExamples());
			return i;
		}
		return -1;

	}

	// remove word from dictionary if it exists.
	public int deleteWord(String word) {
		int i = this.findPosition(word);
		if (this.dictList.get(i).getWord_target().compareTo(word) == 0) {
			this.dictList.remove(i);
			wordTarget.remove(i);
			wordTypes.remove(i);
			wordMeanings.remove(i);
			wordExampless.remove(i);
			return i;
		}
		return -1;

	}

	public ArrayList<Word> getDictData() {
		return dictList;
	}

	public List<String> getWordTarget() {
		return this.wordTarget;
	}

	public List<List<String>> getWordTypes() {
		return this.wordTypes;
	}

	public List<List<String>> getWordMeanings() {
		return this.wordMeanings;
	}

	public List<List<List<String>>> getWordExampless() {
		return this.wordExampless;
	}
}
