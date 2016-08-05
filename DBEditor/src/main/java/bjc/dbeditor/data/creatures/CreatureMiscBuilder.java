package bjc.dbeditor.data.creatures;

import bjc.utils.funcdata.FunctionalList;

public class CreatureMiscBuilder {
	private FunctionalList<CreatureSkill>	skills;
	private String							treasure;
	private String							alignment;
	private String							source;

	public CreatureMiscBuilder() {
		skills = new FunctionalList<>();
	}

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

	public CreatureMisc buildMisc() {
		return new CreatureMisc(skills, treasure, alignment, source);
	}
}
