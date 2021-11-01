package database.sqlserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Server {
	private String url = "jdbc:mysql://localhost:3306/";
	private String user = "root";
	private String password = "tung2612002";
	private Connection con;
	private String query;
	private String antonymIdentifier = "{call getAntonym(?)}";
	private String searchedWordIdentifier = "{CALL searchWord2(?)}";
	private String listIdentifier = "call printList()";
	private String pronounciationIdentifier = "{CALL getPronounciation(?)}";
	private String closestResultsIdentifier = "{call findClosestResults(?)}";
	private String addWordIdentifier = "{CALL addWord(?,?,?)}";
	private String deleteWordIdentifier = "{CALL deleteWord(?)}";
	private String changeWordIdentifier = "{CALL changeWord(?,?,?)}";
	private String addFvouriteIdentifier = "{CALL addFavourite(?)}";
	private String removeFvouriteIdentifier = "{CALL removeFavourite(?)}";
	private String getFvouriteIdentifier = "{CALL getFavourite()}";
	private String getMyDictionaryIdentifier = "{CALL getMyDictionary()}";

	public Server(String database) {
		url += database;
	}

	public void connect() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			con = DriverManager.getConnection(url, user, password);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<String> getAntonym(String word) throws SQLException {
		List<String> ans = new ArrayList<String>();
		try {
			query = antonymIdentifier;
			PreparedStatement statement = con.prepareStatement(query);
			statement.setString(1, word);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				ans.add(resultSet
						.getString(resultSet.getMetaData().getColumnCount()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ans;
	}

	public List<List<String>> getSearchWord(String word) throws SQLException {
		List<List<String>> ans = new ArrayList<List<String>>();
		try {
			query = searchedWordIdentifier;
			PreparedStatement statement = con.prepareCall(query);
			statement.setString(1, word);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				List<String> temp = new ArrayList<String>();
				for (int i = 1; i
						<= resultSet.getMetaData().getColumnCount(); i++) {
					temp.add(resultSet.getString(i));
				}
				ans.add(temp);
			}
		} catch (SQLException e) {
			System.out.println("TUan oc oc");
			e.printStackTrace();
		}
		return ans;
	}

	public List<List<String>> getSearchWordAntonym(String word)
			throws SQLException {
		List<List<String>> ans = new ArrayList<List<String>>();
		try {
			query = antonymIdentifier;
			PreparedStatement statement = con.prepareCall(query);
			statement.setString(1, word);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				List<String> temp = new ArrayList<String>();
				for (int i = 1; i
						<= resultSet.getMetaData().getColumnCount(); i++) {
					temp.add(resultSet.getString(i));
				}
				ans.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ans;
	}

	public String getPronounciation(String word) throws SQLException {
		String ans = new String();
		try {
			query = pronounciationIdentifier;
			PreparedStatement statement = con.prepareCall(query);
			statement.setString(1, word);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				ans = resultSet.getString(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ans;
	}

	public List<List<String>> getList() throws SQLException {
		List<List<String>> ans = new ArrayList<List<String>>();

		query = listIdentifier;
		PreparedStatement statement = con.prepareStatement(query);
		ResultSet resultSet = statement.executeQuery(query);
		while (resultSet.next()) {
			List<String> temp = new ArrayList<String>();
			for (int i = 1; i
					<= resultSet.getMetaData().getColumnCount(); i++) {
				temp.add(resultSet.getString(i));
			}
			ans.add(temp);
		}
		return ans;
	}

	public List<String> getClosestResults(String word) {
		List<String> ans = new ArrayList<String>();
		try {
			query = closestResultsIdentifier;
			PreparedStatement statement = con.prepareStatement(query);
			statement.setString(1, word);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				ans.add(resultSet.getString(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ans;
	}

	public int addWord(String word, String type, String meaning) {
		int count = 0;
		try {
			query = addWordIdentifier;
			PreparedStatement statement = con.prepareCall(query);
			statement.setString(1, word);
			statement.setString(2, type);
			statement.setString(3, meaning);
			count = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public List<String> getFavourite() {
		List<String> ans = new ArrayList<String>();
		try {
			query = getFvouriteIdentifier;
			PreparedStatement statement = con.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				ans.add(resultSet.getString(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ans;
	}

	public int addFavourite(String word) {
		int count = 0;
		try {
			query = addFvouriteIdentifier;
			PreparedStatement statement = con.prepareCall(query);
			statement.setString(1, word);
			count = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int removeFavourite(String word) {
		int count = 0;
		try {
			query = removeFvouriteIdentifier;
			PreparedStatement statement = con.prepareCall(query);
			statement.setString(1, word);
			count = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int changeWord(String word, String type, String meaning) {
		int count = 0;
		try {
			query = changeWordIdentifier;
			PreparedStatement statement = con.prepareCall(query);
			statement.setString(1, word);
			statement.setString(2, type);
			statement.setString(3, meaning);
			count = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int deleteWord(String word) {
		int count = 0;
		try {
			query = deleteWordIdentifier;
			PreparedStatement statement = con.prepareCall(query);
			statement.setString(1, word);
			count = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public List<List<String>> getMyDictionary() throws SQLException {
		List<List<String>> ans = new ArrayList<List<String>>();
		query = getMyDictionaryIdentifier;
		PreparedStatement statement = con.prepareStatement(query);
		ResultSet resultSet = statement.executeQuery(query);
		while (resultSet.next()) {
			List<String> temp = new ArrayList<String>();
			for (int i = 1; i
					<= resultSet.getMetaData().getColumnCount(); i++) {
				temp.add(resultSet.getString(i));
			}
			ans.add(temp);
		}
		return ans;
	}

	public static void main(String[] args) throws SQLException {
		System.out.println(Character.MAX_VALUE);
	}

}
