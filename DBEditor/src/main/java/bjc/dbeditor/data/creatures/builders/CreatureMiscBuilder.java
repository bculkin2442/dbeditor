package bjc.dbeditor.data.creatures.builders;

import bjc.utils.funcdata.FunctionalList;

import bjc.dbeditor.data.creatures.CreatureMisc;
import bjc.dbeditor.data.creatures.CreatureSkill;

/**
 * Builder of misc. creature info
 */
public class CreatureMiscBuilder {
	private FunctionalList<CreatureSkill>	skills;
	private String				treasure;
	private String				alignment;
	private String				source;

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

	public void addSkill(CreatureSkill skill) {
		skills.add(skill);
	}

	public void setTreasure(String treasure) {
		this.treasure = treasure;
	}

	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}

	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * Create the object this builder represents
	 */
	public CreatureMisc buildMisc() {
		return new CreatureMisc(skills, treasure, alignment, source);
	}
}
