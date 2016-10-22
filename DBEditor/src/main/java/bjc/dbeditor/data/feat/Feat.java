package bjc.dbeditor.data.feat;

import bjc.utils.funcdata.FunctionalList;
import bjc.utils.funcdata.IList;

/**
 * Represents a feat that can be taken
 * 
 * @author ben
 *
 */
public class Feat {
	private String					name;

	private FunctionalList<String>	tags;

	private FunctionalList<String>	featPrereqs;
	private FunctionalList<String>	nonFeatPrereqs;

	private String					flavor;
	private String					description;

	private String					source;

	/**
	 * Create a new feat
	 * 
	 * @param name
	 *            The name of the feat
	 * @param tags
	 *            The type tags of the feat
	 * @param featPrereqs
	 *            The required feat prerequisites
	 * @param nonFeatPrereqs
	 *            The required non-feat prerequisites
	 * @param description
	 *            The description of the feat
	 * @param flavor
	 *            The associated flavor text of the feat
	 * @param source
	 *            The source of the feat
	 */
	public Feat(String name, FunctionalList<String> tags,
			FunctionalList<String> featPrereqs,
			FunctionalList<String> nonFeatPrereqs, String description,
			String flavor, String source) {
		this.name = name;
		this.tags = tags;
		this.featPrereqs = featPrereqs;
		this.nonFeatPrereqs = nonFeatPrereqs;
		this.description = description;
		this.flavor = flavor;
		this.source = source;
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

		Feat other = (Feat) obj;

		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}

		return true;
	}

	public String getDescription() {
		return description;
	}

	public IList<String> getFeatPrereqs() {
		return featPrereqs.clone();
	}

	public String getFlavor() {
		return flavor;
	}

	public String getName() {
		return name;
	}

	public IList<String> getNonFeatPrereqs() {
		return nonFeatPrereqs.clone();
	}

	public String getSource() {
		return source;
	}

	public IList<String> getTags() {
		return tags.clone();
	}

	/**
	 * Check if a feat has a given feat as a prerequisite
	 * 
	 * @param featName
	 *            The feat to check if it is a prerequiste
	 * @return Whether or not the specified feat is a prerequisite for this
	 *         one
	 */
	public boolean hasFeatPrereq(String featName) {
		return featPrereqs.contains(featName);
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

	/**
	 * Check if a feat has a given type tag
	 * 
	 * @param tagName
	 *            The tag to check for
	 * @return Whether or not the feat has the specified tag
	 */
	public boolean hasTag(String tagName) {
		return tags.contains(tagName);
	}

	@Override
	public String toString() {
		StringBuilder featText = new StringBuilder(name);
		featText.append(" ");

		tags.forEach((tag) -> {
			featText.append("[ " + tag + "] ");
		});

		return featText.toString();
	}

	/**
	 * Get the full text of a feat
	 * 
	 * @return The full text of a feat
	 */
	public String toFullString() {
		StringBuilder featText = new StringBuilder(name);
		featText.append(" ");

		tags.forEach((tag) -> {
			featText.append("[ " + tag + "] ");
		});

		featText.append("\n\t" + flavor);

		if (featPrereqs.getSize() != 0 || nonFeatPrereqs.getSize() != 0) {
			featText.append("\nPrerequisites: ");
		}

		featPrereqs.forEach((prereq) -> {
			featText.append(prereq + ", ");
		});

		nonFeatPrereqs.forEach((prereq) -> {
			featText.append(prereq + ", ");
		});

		if (featPrereqs.getSize() != 0 || nonFeatPrereqs.getSize() != 0) {
			// Remove trailing comma and space
			featText.delete(featText.length() - 2, featText.length() - 1);
		}

		featText.append("\nBenefit: ");
		featText.append(description);

		featText.append("\n\tSource: " + source);
		return featText.toString();
	}
}