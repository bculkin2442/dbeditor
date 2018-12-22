package bjc.dbeditor.data.creatures;

import bjc.utils.funcdata.FunctionalList;
import bjc.utils.funcdata.IList;

public class CreatureOffenses {
	private CreatureAttack			attackStats;
	private String				attack;
	private String				fullAttack;
	private FunctionalList<CreatureAbility>	specialAttacks;
	private FunctionalList<CreatureAbility>	specialQualities;

	public CreatureAttack getAttackStats() {
		return attackStats;
	}

	public String getAttack() {
		return attack;
	}

	public String getFullAttack() {
		return fullAttack;
	}

	public IList<CreatureAbility> getSpecialAttacks() {
		return specialAttacks.clone();
	}

	public IList<CreatureAbility> getSpecialQualities() {
		return specialQualities.clone();
	}

	public CreatureOffenses(CreatureAttack attackStats, String attack, String fullAttack,
			FunctionalList<CreatureAbility> specialAttacks,
			FunctionalList<CreatureAbility> specialQualities) {
		this.attackStats = attackStats;
		this.attack = attack;
		this.fullAttack = fullAttack;
		this.specialAttacks = specialAttacks;
		this.specialQualities = specialQualities;
	}
}