package bjc.dbeditor.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bjc.utils.funcdata.FunctionalList;
import bjc.utils.funcdata.IList;

import bjc.dbeditor.data.FeatTag;

public class FeatTagDB {
	private static Connection			dbConn;
	private static PreparedStatement	listTagStatement;
	private static PreparedStatement	lookupTagStatement;
	private static PreparedStatement	createTagStatement;

	private static PreparedStatement	listTagNamesStatement;

	public static boolean addFeatTag(FeatTag tagToAdd)
			throws SQLException {
		createTagStatement.setString(1, tagToAdd.getName());
		createTagStatement.setString(2, tagToAdd.getDescription());

		boolean updateSucceded = createTagStatement.executeUpdate() > 0;

		if (updateSucceded == true) {
			dbConn.commit();
		} else {
			dbConn.rollback();
		}

		return updateSucceded;
	}

	public static void initConnection(Connection conn)
			throws SQLException {
		dbConn = conn;

		initStatements();
	}

	public static void disposeConnection() throws SQLException {

		listTagNamesStatement.close();
		createTagStatement.close();
		lookupTagStatement.close();
		listTagStatement.close();

	}

	private static void initStatements() throws SQLException {
		listTagStatement = dbConn.prepareStatement(
				"SELECT name, description FROM feattags");
		lookupTagStatement = dbConn.prepareStatement(
				"SELECT name, description FROM feattags WHERE name = ?");
		createTagStatement = dbConn.prepareStatement(
				"INSERT INTO feattags (name, description) VALUES (?, ?)");
		listTagNamesStatement = dbConn.prepareStatement(
				"SELECT name FROM feattags ORDER BY name");
	}

	public static IList<FeatTag> listTags() throws SQLException {
		FunctionalList<FeatTag> tagList = new FunctionalList<>();

		ResultSet tagSet = listTagStatement.executeQuery();

		while (tagSet.next() == true) {
			tagList.add(new FeatTag(tagSet.getString("name"),
					tagSet.getString("description")));
		}

		tagSet.close();

		return tagList;
	}

	public static IList<String> listTagNames() throws SQLException {
		FunctionalList<String> tagNames = new FunctionalList<>();

		ResultSet nameSet = listTagNamesStatement.executeQuery();

		while (nameSet.next() == true) {
			tagNames.add(nameSet.getString("name"));
		}

		nameSet.close();

		return tagNames;
	}

	public static boolean doesTagExist(String tagName)
			throws SQLException {
		lookupTagStatement.setString(1, tagName);

		try (ResultSet query = lookupTagStatement.executeQuery()) {
			if (query.next()) {
				return tagName.equals(query.getString("name"));
			}

			return false;
		}
	}

	public static FeatTag lookupTag(String tagName) throws SQLException {
		lookupTagStatement.setString(1, tagName);

		try (ResultSet tagSet = lookupTagStatement.executeQuery()) {
			if (tagSet.next()) {
				return new FeatTag(tagSet.getString("name"),
						tagSet.getString("description"));
			}

			return null;
		}
	}
}
