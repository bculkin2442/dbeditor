package bjc.dbeditor.data.creatures;

import bjc.funcdata.FunctionalList;
import bjc.funcdata.IList;

/**
 * Misc. data about a creature.
 * 
 * @author Ben Culkin
 *
 */
public class CreatureMisc {
	private FunctionalList<CreatureSkill> skills;
	private String treasure;
	private String alignment;
	private String source;

	/**
	 * The skills that belong to the creature.
	 * 
	 * @return The skills that belong to the creature.
	 */
	public IList<CreatureSkill> getSkills() {
		return skills.clone();
	}

	/**
	 * Get the treasure info for the creature.
	 * 
	 * @return The treasure info for the creature.
	 */
	public String getTreasure() {
		return treasure;
	}

	// NOTE Store a 'default' alignment value? for querying and stuff?
	// -- Ben Culkin, 4/12/2020
	/**
	 * Get the alignment data for the creature.
	 * 
	 * @return The alignment data for the creature.
	 */
	public String getAlignment() {
		return alignment;
	}

	/**
	 * Get the source for this creature.
	 * 
	 * This is where the creature was originally published, or where the info for it
	 * was found.
	 * 
	 * @return The source data for the creature.
	 */
	public String getSource() {
		return source;
	}

	/**
	 * Construct misc. info for the creature.
	 * 
	 * @param skills
	 *                  The skills for the creature.
	 * @param treasure
	 *                  The treasure for the creature.
	 * @param alignment
	 *                  The alignment data for the creature.
	 * @param source
	 *                  The source of the creature.
	 */
	public CreatureMisc(FunctionalList<CreatureSkill> skills, String treasure,
			String alignment, String source) {
		this.skills = skills;
		this.treasure = treasure;
		this.alignment = alignment;
		this.source = source;
	}
}