package bjc.dbeditor.data.creatures.builders;

import bjc.dbeditor.data.creatures.CreatureDefenses;
import bjc.dbeditor.data.creatures.CreatureSaves;

/**
 * Builder of Creature Defenses
 */
public class CreatureDefensesBuilder {
	private int naturalArmor;
	private int spellResistance;

	private CreatureSaves saves;

	/**
	 * Create a new defense builder
	 */
	public CreatureDefensesBuilder() {

	}

	/*
	 * A bunch of setters
	 */

	/**
	 * Set the natural armor for the creature
	 * 
	 * @param naturalArmor
	 *                     The natural armor for the creature.
	 */
	public void setNaturalArmor(int naturalArmor) {
		this.naturalArmor = naturalArmor;
	}

	/**
	 * Set the spell resistance for the creature.
	 * 
	 * @param spellResistance
	 *                        The spell-resistance for the creature.
	 */
	public void setSpellResistance(int spellResistance) {
		this.spellResistance = spellResistance;
	}

	/**
	 * Set the saves for the creature.
	 * 
	 * @param saves
	 *              The saves for the creature.
	 */
	public void setSaves(CreatureSaves saves) {
		this.saves = saves;
	}

	/**
	 * Create a defense set from this builder
	 * 
	 * @return The defense set this builder represents.
	 */
	public CreatureDefenses buildDefenses() {
		return new CreatureDefenses(naturalArmor, spellResistance, saves);
	}
}
