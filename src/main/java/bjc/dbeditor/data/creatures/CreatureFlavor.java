package bjc.dbeditor.data.creatures;

/**
 * Flavor information for the creature.
 * 
 * @author Ben Culkin
 *
 */
public class CreatureFlavor {
	private String enviroment;
	private String organization;
	private String description;
	private String notes;

	/**
	 * Set the environment the creature was found in.
	 * 
	 * @return The environment the creature was found in.
	 */
	public String getEnviroment() {
		return enviroment;
	}

	/**
	 * Set the organization information for this creature.
	 * 
	 * @return The organization information creature for the editing.
	 */
	public String getOrganization() {
		return organization;
	}

	/**
	 * Set the description for the creature.
	 * 
	 * @return The description for the creature.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the misc. notes for the creature.
	 * 
	 * @return The misc. notes for the creature.
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * Create a new set of creature flavor.
	 * 
	 * @param enviroment
	 *                     The environment the creature is found in.
	 * @param organization
	 *                     The organization info for the creature.
	 * @param description
	 *                     The description of the creature.
	 * @param notes
	 *                     The misc. notes for the creature.
	 */
	public CreatureFlavor(String enviroment, String organization, String description,
			String notes) {
		this.enviroment = enviroment;
		this.organization = organization;
		this.description = description;
		this.notes = notes;
	}
}