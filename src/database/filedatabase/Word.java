package database.filedatabase;

import java.util.ArrayList;
import java.util.List;

public class Word implements Comparable<Word> {
	private String word_target = new String();
	private List<String> types = new ArrayList<String>();
	private List<String> word_meanings = new ArrayList<String>();
	private List<List<String>> examples = new ArrayList<List<String>>();

	Word(String word_target, List<String> types, List<String> word_meanings,
			List<List<String>> examples) {
		this.word_target = word_target;
		this.word_meanings = word_meanings;
		this.types = types;
		this.examples = examples;
	}

	public Word(String word_target, String type, String meaning) {
		this.word_target = word_target;
		this.types.add(type);
		this.word_meanings.add(meaning);
	}

	public List<String> getMeanings() {
		return word_meanings;
	}

	public void setMeanings(List<String> word_meanings) {
		this.word_meanings = word_meanings;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public List<List<String>> getExamples() {
		return examples;
	}

	public void setExamples(List<List<String>> examples) {
		this.examples = examples;
	}

	public String getWord_target() {
		return word_target;
	}

	public void setWord_target(String word_target) {
		this.word_target = word_target;
	}

	public boolean equals(Word w) {
		return this.word_target.equals(w.word_target);
	}

	// @Override
	@Override
	public int compareTo(Word t) {
		return (this.word_target).compareTo(t.word_target);
	}
}
