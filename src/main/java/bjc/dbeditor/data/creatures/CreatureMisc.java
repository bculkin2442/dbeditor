package bjc.dbeditor.data.creatures;

import bjc.utils.funcdata.FunctionalList;
import bjc.utils.funcdata.IList;

public class CreatureMisc {
	private FunctionalList<CreatureSkill>	skills;
	private String				treasure;
	private String				alignment;
	private String				source;

	public IList<CreatureSkill> getSkills() {
		return skills.clone();
	}

	public String getTreasure() {
		return treasure;
	}

	public String getAlignment() {
		return alignment;
	}

	public String getSource() {
		return source;
	}

	public CreatureMisc(FunctionalList<CreatureSkill> skills, String treasure, String alignment, String source) {
		this.skills = skills;
		this.treasure = treasure;
		this.alignment = alignment;
		this.source = source;
	}
}