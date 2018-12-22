package bjc.dbeditor.data.creatures;

public class CreatureHitdieRecord {
	private int	count;
	private int	size;
	private String	type;

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

	public int getCount() {
		return count;
	}

	public int getSize() {
		return size;
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

	public String toFullString() {
		if (type == null || type.equals("")) {
			return count + "d" + size;
		}

		return count + "d" + size + " (From " + type + ")";
	}
}
