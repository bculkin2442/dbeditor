package bjc.dbeditor.data.feat;

import bjc.utils.funcdata.FunctionalList;

/**
 * Builds feats piece by piece
 * 
 * @author ben
 *
 */
public class FeatBuilder {
	private String					name;
	private String					flavor;

	private FunctionalList<String>	tags;

	private FunctionalList<String>	featPrereqs;
	private FunctionalList<String>	nonFeatPrereqs;

	private String					description;
	private String					source;

	/**
	 * Create a new blank feat builder
	 */
	public FeatBuilder() {
		tags = new FunctionalList<>();

		featPrereqs = new FunctionalList<>();
		nonFeatPrereqs = new FunctionalList<>();
	}

	/**
	 * Add a feat as a prerequisite
	 * 
	 * @param feat
	 *            The feat to add as a prerequisite
	 */
	public void addFeatPrereq(String feat) {
		featPrereqs.add(feat);
	}

	/**
	 * Add a non-feat as a prerequisite
	 * 
	 * @param prereq
	 *            The prerequisite to add
	 */
	public void addNonFeatPrereq(String prereq) {
		nonFeatPrereqs.add(prereq);
	}

	/**
	 * Add a type tag
	 * 
	 * @param tag
	 *            The tag to add
	 */
	public void addTag(String tag) {
		tags.add(tag);
	}

	/**
	 * Convert this builder into a feat
	 * 
	 * @return The feat this builder represents
	 */
	public Feat buildFeat() {
		return new Feat(name, tags, featPrereqs, nonFeatPrereqs,
				description, flavor, source);
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFlavor(String flavor) {
		this.flavor = flavor;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSource(String source) {
		this.source = source;
	}
}