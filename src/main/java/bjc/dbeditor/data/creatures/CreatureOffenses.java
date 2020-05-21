package bjc.dbeditor.data.creatures;

import bjc.funcdata.FunctionalList;
import bjc.funcdata.IList;

/**
 * Offensive details for the creature
 * 
 * @author Ben Culkin
 *
 */
public class CreatureOffenses {
	private CreatureAttack attackStats;
	private String attack;
	private String fullAttack;
	private FunctionalList<CreatureAbility> specialAttacks;
	private FunctionalList<CreatureAbility> specialQualities;

	/**
	 * Get the attack stats for the creature.
	 * 
	 * @return The attack stats for the creature.
	 */
	public CreatureAttack getAttackStats() {
		return attackStats;
	}

	/**
	 * Get the normal attack routine for the creature.
	 * 
	 * @return The normal attack routine for the creature.
	 */
	public String getAttack() {
		return attack;
	}

	/**
	 * Get the full-attack routine for the creature.
	 * 
	 * @return The full-attack routine for the creature.
	 */
	public String getFullAttack() {
		return fullAttack;
	}

	/**
	 * Get the special attacks of the creature.
	 * 
	 * @return The special attacks of the creature.
	 */
	public IList<CreatureAbility> getSpecialAttacks() {
		return specialAttacks.clone();
	}

	/**
	 * Get the special qualities of the creature.
	 * 
	 * @return The special qualities of the creature.
	 */
	public IList<CreatureAbility> getSpecialQualities() {
		return specialQualities.clone();
	}

	/**
	 * Create a new set of offensive details for the creature.
	 * 
	 * @param attackStats
	 *                         The attack stats for the creature.
	 * @param attack
	 *                         The attack routine for the creature.
	 * @param fullAttack
	 *                         The full-attack routine for the creature.
	 * @param specialAttacks
	 *                         The special attacks of the creature.
	 * @param specialQualities
	 *                         The special qualities of the creature.
	 */
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