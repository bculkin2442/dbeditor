package bjc.dbeditor.data.creatures.builders;

import bjc.dbeditor.data.creatures.CreatureAbility;
import bjc.dbeditor.data.creatures.CreatureAttack;
import bjc.dbeditor.data.creatures.CreatureOffenses;
import bjc.funcdata.FunctionalList;

/**
 * A builder of creature offenses
 */
public class CreatureOffensesBuilder {
	private CreatureAttack attackStats;
	private String attack;
	private String fullAttack;
	private FunctionalList<CreatureAbility> specialAttacks;
	private FunctionalList<CreatureAbility> specialQualities;

	/**
	 * Create a blank builder
	 */
	public CreatureOffensesBuilder() {
		// Initialize lists
		specialAttacks = new FunctionalList<>();
		specialQualities = new FunctionalList<>();
	}

	/*
	 * Bunch of setters
	 */

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

	/**
	 * Build the offenses this class represents
	 */
	public CreatureOffenses buildOffenses() {
		return new CreatureOffenses(attackStats, attack, fullAttack, specialAttacks,
				specialQualities);
	}
}
