package bjc.dbeditor.data.creatures;

public class CreatureDefenses {
	private int		naturalArmor;
	private int		spellResistance;
	private CreatureSaves	saves;

	public int getNaturalArmor() {
		return naturalArmor;
	}

	public int getSpellResistance() {
		return spellResistance;
	}

	public CreatureSaves getSaves() {
		return saves;
	}

	public CreatureDefenses(int naturalArmor, int spellResistance, CreatureSaves saves) {
		this.naturalArmor = naturalArmor;
		this.spellResistance = spellResistance;
		this.saves = saves;
	}
}