package bjc.dbeditor.data.creatures;

public class CreatureSpeed {
	private String	type;
	private int		rate;

	public CreatureSpeed(String type, int rate) {
		this.type = type;
		this.rate = rate;
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

		CreatureSpeed other = (CreatureSpeed) obj;

		if (rate != other.rate) {
			return false;
		}

		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}

		return true;
	}

	public int getRate() {
		return rate;
	}

	public String getType() {
		return type;
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
		result = prime * result + rate;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return type + " " + rate + " ft";
	}
}
