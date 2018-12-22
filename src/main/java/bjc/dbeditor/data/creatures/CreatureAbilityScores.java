package bjc.dbeditor.data.creatures;

/**
 * Ability scores of a creature.
 * @author bjculkin
 *
 */
public class CreatureAbilityScores {
	private static String stringifyScore(int score) {
		if (score < 0) {
			// It's a non-score. This is different from a having a 0 in a
			// score
			return "-";
		}

		return Integer.toString(score);
	}

	private int	strength;
	private int	dexterity;
	private int	constitution;

	private int	intelligence;
	private int	wisdom;
	private int	charisma;

	/**
	 * Create a new set of ability scores.
	 * @param strength The strength of a creature.
	 * @param dexterity The dexterity of a creature.
	 * @param constitution The constitution of a creature.
	 * @param intelligence The intelligence of a creature.
	 * @param wisdom The wisdom of a creature.
	 * @param charisma The charisma of a creature.
	 */
	public CreatureAbilityScores(int strength, int dexterity, int constitution, int intelligence, int wisdom,
			int charisma) {
		this.strength = strength;
		this.dexterity = dexterity;
		this.constitution = constitution;
		this.intelligence = intelligence;
		this.wisdom = wisdom;
		this.charisma = charisma;
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

		CreatureAbilityScores other = (CreatureAbilityScores) obj;

		if (charisma != other.charisma) {
			return false;
		} else if (constitution != other.constitution) {
			return false;
		} else if (dexterity != other.dexterity) {
			return false;
		} else if (intelligence != other.intelligence) {
			return false;
		} else if (strength != other.strength) {
			return false;
		} else if (wisdom != other.wisdom) {
			return false;
		}

		return true;
	}

	public int getCharisma() {
		return charisma;
	}

	public int getConstitution() {
		return constitution;
	}

	public int getDexterity() {
		return dexterity;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public int getStrength() {
		return strength;
	}

	public int getWisdom() {
		return wisdom;
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
		result = prime * result + charisma;
		result = prime * result + constitution;
		result = prime * result + dexterity;
		result = prime * result + intelligence;
		result = prime * result + strength;
		result = prime * result + wisdom;
		return result;
	}

	@Override
	public String toString() {
		return "Str: " + stringifyScore(strength) + "\tDex: " + stringifyScore(dexterity) + "\tCon: "
				+ stringifyScore(constitution) + "\nInt: " + stringifyScore(intelligence) + "\tWis: "
				+ stringifyScore(wisdom) + "\tCha" + stringifyScore(charisma);
	}
}
