package bjc.dbeditor.data.creatures;

public class CreatureSaves {
	private int	fortitude;
	private int	reflex;
	private int	will;

	public CreatureSaves(int fortitude, int reflex, int will) {
		this.fortitude = fortitude;
		this.reflex = reflex;
		this.will = will;
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

		CreatureSaves other = (CreatureSaves) obj;

		if (fortitude != other.fortitude) {
			return false;
		} else if (reflex != other.reflex) {
			return false;
		} else if (will != other.will) {
			return false;
		}

		return true;
	}

	public int getFortitude() {
		return fortitude;
	}

	public int getReflex() {
		return reflex;
	}

	public int getWill() {
		return will;
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
		result = prime * result + fortitude;
		result = prime * result + reflex;
		result = prime * result + will;
		return result;
	}

	@Override
	public String toString() {
		return "Fort: " + fortitude + "\tRef: " + reflex + "\tWill: " + will;
	}
}
