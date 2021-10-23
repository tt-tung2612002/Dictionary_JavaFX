package database.sqlserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class server {
	private String url = "jdbc:mysql://localhost:3306/";
	private String user = "root";
	private String password = "tung2612002";
	private Connection con;
	private String query;
	private String synonymIdentifier = "call findSynonym('";
	private String antonymIdentifier = "call findAnonynym('";
	private String searchedWordIdentifer = "{CALL searchWord(?)}";
	private String listIdentifer = "call printList()";
	private String pronounciationIdentifier = "{CALL getPronounciation(?)}";
	private String closestResultsIdentifer = "call findClosestResults('";

	public server(String database) {
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

	public List<String> getSynonym(String word) throws SQLException {
		List<String> ans = new ArrayList<String>();
		try {
			query = synonymIdentifier + word + "')";
			PreparedStatement statement = con.prepareStatement(query);
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

	public List<String> getAntonym(String word) throws SQLException {
		List<String> ans = new ArrayList<String>();
		try {
			query = antonymIdentifier + word + "')";
			PreparedStatement statement = con.prepareStatement(query);
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
			query = searchedWordIdentifer;
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

	public List<List<String>> getList() throws SQLException {
		List<List<String>> ans = new ArrayList<List<String>>();

		query = listIdentifer;
		Statement statement = con.createStatement();
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

	public List<String> getClosestResults(String word) {
		List<String> ans = new ArrayList<String>();
		try {
			query = closestResultsIdentifer + word + "')";
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

}
