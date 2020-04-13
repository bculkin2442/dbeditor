package bjc.dbeditor.data.creatures;

import java.math.BigDecimal;

import bjc.funcdata.FunctionalList;
import bjc.funcdata.IList;

/**
 * A creature.
 * 
 * @author bjculkin
 *
 */
public class Creature {
	// IMPROVE Add some sort of notion of monster genus/species?
	// Some way to say: all of these are the same sort of thing?
	// -- Ben Culkin, 4/12/2020
	private String name;

	// IMPROVE Maybe store face/reach as well?
	// 	Since it's not necessarily implied by size?
	// -- Ben Culkin, 4/12/2020
	private CreatureSize size;

	// IMPROVE Should these be stored separately?
	// So as to note the rules associated with that type/subtype?
	// -- Ben Culkin, 4/12/2020
	private CreatureType type;
	private FunctionalList<String> subtypes;

	private FunctionalList<CreatureHitdieRecord> hitdice;

	private FunctionalList<CreatureSpeed> speeds;

	private CreatureDefenses defenses;

	private CreatureOffenses offenses;

	private CreatureAbilityScores abilityScores;

	// IMPROVE Should we note if the feature is a bonus feat?
	// -- Ben Culkin, 4/12/2020
	private FunctionalList<String> feats;

	private BigDecimal challengeRating;
	private String levelAdjustment;

	private String advancement;

	private CreatureMisc miscData;

	private CreatureFlavor flavor;

	/**
	 * Get the name of the creature.
	 * 
	 * @return The name of the creature.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the size of the creature.
	 * 
	 * @return The size of the creature.
	 */
	public CreatureSize getSize() {
		return size;
	}

	/**
	 * Get the creatures feats.
	 * 
	 * @return The feats the creature has.
	 */
	public IList<String> getFeats() {
		if (feats == null) {
			return null;
		}

		return feats.clone();
	}

	/**
	 * Get the type of the creature.
	 * 
	 * @return The type of the creature.
	 */
	public CreatureType getType() {
		return type;
	}

	/**
	 * Get the subtypes of the creature.
	 * 
	 * @return The subtypes of the creature.
	 */
	public IList<String> getSubtypes() {
		if (subtypes == null) {
			return null;
		}

		return subtypes.clone();
	}

	/**
	 * Get the hitdice of the creature.
	 * 
	 * @return The hitdice of the creature.
	 */
	public IList<CreatureHitdieRecord> getHitdice() {
		return hitdice.clone();
	}

	/**
	 * Get the creatures speeds.
	 * 
	 * @return The speeds of the creature.
	 */
	public IList<CreatureSpeed> getSpeeds() {
		return speeds.clone();
	}

	/**
	 * Get the creatures defenses.
	 * 
	 * @return The defenses of the creature.
	 */
	public CreatureDefenses getDefenses() {
		return defenses;
	}

	/**
	 * Get the offenses of the creature.
	 * 
	 * @return The offenses of the creature.
	 */
	public CreatureOffenses getOffenses() {
		return offenses;
	}

	/**
	 * Get the creatures ability scores.
	 * 
	 * @return The ability scores of the creature.
	 */
	public CreatureAbilityScores getAbilityScores() {
		return abilityScores;
	}

	/**
	 * Get the creatures challenge rating.
	 * 
	 * @return The challenge rating of the creature.
	 */
	public BigDecimal getChallengeRating() {
		return challengeRating;
	}

	/**
	 * Get the creatures level adjustment.
	 * 
	 * @return The level adjustment of the creature.
	 */
	public String getLevelAdjustment() {
		return levelAdjustment;
	}

	/**
	 * Get the advancement details for the creature.
	 * 
	 * @return The advancement details for the creature.
	 */
	public String getAdvancement() {
		return advancement;
	}

	/**
	 * Get the misc. data for the creature.
	 * 
	 * @return The misc. data for the creature.
	 */
	public CreatureMisc getMiscData() {
		return miscData;
	}

	/**
	 * Get the flavor for the creature.
	 * 
	 * @return The flavor for the creature.
	 */
	public CreatureFlavor getFlavor() {
		return flavor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (getClass() != obj.getClass()) {
			return false;
		}

		Creature other = (Creature) obj;

		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}

		return true;
	}

	/**
	 * Create a new creature.
	 * 
	 * @param name
	 *                        The name of the creature.
	 * @param size
	 *                        The size of the creature.
	 * @param type
	 *                        The type of the creature.
	 * @param subtypes
	 *                        The subtypes of the creature.
	 * @param hitdice
	 *                        The hitdice of the creature.
	 * @param speeds
	 *                        The speeds of the creature.
	 * @param defenses
	 *                        The defenses of the creature.
	 * @param offenses
	 *                        The offenses of the creature.
	 * @param scores
	 *                        The ability scores of the creature.
	 * @param challengeRating
	 *                        The challenge rating of the creature.
	 * @param levelAdjustment
	 *                        The level adjustment of the creature.
	 * @param advancement
	 *                        The advancement details for the creature.
	 * @param miscData
	 *                        The misc. data for the creature.
	 * @param flavor
	 *                        The flavor for the creature.
	 */
	public Creature(String name, CreatureSize size, CreatureType type,
			FunctionalList<String> subtypes, FunctionalList<CreatureHitdieRecord> hitdice,
			FunctionalList<CreatureSpeed> speeds, CreatureDefenses defenses,
			CreatureOffenses offenses, CreatureAbilityScores scores,
			BigDecimal challengeRating, String levelAdjustment, String advancement,
			CreatureMisc miscData, CreatureFlavor flavor) {
		this.name = name;
		this.size = size;
		this.type = type;
		this.subtypes = subtypes;
		this.hitdice = hitdice;
		this.speeds = speeds;
		this.defenses = defenses;
		this.offenses = offenses;
		this.challengeRating = challengeRating;
		this.levelAdjustment = levelAdjustment;
		this.advancement = advancement;
		this.miscData = miscData;
		this.flavor = flavor;
		this.abilityScores = scores;
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * Convert the creature into a stat-block string.
	 * 
	 * @return The creature as a stat-block.
	 */
	public String toFullString() {
		StringBuilder creatureText = new StringBuilder(name);

		creatureText.append("\tCR " + challengeRating);

		creatureText.append("\n" + size + " " + type + " (");

		for (String subtype : subtypes.toIterable()) {
			creatureText.append(" " + subtype + ",");
		}

		// Remove trailing comma
		creatureText.deleteCharAt(creatureText.length() - 1);

		creatureText.append("\nHD:");

		for (CreatureHitdieRecord hitdie : hitdice.toIterable()) {
			creatureText.append(" " + hitdie.toString() + " +");
		}

		// Remove trailing plus and space
		creatureText.deleteCharAt(creatureText.length() - 1);
		creatureText.deleteCharAt(creatureText.length() - 1);

		creatureText.append("\nSpeed:");

		for (CreatureSpeed speed : speeds.toIterable()) {
			creatureText.append(" " + speed.toString() + ",");
		}

		// Remove trailing comma
		creatureText.deleteCharAt(creatureText.length() - 1);

		creatureText.append("\n------------------");
		creatureText.append("\nDefenses");
		creatureText.append("\nNatural Armor: " + defenses.getNaturalArmor());
		creatureText.append("\nSpell Resistance: " + defenses.getSpellResistance());
		creatureText.append("\nSaving Throws");
		creatureText.append("\n\t" + defenses.getSaves());
		creatureText.append("\n------------------");
		creatureText.append("\nOffense");
		creatureText.append("\n" + offenses.getAttackStats());
		creatureText.append("\nAttack: " + offenses.getAttack());
		creatureText.append("\nFull Attack: " + offenses.getFullAttack());
		creatureText.append("\nSpecial Attacks:");
		for (CreatureAbility specialAttack : offenses.getSpecialAttacks().toIterable()) {
			creatureText.append(" " + specialAttack + ",");
		}

		// Remove trailing comma
		creatureText.deleteCharAt(creatureText.length() - 1);

		creatureText.append("\nSpecial Qualities:");
		for (CreatureAbility specialQuality : offenses.getSpecialQualities()
				.toIterable()) {
			creatureText.append(" " + specialQuality + ",");
		}

		// Remove trailing comma
		creatureText.deleteCharAt(creatureText.length() - 1);

		creatureText.append("\n------------------");
		creatureText.append(abilityScores.toString());
		creatureText.append("\n------------------");
		creatureText.append("Skills:");
		for (CreatureSkill skill : miscData.getSkills().toIterable()) {
			creatureText.append(" " + skill + ",");
		}

		// Remove trailing comma
		creatureText.deleteCharAt(creatureText.length() - 1);

		creatureText.append("\nFeats:");

		for (String feat : feats.toIterable()) {
			creatureText.append(" " + feat + ",");
		}

		// Remove trailing comma
		creatureText.deleteCharAt(creatureText.length() - 1);

		creatureText.append("\n\nEnviroment: " + flavor.getEnviroment());
		creatureText.append("\nOrganization: " + flavor.getOrganization());
		creatureText.append("\nAlignment: " + miscData.getAlignment());
		creatureText.append("\nTreasure: " + miscData.getTreasure());
		creatureText.append("\nLevel Adjustment: " + levelAdjustment);
		creatureText.append("\nAdvancement: " + advancement);

		creatureText.append("\n\n" + flavor.getDescription());
		creatureText.append("\n\n" + flavor.getNotes());

		creatureText.append("\n\nSource: " + miscData.getSource());

		return creatureText.toString();
	}
}
