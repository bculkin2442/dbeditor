package bjc.dbeditor.data.creatures;

import bjc.utils.funcdata.FunctionalList;

public class CreatureOffensesBuilder {
	private CreatureAttack					attackStats;
	private String							attack;
	private String							fullAttack;
	private FunctionalList<CreatureAbility>	specialAttacks;
	private FunctionalList<CreatureAbility>	specialQualities;

	public CreatureOffensesBuilder() {
		specialAttacks = new FunctionalList<>();
		specialQualities = new FunctionalList<>();
	}

	public void setAttackStats(CreatureAttack attackStats) {
		this.attackStats = attackStats;
	}

	public void setAttack(String attack) {
		this.attack = attack;
	}

	public void setFullAttack(String fullAttack) {
		this.fullAttack = fullAttack;
	}

	public void addSpecialAttack(CreatureAbility specialAttack) {
		specialAttacks.add(specialAttack);
	}

	public void addSpecialQuality(CreatureAbility specialQuality) {
		specialQualities.add(specialQuality);
	}

	public CreatureOffenses buildOffenses() {
		return new CreatureOffenses(attackStats, attack, fullAttack,
				specialAttacks, specialQualities);
	}
}
