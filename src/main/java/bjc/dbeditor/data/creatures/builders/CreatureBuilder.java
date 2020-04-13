package bjc.dbeditor.data.creatures.builders;

import java.math.BigDecimal;

import bjc.dbeditor.data.creatures.Creature;
import bjc.dbeditor.data.creatures.CreatureAbilityScores;
import bjc.dbeditor.data.creatures.CreatureDefenses;
import bjc.dbeditor.data.creatures.CreatureFlavor;
import bjc.dbeditor.data.creatures.CreatureHitdieRecord;
import bjc.dbeditor.data.creatures.CreatureMisc;
import bjc.dbeditor.data.creatures.CreatureOffenses;
import bjc.dbeditor.data.creatures.CreatureSize;
import bjc.dbeditor.data.creatures.CreatureSpeed;
import bjc.dbeditor.data.creatures.CreatureType;
import bjc.funcdata.FunctionalList;

/**
 * Class for building instances of creatures
 */
public class CreatureBuilder {
	// Fields for storing builder data
	private String name;

	private CreatureSize size;

	private CreatureType type;
	private FunctionalList<String> subtypes;

	private FunctionalList<CreatureHitdieRecord> hitdice;

	private FunctionalList<CreatureSpeed> speeds;

	private CreatureDefenses defenses;

	private CreatureOffenses offenses;

	private CreatureAbilityScores abilityScores;

	private FunctionalList<String> feats;

	private BigDecimal challengeRating;
	private String levelAdjustment;

	private String advancement;

	private CreatureMisc miscData;

	private CreatureFlavor flavor;

	/**
	 * Create a new blank builder
	 */
	public CreatureBuilder() {
		// Initialize the lists as blank
		subtypes = new FunctionalList<>();
		hitdice = new FunctionalList<>();
		speeds = new FunctionalList<>();
		feats = new FunctionalList<>();
	}

	/*
	 * A bunch of self-explanatory builder functions
	 */

	/**
	 * Set the name for the creature.
	 * 
	 * @param name
	 *             The name for the creature.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Set the size for the creature.
	 * 
	 * @param size
	 *             The size for the creature.
	 */
	public void setSize(CreatureSize size) {
		this.size = size;
	}

	/**
	 * Set the type of the creature.
	 * 
	 * @param type
	 *             The type of the creature.
	 */
	public void setType(CreatureType type) {
		this.type = type;
	}

	/**
	 * Set the challenge rating for the creature.
	 * 
	 * @param challengeRating
	 *                        The challenge rating for the creature.
	 */
	public void setChallengeRating(BigDecimal challengeRating) {
		this.challengeRating = challengeRating;
	}

	/**
	 * Set the level adjustment for the creature.
	 * 
	 * @param levelAdjustment
	 *                        The level adjustment for the creature.
	 */
	public void setLevelAdjustment(String levelAdjustment) {
		this.levelAdjustment = levelAdjustment;
	}

	/**
	 * Set the advancement details for the creature.
	 * 
	 * @param advancement
	 *                    The advancement details for the creature.
	 */
	public void setAdvancement(String advancement) {
		this.advancement = advancement;
	}

	/**
	 * Set the defenses for the creature.
	 * 
	 * @param defenses
	 *                 The defenses for the creature.
	 */
	public void setDefenses(CreatureDefenses defenses) {
		this.defenses = defenses;
	}

	/**
	 * Set the offenses for the creature.
	 * 
	 * @param offenses
	 *                 The offenses for the creature.
	 */
	public void setOffenses(CreatureOffenses offenses) {
		this.offenses = offenses;
	}

	/**
	 * Set the ability scores for the creature.
	 * 
	 * @param scores
	 *               The ability scores for the creature.
	 */
	public void setAbilityScores(CreatureAbilityScores scores) {
		abilityScores = scores;
	}

	/**
	 * Set the misc. data for the creature.
	 * 
	 * @param miscData
	 *                 The misc. data for the creature.
	 */
	public void setMiscData(CreatureMisc miscData) {
		this.miscData = miscData;
	}

	/**
	 * Set the flavor for the creature.
	 * 
	 * @param flavor
	 *               The flavor for the creature.
	 */
	public void setFlavor(CreatureFlavor flavor) {
		this.flavor = flavor;
	}

	/**
	 * Add a subtype to the creature.
	 * 
	 * @param subtype
	 *                The subtype for the creature.
	 */
	public void addSubtype(String subtype) {
		subtypes.add(subtype);
	}

	/**
	 * Add hit-dice to the creature.
	 * 
	 * @param count
	 *              The number of this particular hit-dice.
	 * @param siz
	 *              The size of the hit-dice.
	 * @param typ
	 *              The descriptor for the hit-dice.
	 */
	public void addHitdie(int count, int siz, String typ) {
		hitdice.add(new CreatureHitdieRecord(count, siz, typ));
	}

	/**
	 * Add hit-dice to the creature.
	 * 
	 * @param hitdie
	 *               The hit-dice to add.
	 */
	public void addHitdie(CreatureHitdieRecord hitdie) {
		hitdice.add(hitdie);
	}

	/**
	 * Add a speed to the creature.
	 * 
	 * @param typ
	 *             The type of speed to add.
	 * @param rate
	 *             The rate in feet to travel at.
	 */
	public void addSpeed(String typ, int rate) {
		speeds.add(new CreatureSpeed(typ, rate));
	}

	/**
	 * Add a speed to the creature.
	 * 
	 * @param speed
	 *              The speed to add.
	 */
	public void addSpeed(CreatureSpeed speed) {
		speeds.add(speed);
	}

	/**
	 * Add a feat to the creature.
	 * 
	 * @param feat
	 *             The feat to add to the creature.
	 */
	public void addFeat(String feat) {
		feats.add(feat);
	}

	// IMPROVE methods to bulk-set/clear the 'add...' ones?
	// -- Ben Culkin, 4/12/2020
	/**
	 * Convert this builder into a creature
	 * 
	 * @return The creature this builder represents.
	 */
	public Creature buildCreature() {
		return new Creature(name, size, type, subtypes, hitdice, speeds, defenses,
				offenses, abilityScores, challengeRating, levelAdjustment, advancement,
				miscData, flavor);
	}
}
