package bjc.dbeditor.data.creatures;

/**
 * The defenses for the creature.
 * 
 * @author Ben Culkin
 *
 */
public class CreatureDefenses {
	private int naturalArmor;
	private int spellResistance;
	private CreatureSaves saves;

	/**
	 * Get the creatures natural armor.
	 * 
	 * @return The natural armor of the creature.
	 */
	public int getNaturalArmor() {
		return naturalArmor;
	}

	/**
	 * Get the spell resistance of the creature.
	 * 
	 * @return The spell resistance of the creature.
	 */
	public int getSpellResistance() {
		return spellResistance;
	}

	/**
	 * Get the saves for the creature.
	 * 
	 * @return The saves for the creature.
	 */
	public CreatureSaves getSaves() {
		return saves;
	}

	/**
	 * Create the defenses for the creature.
	 * 
	 * @param naturalArmor
	 *                        The natural armor of the creature.
	 * @param spellResistance
	 *                        The spell-resistance of the creature.
	 * @param saves
	 *                        The saves for the creature.
	 */
	public CreatureDefenses(int naturalArmor, int spellResistance, CreatureSaves saves) {
		this.naturalArmor = naturalArmor;
		this.spellResistance = spellResistance;
		this.saves = saves;
	}
}