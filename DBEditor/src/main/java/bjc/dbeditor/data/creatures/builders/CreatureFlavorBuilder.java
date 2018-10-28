package bjc.dbeditor.data.creatures.builders;

import bjc.dbeditor.data.creatures.CreatureFlavor;

/**
 * Builder for creature flavor
 */
public class CreatureFlavorBuilder {
	private String	enviroment;
	private String	organization;
	private String	description;
	private String	notes;

	/**
	 * Create a new blank builder
	 */
	public CreatureFlavorBuilder() {

	}

	/*
	 * Bunch of setters
	 */

	public void setEnviroment(String enviroment) {
		this.enviroment = enviroment;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * Convert the builder into the object it represents
	 */
	public CreatureFlavor buildFlavor() {
		return new CreatureFlavor(enviroment, organization, description, notes);
	}
}
