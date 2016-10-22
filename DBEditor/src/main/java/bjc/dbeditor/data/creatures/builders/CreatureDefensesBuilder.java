package bjc.dbeditor.data.creatures.builders;

import bjc.dbeditor.data.creatures.CreatureDefenses;
import bjc.dbeditor.data.creatures.CreatureSaves;

public class CreatureDefensesBuilder {
	private int				naturalArmor;
	private int				spellResistance;

	private CreatureSaves	saves;

	public CreatureDefensesBuilder() {

	}

	public void setNaturalArmor(int naturalArmor) {
		this.naturalArmor = naturalArmor;
	}

	public void setSpellResistance(int spellResistance) {
		this.spellResistance = spellResistance;
	}

	public void setSaves(CreatureSaves saves) {
		this.saves = saves;
	}

	public CreatureDefenses buildDefenses() {
		return new CreatureDefenses(naturalArmor, spellResistance, saves);
	}
}
