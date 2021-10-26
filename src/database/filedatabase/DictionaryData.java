package database.filedatabase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DictionaryData {
	private final String FILE_PATH =
			"E:\\Program\\eclipse\\Dictionary\\src\\application\\EV.txt";
	private ArrayList<Word> DictList = new ArrayList<Word>();
	private Map<Integer, String> wordTarget_ = new HashMap<>();
	private Map<Integer, String> wordInfo_ = new HashMap<>();

	public String getFILE_PATH() {
		return FILE_PATH;
	}

	public DictionaryData() {
		String line;
		try {
			BufferedReader reader =
					new BufferedReader(new FileReader(FILE_PATH));

			while ((line = reader.readLine()) != null) {
				String word = "";
				String info;
				char[] a = line.toCharArray();

				int i = 0;
				while (a[i] != '<') {
					word += a[i];
					i++;
				}
				info = line.substring(i);
				this.DictList.add(new Word(word, info));
			}
			reader.close();
		} catch (Exception e) {
			System.out.println(this.DictList.size());
			System.out.println("Can't read file:" + FILE_PATH);
		}

		System.out.println(this.getDictData().size());
	}

	// finding position of a word if dictionary has it or the position of the
	// word
	// front of it
	public int findPosition(String w) {
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
		if (w.compareTo(this.getDictData().get(i).getWord_target()) > 0)
			return i + 1;// find "c" in "a", "b", "d" will return 2 pos of "d"
		return i;
	}

	// add word into dictionary if it doesn't exist.
	boolean addWord(Word word) {
		int i = this.findPosition(word.getWord_target());

		if (this.DictList.size() == 0
				|| this.DictList.get(i).compareTo(word) != 0) {
			this.DictList.add(i, word);
			return true;
		} else
			return false;
	}

	// remove word from dictionary if it exists.
	boolean removeWord(Word word) {
		if (this.DictList.remove(word))
			return true;

		if (this.DictList.size() == 0)
			return false;

		int i = this.findPosition(word.getWord_target());

		if (this.DictList.get(i).compareTo(word) == 0) {
			this.DictList.remove(i);
			return true;
		} else
			return false;
	}

	// make change in word's info
	void changeWordInfo(Word word, String info) {
		word.setWord_info(info);
	}

	public ArrayList<Word> getDictData() {
		return DictList;
	}

	public Map<Integer, String> getWordTarget_() {
		try {
			for (int i = 0; i < DictList.size(); i++) {
				wordTarget_.put(i, DictList.get(i).getWord_target());
			}

		} catch (Exception e) {
			System.out.println("Can't get Word!!!!!");
		}
		return wordTarget_;
	}

	public Map<Integer, String> getWordInfo_() {
		try {
			for (int i = 0; i < DictList.size(); i++) {
				wordInfo_.put(i, DictList.get(i).getWord_info());
			}

		} catch (Exception e) {
			System.out.println("Can't get info Word!!!!!");
		}
		return wordInfo_;
	}

}
