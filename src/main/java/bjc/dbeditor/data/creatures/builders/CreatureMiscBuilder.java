package bjc.dbeditor.data.creatures.builders;

import bjc.dbeditor.data.creatures.CreatureMisc;
import bjc.dbeditor.data.creatures.CreatureSkill;
import bjc.funcdata.FunctionalList;

/**
 * Builder of misc. creature info
 */
public class CreatureMiscBuilder {
	private FunctionalList<CreatureSkill> skills;
	private String treasure;
	private String alignment;
	private String source;

	/**
	 * Create a blank builder
	 */
	public CreatureMiscBuilder() {
		// Initialize list
		skills = new FunctionalList<>();
	}

	/*
	 * Bunch of setters
	 */

	/**
	 * Add a skill to the creature
	 * 
	 * @param skill
	 *              The skill to add.
	 */
	public void addSkill(CreatureSkill skill) {
		skills.add(skill);
	}

	// IMPROVE add batch-set/clear for skils
	// -- Ben Culkin, 4/12/2020

	/**
	 * Set the treasure data for the creature.
	 * 
	 * @param treasure
	 *                 The treasure data for the creature.
	 */
	public void setTreasure(String treasure) {
		this.treasure = treasure;
	}

	/**
	 * Set the alignment info for the creature.
	 * 
	 * @param alignment
	 *                  The alignment info for the creature.
	 */
	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}

	/**
	 * Set the source data for the creature.
	 * 
	 * @param source
	 *               The source data for the creature.
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * Create the object this builder represents.
	 * 
	 * @return The misc. data represented by the builder.
	 */
	public CreatureMisc buildMisc() {
		return new CreatureMisc(skills, treasure, alignment, source);
	}
}
