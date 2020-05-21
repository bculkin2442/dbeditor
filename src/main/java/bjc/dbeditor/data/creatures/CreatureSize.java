package bjc.dbeditor.data.creatures;

import org.apache.commons.lang3.StringUtils;

public enum CreatureSize {
	FINE, DIMINUTIVE, TINY, SMALL, MEDIUM, LARGE, HUGE, GARGANTUAN, COLOSSAL;

	@Override
	public String toString() {
		return StringUtils.capitalize(this.name().toLowerCase());
	}
}
