package bjc.dbeditor.data.creatures;

/**
 * An ability for a creature.
 * 
 * @author bjculkin
 *
 */
public class CreatureAbility {
	/**
	 * The name of the ability.
	 */
	public String name;

	/**
	 * The type of the ability.
	 */
	public CreatureAbilityType type;

	/**
	 * The description of the ability.
	 */
	public String description;

	/**
	 * Create a new ability for a creature.
	 * 
	 * @param name
	 *                    The name of the ability.
	 * @param description
	 *                    The description of the ability.
	 * @param type
	 *                    The type of the ability.
	 */
	public CreatureAbility(String name, String description, CreatureAbilityType type) {
		this.name = name;
		this.type = type;
		this.description = description;
	}

	/**
	 * Create a new creature.
	 */
	public CreatureAbility() {
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

		CreatureAbility other = (CreatureAbility) obj;

		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}

		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}

		if (type != other.type) {
			return false;
		}

		return true;
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
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * Print the ability as a stat. block.
	 * 
	 * @return The ability as a stat block.
	 */
	public String toFullString() {
		return name + " " + type.toShortString() + ":\n\t" + description;
	}
}
