package bjc.dbeditor.data.creatures.builders;

import bjc.dbeditor.data.creatures.CreatureDefenses;
import bjc.dbeditor.data.creatures.CreatureSaves;

/**
 * Builder of Creature Defenses
 */
public class CreatureDefensesBuilder {
	private int	naturalArmor;
	private int	spellResistance;

	private CreatureSaves saves;

	/**
	 * Create a new defense builder
	 */
	public CreatureDefensesBuilder() {

	}

	/*
	 * A bunch of setters
	 */

	public void setNaturalArmor(int naturalArmor) {
		this.naturalArmor = naturalArmor;
	}

	public void setSpellResistance(int spellResistance) {
		this.spellResistance = spellResistance;
	}

	public void setSaves(CreatureSaves saves) {
		this.saves = saves;
	}

	/**
	 * Create a defense set from this builder
	 */
	public CreatureDefenses buildDefenses() {
		return new CreatureDefenses(naturalArmor, spellResistance, saves);
	}
}
