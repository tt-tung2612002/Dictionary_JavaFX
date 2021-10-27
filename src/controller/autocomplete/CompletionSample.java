package controller.autocomplete;

import java.util.ArrayList;
import java.util.List;

import database.DatabaseManager;

public class CompletionSample implements Runnable {

	public static class NameService implements CompletionService<String> {
		// private List<String> data;
		private DatabaseManager databaseManager;

		public NameService(DatabaseManager databaseManager) {
			// ArrayList<Word> DictData = dict.getDictData();
			// String words[] = new String[138975];
			// for (int i = 0; i < 138975; i++) {
			// words[i] = new String(DictData.get(i).getWord_target());
			// }
			// data = Arrays.asList(words);
			this.databaseManager = databaseManager;
		}

		@Override
		public String toString() {
			StringBuilder b = new StringBuilder();
			// for (String o : data) {
			// b.append(o).append("\n");
			// }
			return b.toString();
		}

		@Override
		public String autoComplete(String startsWith) {
			String ans = null;
			List<String> anss = new ArrayList<String>();
			if (startsWith != null && !startsWith.equals("Search in English")
					&& startsWith.length() >= 3)
				anss =
						databaseManager.getFirstServer()
								.getClosestResults(startsWith);
			if (anss.size() > 0)
				return anss.get(0);
			return ans;
		}

	}

	@Override
	public void run() {

	}
}
