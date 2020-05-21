package bjc.dbeditor.data.creatures;

import org.apache.commons.lang3.StringUtils;

public enum CreatureType {
	ABERRATION, ANIMAL, CONSTRUCT, DEATHLESS, DRAGON, ELEMENTAL, FEY, GIANT, HUMANOID,
	MAGICALBEAST, MONSTROUSHUMANOID, OOZE, OUTSIDER, PLANT, UNDEAD, VERMIN;

	@Override
	public String toString() {
		return StringUtils.capitalize(this.name().toLowerCase());
	}
}
