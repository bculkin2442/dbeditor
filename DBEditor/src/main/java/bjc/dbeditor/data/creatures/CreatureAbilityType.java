package bjc.dbeditor.data.creatures;

public enum CreatureAbilityType {
	EXTRAORDINARY, INHERENT, PSILIKE, SPELLLIKE, SUPERNATURAL;

	public String toShortString() {
		switch (this) {
			case EXTRAORDINARY:
				return "(Ex)";
			case PSILIKE:
				return "(Psi)";
			case SPELLLIKE:
				return "(Sp)";
			case SUPERNATURAL:
				return "(Su)";
			case INHERENT:
			default:
				return "";
		}
	}
}
