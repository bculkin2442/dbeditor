package bjc.dbeditor.data.creatures;

public class CreatureSkill {
	private String	skillName;
	private int	skillBonus;

	public CreatureSkill(String skillName, int skillBonus) {
		this.skillName = skillName;
		this.skillBonus = skillBonus;
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

		CreatureSkill other = (CreatureSkill) obj;

		if (skillBonus != other.skillBonus) {
			return false;
		}

		if (skillName == null) {
			if (other.skillName != null) {
				return false;
			}
		} else if (!skillName.equals(other.skillName)) {
			return false;
		}

		return true;
	}

	public int getSkillBonus() {
		return skillBonus;
	}

	public String getSkillName() {
		return skillName;
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
		result = prime * result + skillBonus;
		result = prime * result + ((skillName == null) ? 0 : skillName.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return skillName + ": +" + skillBonus;
	}
}
