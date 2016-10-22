package bjc.dbeditor.data.creatures;

import java.math.BigDecimal;

import bjc.utils.funcdata.FunctionalList;
import bjc.utils.funcdata.IList;

public class Creature {
	private String									name;

	private CreatureSize							size;

	private CreatureType							type;
	private FunctionalList<String>					subtypes;

	private FunctionalList<CreatureHitdieRecord>	hitdice;

	private FunctionalList<CreatureSpeed>			speeds;

	private CreatureDefenses						defenses;

	private CreatureOffenses						offenses;

	private CreatureAbilityScores					abilityScores;

	private FunctionalList<String>					feats;

	private BigDecimal								challengeRating;
	private String									levelAdjustment;

	private String									advancement;

	private CreatureMisc							miscData;

	private CreatureFlavor							flavor;

	public String getName() {
		return name;
	}

	public CreatureSize getSize() {
		return size;
	}

	public IList<String> getFeats() {
		if (feats == null) {
			return null;
		}

		return feats.clone();
	}

	public CreatureType getType() {
		return type;
	}

	public IList<String> getSubtypes() {
		if (subtypes == null) {
			return null;
		}

		return subtypes.clone();
	}

	public IList<CreatureHitdieRecord> getHitdice() {
		return hitdice.clone();
	}

	public IList<CreatureSpeed> getSpeeds() {
		return speeds.clone();
	}

	public CreatureDefenses getDefenses() {
		return defenses;
	}

	public CreatureOffenses getOffenses() {
		return offenses;
	}

	public CreatureAbilityScores getAbilityScores() {
		return abilityScores;
	}

	public BigDecimal getChallengeRating() {
		return challengeRating;
	}

	public String getLevelAdjustment() {
		return levelAdjustment;
	}

	public String getAdvancement() {
		return advancement;
	}

	public CreatureMisc getMiscData() {
		return miscData;
	}

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

	public Creature(String name, CreatureSize size, CreatureType type,
			FunctionalList<String> subtypes,
			FunctionalList<CreatureHitdieRecord> hitdice,
			FunctionalList<CreatureSpeed> speeds,
			CreatureDefenses defenses, CreatureOffenses offenses,
			CreatureAbilityScores scores, BigDecimal challengeRating,
			String levelAdjustment, String advancement,
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
		creatureText
				.append("\nNatural Armor: " + defenses.getNaturalArmor());
		creatureText.append(
				"\nSpell Resistance: " + defenses.getSpellResistance());
		creatureText.append("\nSaving Throws");
		creatureText.append("\n\t" + defenses.getSaves());
		creatureText.append("\n------------------");
		creatureText.append("\nOffense");
		creatureText.append("\n" + offenses.getAttackStats());
		creatureText.append("\nAttack: " + offenses.getAttack());
		creatureText.append("\nFull Attack: " + offenses.getFullAttack());
		creatureText.append("\nSpecial Attacks:");
		for (CreatureAbility specialAttack : offenses.getSpecialAttacks()
				.toIterable()) {
			creatureText.append(" " + specialAttack + ",");
		}

		// Remove trailing comma
		creatureText.deleteCharAt(creatureText.length() - 1);

		creatureText.append("\nSpecial Qualities:");
		for (CreatureAbility specialQuality : offenses
				.getSpecialQualities().toIterable()) {
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
