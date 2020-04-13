package bjc.dbeditor.db;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bjc.dbeditor.data.creatures.Creature;
import bjc.dbeditor.data.creatures.CreatureAbility;
import bjc.dbeditor.data.creatures.CreatureAbilityScores;
import bjc.dbeditor.data.creatures.CreatureAbilityType;
import bjc.dbeditor.data.creatures.CreatureAttack;
import bjc.dbeditor.data.creatures.CreatureDefenses;
import bjc.dbeditor.data.creatures.CreatureFlavor;
import bjc.dbeditor.data.creatures.CreatureHitdieRecord;
import bjc.dbeditor.data.creatures.CreatureMisc;
import bjc.dbeditor.data.creatures.CreatureOffenses;
import bjc.dbeditor.data.creatures.CreatureSaves;
import bjc.dbeditor.data.creatures.CreatureSize;
import bjc.dbeditor.data.creatures.CreatureSkill;
import bjc.dbeditor.data.creatures.CreatureSpeed;
import bjc.dbeditor.data.creatures.CreatureType;
import bjc.dbeditor.data.creatures.builders.CreatureBuilder;
import bjc.dbeditor.data.creatures.builders.CreatureDefensesBuilder;
import bjc.dbeditor.data.creatures.builders.CreatureFlavorBuilder;
import bjc.dbeditor.data.creatures.builders.CreatureMiscBuilder;
import bjc.dbeditor.data.creatures.builders.CreatureOffensesBuilder;
import bjc.funcdata.FunctionalList;
import bjc.funcdata.IList;

// NOTE Is there something better to do than throw around these SQLExceptions?
// -- Ben Culkin, 4/12/2020

// TODO Add support for updating creatures.
// TODO Add support for better querying of creatures
// -- Ben Culkin, 4/12/2020
/**
 * Database for storing creatures.
 * 
 * @author bjculkin
 *
 */
public class CreatureJDBCDB {
	private static Connection dbConn;

	private static PreparedStatement listCreatureNamesStatement;

	private static PreparedStatement lookupCreatureStatement;

	private static PreparedStatement	lookupDependantAbilitiesStatement;
	private static PreparedStatement	lookupDependantFeatsStatement;
	private static PreparedStatement	lookupDependantHitdiceStatement;
	private static PreparedStatement	lookupDependantSkillsStatement;
	private static PreparedStatement	lookupDependantSpeedsStatement;

	private static PreparedStatement addCreatureStatement;

	private static PreparedStatement	addDependantAbilitiesStatement;
	private static PreparedStatement	addDependantFeatsStatement;
	private static PreparedStatement	addDependantHitdiceStatement;
	private static PreparedStatement	addDependantSkillsStatement;
	private static PreparedStatement	addDependantSpeedsStatement;

	/**
	 * Set the database connection to get creatures from.
	 * @param conn The connection to get creatures from.
	 * @throws SQLException If something goes wrong during setup.
	 */
	public static void initConnection(Connection conn) throws SQLException {
		dbConn = conn;

		initStatements();
	}

	private static void initStatements() throws SQLException {
		listCreatureNamesStatement = dbConn.prepareStatement("SELECT name FROM creatures");

		lookupCreatureStatement = dbConn.prepareStatement(
				"SELECT name, size, type, subtypes, nat_armor, sr, attack, full_attack"
						+ ", enviroment, organization, cr, la, treasure, alignment,"
						+ " advancement, description, notes, source, saves,"
						+ " attack_stat, ability_scores FROM creatures WHERE name = ?");

		lookupDependantAbilitiesStatement = dbConn
				.prepareStatement("SELECT is_quality, ability_name, description, type"
						+ " FROM creature_ability WHERE creature_name = ?");
		lookupDependantFeatsStatement = dbConn
				.prepareStatement("SELECT feat_name FROM creature_feats WHERE creature_name = ?");
		lookupDependantHitdiceStatement = dbConn.prepareStatement(
				"SELECT count, size, class FROM creature_hitdice WHERE creature_name = ?");
		lookupDependantSkillsStatement = dbConn.prepareStatement(
				"SELECT skill_name, bonus FROM creature_skills WHERE creature_name = ?");
		lookupDependantSpeedsStatement = dbConn
				.prepareStatement("SELECT type, rate FROM creature_speeds WHERE creature_name = ?");

		addCreatureStatement = dbConn
				.prepareStatement("INSERT INTO creatures (name, size, type, subtypes, nat_armor,"
						+ " sr, attack, full_attack, enviroment, organization, cr,"
						+ " la, treasure, alignment, advancement, description, notes,"
						+ " source, saves, attack_stat, ability_scores) "
						+ "VALUES (?, CAST (? as creature_size), CAST (? as creature_type),"
						+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		addDependantAbilitiesStatement = dbConn
				.prepareStatement("INSERT INTO creature_ability (creature_name, is_quality,"
						+ " ability_name, description, type) VALUES (?, ?, ?, ?, "
						+ "CAST (? as creature_ability_type))");
		addDependantFeatsStatement = dbConn.prepareStatement(
				"INSERT INTO creature_feats (creature_name, feat_name) VALUES (?, ?)");
		addDependantHitdiceStatement = dbConn.prepareStatement(
				"INSERT INTO creature_hitdice (creature_name, count, size, class) VALUES (?, ?, ?, ?)");
		addDependantSkillsStatement = dbConn.prepareStatement(
				"INSERT INTO creature_skills (creature_name, skill_name, bonus) VALUES (?, ?, ?)");
		addDependantSpeedsStatement = dbConn.prepareStatement(
				"INSERT INTO creature_speeds (creature_name, type, rate) VALUES (?, ?, ?)");
	}

	/**
	 * Dispose of all the resources related to the connection, though not the connection itself.
	 * @throws SQLException If something goes wrong during disposal.
	 */
	public static void disposeConnection() throws SQLException {
		addDependantSpeedsStatement.close();
		addDependantSkillsStatement.close();
		addDependantHitdiceStatement.close();
		addDependantFeatsStatement.close();
		addDependantAbilitiesStatement.close();

		addCreatureStatement.close();

		lookupDependantSpeedsStatement.close();
		lookupDependantSkillsStatement.close();
		lookupDependantHitdiceStatement.close();
		lookupDependantFeatsStatement.close();
		lookupDependantAbilitiesStatement.close();

		lookupCreatureStatement.close();

		listCreatureNamesStatement.close();
	}

	/**
	 * Get a list of all of the creature names.
	 * @return A list of all of the named creatures.
	 * @throws SQLException If something goes wrong.
	 */
	public static IList<String> listCreatureNames() throws SQLException {
		FunctionalList<String> names = new FunctionalList<>();

		try (ResultSet nameSet = listCreatureNamesStatement.executeQuery()) {
			while (nameSet.next() == true) {
				names.add(nameSet.getString("name"));
			}
		}

		return names;
	}

	/**
	 * Look up a creature by name.
	 * @param name The name of the creature.
	 * @return The creature with that name.
	 * @throws SQLException If something goes wrong.
	 */
	public static Creature lookupCreatureByName(String name) throws SQLException {
		CreatureBuilder creatureBuilder = new CreatureBuilder();
		creatureBuilder.setName(name);

		lookupCreatureStatement.setString(1, name);

		try (ResultSet creatureSet = lookupCreatureStatement.executeQuery()) {
			creatureSet.next();

			creatureBuilder.setSize(CreatureSize.valueOf(creatureSet.getString("size").toUpperCase()));

			creatureBuilder.setType(CreatureType.valueOf(creatureSet.getString("type").toUpperCase()));

			try (ResultSet subtypeSet = creatureSet.getArray("subtypes").getResultSet()) {
				while (subtypeSet.next() == true) {
					creatureBuilder.addSubtype(subtypeSet.getString(2));
				}
			}

			CreatureDefensesBuilder defenseBuilder = new CreatureDefensesBuilder();

			defenseBuilder.setNaturalArmor(creatureSet.getInt("nat_armor"));
			defenseBuilder.setSpellResistance(creatureSet.getInt("sr"));

			Integer[] saveArray = (Integer[]) creatureSet.getArray("saves").getArray();

			defenseBuilder.setSaves(new CreatureSaves(saveArray[0], saveArray[1], saveArray[2]));

			creatureBuilder.setDefenses(defenseBuilder.buildDefenses());

			CreatureOffensesBuilder offenseBuilder = new CreatureOffensesBuilder();

			Integer[] attackArray = (Integer[]) creatureSet.getArray("attack_stat").getArray();

			offenseBuilder.setAttackStats(new CreatureAttack(attackArray[0], attackArray[1]));
			offenseBuilder.setAttack(creatureSet.getString("attack"));
			offenseBuilder.setFullAttack(creatureSet.getString("full_attack"));

			lookupDependantAbilitiesStatement.setString(1, name);

			try (ResultSet abilitySet = lookupDependantAbilitiesStatement.executeQuery()) {
				while (abilitySet.next()) {
					CreatureAbility ability = new CreatureAbility(
							abilitySet.getString("ability_name"),
							abilitySet.getString("description"),
							CreatureAbilityType.valueOf(
									abilitySet.getString("type").toUpperCase()));

					if (abilitySet.getBoolean("is_quality")) {
						offenseBuilder.addSpecialQuality(ability);
					} else {
						offenseBuilder.addSpecialAttack(ability);
					}
				}
			}

			creatureBuilder.setOffenses(offenseBuilder.buildOffenses());

			CreatureMiscBuilder miscBuilder = new CreatureMiscBuilder();
			CreatureFlavorBuilder flavorBuilder = new CreatureFlavorBuilder();

			lookupDependantFeatsStatement.setString(1, name);
			try (ResultSet featSet = lookupDependantFeatsStatement.executeQuery()) {
				while (featSet.next() == true) {
					creatureBuilder.addFeat(featSet.getString("feat_name"));
				}
			}

			lookupDependantHitdiceStatement.setString(1, name);
			try (ResultSet hitdiceSet = lookupDependantHitdiceStatement.executeQuery()) {
				while (hitdiceSet.next() == true) {
					creatureBuilder.addHitdie(hitdiceSet.getInt("count"), hitdiceSet.getInt("size"),
							hitdiceSet.getString("class"));
				}
			}

			lookupDependantSkillsStatement.setString(1, name);
			try (ResultSet skillSet = lookupDependantSkillsStatement.executeQuery()) {
				while (skillSet.next() == true) {
					miscBuilder.addSkill(new CreatureSkill(skillSet.getString("skill_name"),
							skillSet.getInt("bonus")));
				}
			}

			lookupDependantSpeedsStatement.setString(1, name);
			try (ResultSet speedSet = lookupDependantSpeedsStatement.executeQuery()) {
				while (speedSet.next() == true) {
					creatureBuilder.addSpeed(speedSet.getString("type"), speedSet.getInt("rate"));
				}
			}

			flavorBuilder.setEnviroment(creatureSet.getString("enviroment"));
			flavorBuilder.setOrganization(creatureSet.getString("organization"));

			creatureBuilder.setChallengeRating(creatureSet.getBigDecimal("cr"));
			creatureBuilder.setLevelAdjustment(creatureSet.getString("la"));

			miscBuilder.setTreasure(creatureSet.getString("treasure"));

			miscBuilder.setAlignment(creatureSet.getString("alignment"));

			creatureBuilder.setAdvancement(creatureSet.getString("advancement"));

			flavorBuilder.setDescription(creatureSet.getString("description"));
			flavorBuilder.setNotes(creatureSet.getString("notes"));

			miscBuilder.setSource(creatureSet.getString("source"));
			creatureBuilder.setMiscData(miscBuilder.buildMisc());
			creatureBuilder.setFlavor(flavorBuilder.buildFlavor());
		}

		return creatureBuilder.buildCreature();
	}

	/**
	 * Add a creature to the DB.
	 * @param add The creature to add.
	 * @return Whether or not the creature was added succesfully.
	 * @throws SQLException If something goes wrong.
	 */
	public static boolean addCreature(Creature add) throws SQLException {
		String name = add.getName();

		for (CreatureAbility specialAttack : add.getOffenses().getSpecialAttacks().toIterable()) {
			addDependantAbilitiesStatement.setString(1, name);

			// This is true for special qualities only
			addDependantAbilitiesStatement.setBoolean(2, false);

			addDependantAbilitiesStatement.setString(3, specialAttack.name);
			addDependantAbilitiesStatement.setString(4, specialAttack.description);
			addDependantAbilitiesStatement.setString(5, specialAttack.type.toString().toLowerCase());

			addDependantAbilitiesStatement.addBatch();
		}

		for (CreatureAbility specialQuality : add.getOffenses().getSpecialQualities().toIterable()) {
			addDependantAbilitiesStatement.setString(1, name);

			// This is true for special qualities only
			addDependantAbilitiesStatement.setBoolean(2, true);

			addDependantAbilitiesStatement.setString(3, specialQuality.name);
			addDependantAbilitiesStatement.setString(4, specialQuality.description);
			addDependantAbilitiesStatement.setString(5, specialQuality.type.toString().toLowerCase());

			addDependantAbilitiesStatement.addBatch();
		}

		try {
			for (int res : addDependantAbilitiesStatement.executeBatch()) {
				if (res < 1) {
					System.out.println("Error: could not save ability to database");

					dbConn.rollback();
					return false;
				}
			}
		} catch (BatchUpdateException buex) {
			System.out.println("\n\nBatch error: ");
			buex.getNextException().printStackTrace();
		}

		if (add.getFeats() != null) {
			for (String feat : add.getFeats().toIterable()) {
				addDependantFeatsStatement.setString(1, name);

				addDependantFeatsStatement.setString(2, feat);

				addDependantFeatsStatement.addBatch();
			}

			for (int res : addDependantFeatsStatement.executeBatch()) {
				if (res < 1) {
					System.out.println("Error: could not save feat to database");

					dbConn.rollback();
					return false;
				}
			}
		}

		for (CreatureHitdieRecord hitdie : add.getHitdice().toIterable()) {
			addDependantHitdiceStatement.setString(1, name);

			addDependantHitdiceStatement.setInt(2, hitdie.getCount());
			addDependantHitdiceStatement.setInt(3, hitdie.getSize());
			addDependantHitdiceStatement.setString(4, hitdie.getType());

			addDependantHitdiceStatement.addBatch();
		}

		try {
			for (int res : addDependantHitdiceStatement.executeBatch()) {
				if (res < 1) {
					System.out.println("Error: could not save hitdie to database");

					dbConn.rollback();
					return false;
				}
			}
		} catch (BatchUpdateException buex) {
			System.out.println("\n\nBatch error: ");
			buex.getNextException().printStackTrace();
		}

		for (CreatureSkill skill : add.getMiscData().getSkills().toIterable()) {
			addDependantSkillsStatement.setString(1, name);

			addDependantSkillsStatement.setString(2, skill.getSkillName());
			addDependantSkillsStatement.setInt(3, skill.getSkillBonus());

			addDependantSkillsStatement.addBatch();
		}

		for (int res : addDependantSkillsStatement.executeBatch()) {
			if (res < 1) {
				System.out.println("Error: could not save skill to database");

				dbConn.rollback();
				return false;
			}
		}

		for (CreatureSpeed speed : add.getSpeeds().toIterable()) {
			addDependantSpeedsStatement.setString(1, name);

			addDependantSpeedsStatement.setString(2, speed.getType());
			addDependantSpeedsStatement.setInt(3, speed.getRate());

			addDependantSpeedsStatement.addBatch();
		}

		for (int res : addDependantSpeedsStatement.executeBatch()) {
			if (res < 1) {
				System.out.println("Error: could not save speed to database");

				dbConn.rollback();
				return false;
			}
		}

		addCreatureStatement.setString(1, name);

		addCreatureStatement.setString(2, add.getSize().toString());
		addCreatureStatement.setString(3, add.getType().toString());

		addCreatureStatement.setArray(4,
				dbConn.createArrayOf("varchar", add.getSubtypes().toArray(new String[0])));

		CreatureDefenses defenses = add.getDefenses();

		addCreatureStatement.setInt(5, defenses.getNaturalArmor());
		addCreatureStatement.setInt(6, defenses.getSpellResistance());

		CreatureOffenses offenses = add.getOffenses();

		addCreatureStatement.setString(7, offenses.getAttack());
		addCreatureStatement.setString(8, offenses.getFullAttack());

		CreatureFlavor flavor = add.getFlavor();
		CreatureMisc misc = add.getMiscData();

		addCreatureStatement.setString(9, flavor.getEnviroment());
		addCreatureStatement.setString(10, flavor.getOrganization());

		addCreatureStatement.setBigDecimal(11, add.getChallengeRating());
		addCreatureStatement.setString(12, add.getLevelAdjustment());

		addCreatureStatement.setString(13, misc.getAlignment());
		addCreatureStatement.setString(14, misc.getTreasure());

		addCreatureStatement.setString(15, add.getAdvancement());

		addCreatureStatement.setString(16, flavor.getDescription());
		addCreatureStatement.setString(17, flavor.getNotes());

		addCreatureStatement.setString(18, misc.getSource());

		CreatureSaves saves = defenses.getSaves();

		addCreatureStatement.setArray(19, dbConn.createArrayOf("integer",
				new Integer[] { saves.getFortitude(), saves.getReflex(), saves.getWill() }));

		CreatureAttack attack = offenses.getAttackStats();

		addCreatureStatement.setArray(20, dbConn.createArrayOf("integer",
				new Integer[] { attack.getBaseAttackBonus(), attack.getGrappleMod() }));

		CreatureAbilityScores scores = add.getAbilityScores();

		addCreatureStatement.setArray(21,
				dbConn.createArrayOf("integer",
						new Integer[] { scores.getStrength(), scores.getDexterity(),
								scores.getConstitution(), scores.getIntelligence(),
								scores.getWisdom(), scores.getCharisma() }));

		if (addCreatureStatement.executeUpdate() > 0) {
			dbConn.commit();

			return true;
		}

		System.out.println("Error: Could not save creature to database.");

		dbConn.rollback();

		return false;
	}
}
