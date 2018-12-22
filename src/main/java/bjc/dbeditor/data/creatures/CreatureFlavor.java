package bjc.dbeditor.data.creatures;

public class CreatureFlavor {
	private String	enviroment;
	private String	organization;
	private String	description;
	private String	notes;

	public String getEnviroment() {
		return enviroment;
	}

	public String getOrganization() {
		return organization;
	}

	public String getDescription() {
		return description;
	}

	public String getNotes() {
		return notes;
	}

	public CreatureFlavor(String enviroment, String organization, String description, String notes) {
		this.enviroment = enviroment;
		this.organization = organization;
		this.description = description;
		this.notes = notes;
	}
}