package bjc.dbeditor.data.creatures;

// NOTE is this the right place for this? 
// 	this seems like it would be generally useful elsewhere.
// -- Ben Culkin, 4/12/2020
/**
 * Specifies the type of any ability
 * 
 * @author Ben Culkin
 *
 */
public enum CreatureAbilityType {
	/**
	 * This is an extraordinary ability.
	 */
	EXTRAORDINARY,
	/**
	 * This is a psi-like ability.
	 * 
	 * Note that if magic/psi transparency is in full effect, this may be equivalent
	 * to spell-like.
	 */
	PSILIKE,
	/**
	 * This is a spell-like ability.
	 * 
	 * Note that if magic/psi transparency is in full effect, this may be equivalent
	 * to psi-like.
	 */
	SPELLLIKE,
	/**
	 * This is a supernatural ability.
	 */
	SUPERNATURAL,
	/**
	 * This is a 'inherent' ability.
	 * 
	 * Note that this is not a traditional ability category. It's primary purpose is
	 * to prevent the arguments about whether something like 'Spellcasting' is
	 * extra-ordinary or not.
	 */
	INHERENT;

	/**
	 * Convert this ability type to its short abbreviation.
	 * 
	 * @return The short abbreviation for the type.
	 */
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
