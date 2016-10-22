package bjc.dbeditor.data.feat;

/**
 * A type tag for a feat
 * 
 * @author ben
 *
 */
public class FeatTag {
	private String	name;
	private String	description;

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (getClass() != obj.getClass()) {
			return false;
		}

		FeatTag other = (FeatTag) obj;

		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}

		return true;
	}

	/**
	 * Create a new feat type tag
	 * 
	 * @param name
	 *            The name of the tag
	 * @param description
	 *            The description of the tag
	 */
	public FeatTag(String name, String description) {
		this.name = name;
		this.description = description;
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * Get the full details of this tag
	 * 
	 * @return The full details of this tag
	 */
	public String toFullString() {
		return name + " Feats\n\tSpecial Rules: " + description;
	}
}