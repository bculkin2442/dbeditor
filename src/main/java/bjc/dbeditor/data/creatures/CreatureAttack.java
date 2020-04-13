package bjc.dbeditor.data.creatures;

/**
 * Describes attack details for a creature.
 * @author Ben Culkin
 *
 */
public class CreatureAttack {
	private int	baseAttackBonus;
	private int	grappleMod;

	/**
	 * Create a new set of attack details.
	 * @param baseAttackBonus The BAB for a creature.
	 * @param grappleMod The grapple mod for a creature.
	 */
	public CreatureAttack(int baseAttackBonus, int grappleMod) {
		this.baseAttackBonus = baseAttackBonus;
		this.grappleMod = grappleMod;
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

		CreatureAttack other = (CreatureAttack) obj;

		if (baseAttackBonus != other.baseAttackBonus) {
			return false;
		} else if (grappleMod != other.grappleMod) {
			return false;
		}

		return true;
	}

	/**
	 * Get the BAB for the creature.
	 * @return The BAB for the creature.
	 */
	public int getBaseAttackBonus() {
		return baseAttackBonus;
	}

	/**
	 * Get the grapple modifier for the creature.
	 * @return The grapple modifier for the creature.
	 */
	public int getGrappleMod() {
		return grappleMod;
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
		result = prime * result + baseAttackBonus;
		result = prime * result + grappleMod;
		return result;
	}

	@Override
	public String toString() {
		return "Base Attack/Grapple: +" + baseAttackBonus + "/+" + grappleMod;
	}
}
