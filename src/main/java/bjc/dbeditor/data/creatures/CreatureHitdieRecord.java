package bjc.dbeditor.data.creatures;

/**
 * Represents a set of hit-dice for a creature.
 * 
 * @author Ben Culkin
 *
 */
public class CreatureHitdieRecord {
	private int count;
	private int size;
	private String type;

	/**
	 * Create a new hit-dice set.
	 * 
	 * @param count
	 *              The number of hit-dice in this set.
	 * @param size
	 *              The size of the the hit-dice in this set.
	 * @param type
	 *              The label for this hit-dice set.
	 */
	public CreatureHitdieRecord(int count, int size, String type) {
		this.count = count;
		this.size = size;
		this.type = type;
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

		CreatureHitdieRecord other = (CreatureHitdieRecord) obj;

		if (count != other.count) {
			return false;
		} else if (size != other.size) {
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

	/**
	 * Get the number of hit-dice in this set.
	 * 
	 * @return The number of hit-dice in this set.
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Get the size of the hit-dice in this set.
	 * 
	 * @return The size of the hit-dice in this set.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Get the label for the hit-dice in this set.
	 * 
	 * @return The label for the hit-dice in this set.
	 */
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
		result = prime * result + count;
		result = prime * result + size;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("'( " + count + ", " + size + ")'");

		return sb.toString();
	}

	/**
	 * Get a string with a full description of these hit-dice.
	 * @return A string with a full description of these hit-dice.
	 */
	public String toFullString() {
		if (type == null || type.equals("")) {
			return count + "d" + size;
		}

		return count + "d" + size + " (From " + type + ")";
	}
}
