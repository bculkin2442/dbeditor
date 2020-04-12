package bjc.dbeditor.db;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bjc.dbeditor.data.feat.Feat;
import bjc.dbeditor.data.feat.FeatBuilder;
import bjc.funcdata.FunctionalList;
import bjc.funcdata.IList;

/**
 * Database for storing feats.
 * 
 * @author bjculkin
 *
 */
public class FeatDB {
	private static Connection dbConnection;

	private static PreparedStatement	lookupFeatNameStatement;
	private static PreparedStatement	createFeatStatement;
	private static PreparedStatement	listFeatStatement;

	private static PreparedStatement	lookupDependantTagsStatement;
	private static PreparedStatement	lookupDependantFeatsStatement;

	private static PreparedStatement	addDependantTagStatement;
	private static PreparedStatement	addDependantFeatStatement;

	private static PreparedStatement listFeatNamesStatement;

	private static PreparedStatement lookupFeatStatement;

	public static void initConnection(Connection conn) throws SQLException {
		dbConnection = conn;

		initStatements();
	}

	public static boolean addFeat(Feat featToAdd) throws SQLException {
		ensureConnection();

		String featName = featToAdd.getName();
		createFeatStatement.setString(1, featName);

		addDependantTagStatement.setString(1, featName);

		for (String tagName : featToAdd.getTags().toIterable()) {
			addDependantTagStatement.setString(2, tagName);
			addDependantTagStatement.addBatch();
		}

		for (int i : addDependantTagStatement.executeBatch()) {
			if (i < 1) {
				throw new IllegalStateException("Error: Tags not added succesfully.");
			}
		}

		addDependantFeatStatement.setString(1, featName);

		for (String prereqName : featToAdd.getFeatPrereqs().toIterable()) {
			addDependantFeatStatement.setString(2, prereqName);
			addDependantFeatStatement.addBatch();
		}

		for (int i : addDependantFeatStatement.executeBatch()) {
			if (i < 1) {
				throw new IllegalStateException("Error: Prerequisite feats not added succesfully.");
			}
		}

		createFeatStatement.setArray(2, dbConnection.createArrayOf("VARCHAR",
				featToAdd.getNonFeatPrereqs().toArray(new String[0])));

		createFeatStatement.setString(3, featToAdd.getDescription());
		createFeatStatement.setString(4, featToAdd.getFlavor());
		createFeatStatement.setString(5, featToAdd.getSource());

		boolean wasFeatCreated = createFeatStatement.executeUpdate() > 0;

		if (wasFeatCreated) {
			dbConnection.commit();
		} else {
			dbConnection.rollback();
		}

		return wasFeatCreated;
	}

	public static void disposeConnection() throws SQLException {
		lookupFeatStatement.close();

		listFeatNamesStatement.close();

		addDependantFeatStatement.close();
		addDependantTagStatement.close();

		lookupDependantFeatsStatement.close();
		lookupDependantTagsStatement.close();

		listFeatStatement.close();
		lookupFeatNameStatement.close();
		createFeatStatement.close();

	}

	public static boolean doesFeatExist(String featName) throws SQLException {
		ensureConnection();

		lookupFeatNameStatement.setString(1, featName);

		try (ResultSet featSet = lookupFeatNameStatement.executeQuery()) {
			if (featSet.next() == true) {
				return featName.equals(featSet.getString("name"));
			}

			return false;
		}
	}

	private static void initStatements() throws SQLException {
		createFeatStatement = dbConnection.prepareStatement("INSERT INTO feats "
				+ "(name, prereq_nonfeats, description, flavor, source)" + " VALUES (?, ?, ?, ?, ?)");
		lookupFeatNameStatement = dbConnection.prepareStatement("SELECT name FROM feats WHERE name = ?");
		listFeatStatement = dbConnection.prepareStatement(
				"SELECT name, prereq_nonfeats, description, flavor, source" + " FROM feats ");

		lookupDependantTagsStatement = dbConnection
				.prepareStatement("SELECT tag_name FROM feat_tags WHERE feat_name = ?");
		lookupDependantFeatsStatement = dbConnection
				.prepareStatement("SELECT prereq_name FROM feat_prereqs WHERE feat_name = ?");

		addDependantTagStatement = dbConnection
				.prepareStatement("INSERT INTO feat_tags " + " (feat_name, tag_name) VALUES (?, ?)");
		addDependantFeatStatement = dbConnection.prepareStatement(
				"INSERT INTO feat_prereqs " + " (feat_name, prereq_name) VALUES (?, ?)");

		listFeatNamesStatement = dbConnection.prepareStatement("SELECT name FROM feats ORDER BY name");

		lookupFeatStatement = dbConnection
				.prepareStatement("SELECT name, prereq_nonfeats, description, flavor, source"
						+ " FROM feats WHERE name = ?");
	}

	public static IList<Feat> listFeats() throws SQLException {
		ensureConnection();

		FunctionalList<Feat> featList = new FunctionalList<>();

		ResultSet featResultSet = listFeatStatement.executeQuery();

		while (featResultSet.next() != false) {
			featList.add(createFeatFromSet(featResultSet));
		}

		featResultSet.close();

		return featList;
	}

	private static Feat createFeatFromSet(ResultSet featResultSet) throws SQLException {
		FeatBuilder currentFeat = new FeatBuilder();

		String featName = featResultSet.getString("name");
		currentFeat.setName(featName);

		lookupDependantTagsStatement.setString(1, featName);
		ResultSet tagSet = lookupDependantTagsStatement.executeQuery();

		while (tagSet.next() == true) {
			currentFeat.addTag(tagSet.getString("tag_name"));
		}

		tagSet.close();

		lookupDependantFeatsStatement.setString(1, featName);
		ResultSet featSet = lookupDependantFeatsStatement.executeQuery();

		while (featSet.next() == true) {
			currentFeat.addFeatPrereq(featSet.getString("prereq_name"));
		}

		featSet.close();

		Array prereqNonFeatsArray = featResultSet.getArray("prereq_nonfeats");
		String[] prereqNonFeats = (String[]) prereqNonFeatsArray.getArray();

		for (String prereqNonFeat : prereqNonFeats) {
			currentFeat.addNonFeatPrereq(prereqNonFeat);
		}

		currentFeat.setDescription(featResultSet.getString("description"));

		currentFeat.setFlavor(featResultSet.getString("flavor"));
		currentFeat.setSource(featResultSet.getString("source"));

		Feat builtFeat = currentFeat.buildFeat();
		return builtFeat;
	}

	public static IList<String> listFeatNames() throws SQLException {
		FunctionalList<String> featNames = new FunctionalList<>();

		ResultSet nameSet = listFeatNamesStatement.executeQuery();

		while (nameSet.next() == true) {
			featNames.add(nameSet.getString("name"));
		}

		nameSet.close();

		return featNames;
	}

	private static void ensureConnection() {
		if (dbConnection == null) {
			throw new IllegalStateException("ERROR: No connection available");
		}
	}

	public static Feat lookupFeat(String featName) throws SQLException {
		lookupFeatStatement.setString(1, featName);

		try (ResultSet featSet = lookupFeatStatement.executeQuery()) {
			featSet.next();

			return createFeatFromSet(featSet);
		}
	}
}
