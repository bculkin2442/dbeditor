package bjc.dbeditor.data;

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

	public FeatTag(String name, String description) {
		this.name = name;
		this.description = description;
	}

	@Override
	public String toString() {
		return name;
	}

	public String toFullString() {
		return name + " Feats\n\tSpecial Rules: " + description;
	}
}
