package bjc.dbeditor.data;

import bjc.utils.funcdata.FunctionalList;

public class FeatBuilder {
	private String					name;
	private String					flavor;

	private FunctionalList<String>	tags;

	private FunctionalList<String>	featPrereqs;
	private FunctionalList<String>	nonFeatPrereqs;

	private String					description;
	private String					source;

	public FeatBuilder() {
		tags = new FunctionalList<>();

		featPrereqs = new FunctionalList<>();
		nonFeatPrereqs = new FunctionalList<>();
	}

	public void addFeatPrereq(String feat) {
		featPrereqs.add(feat);
	}

	public void addNonFeatPrereq(String prereq) {
		nonFeatPrereqs.add(prereq);
	}

	public void addTag(String tag) {
		tags.add(tag);
	}

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
