package bjc.dbeditor.data.creatures.builders;

import bjc.dbeditor.data.creatures.CreatureFlavor;

/**
 * Builder for creature flavor
 */
public class CreatureFlavorBuilder {
	private String enviroment;
	private String organization;
	private String description;
	private String notes;

	/**
	 * Create a new blank builder
	 */
	public CreatureFlavorBuilder() {

	}

	/*
	 * Bunch of setters
	 */

	/**
	 * Set the environment of the creature.
	 * 
	 * @param enviroment
	 *                   The environment of the creature.
	 */
	public void setEnviroment(String enviroment) {
		this.enviroment = enviroment;
	}

	/**
	 * Set the organization info for the creature.
	 * 
	 * @param organization
	 *                     The organization info for the creature.
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}

	/**
	 * Set the description of the creature.
	 * 
	 * @param description
	 *                    The description of the creature.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Set the misc. notes for the creature.
	 * 
	 * @param notes
	 *              The misc. notes for the creature.
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * Convert the builder into the object it represents
	 * 
	 * @return The flavor info for the creature.
	 */
	public CreatureFlavor buildFlavor() {
		return new CreatureFlavor(enviroment, organization, description, notes);
	}
}
