package net.maehtricks.libspykeej.datatype;

import net.maehtricks.libspykeej.util.EnumConverter;

/**
 * The Enum DockState represents the low level dock state event type.
 */
public enum DockState implements EnumConverter {
	
	UNDEFINED(0), UNDOCKED(1), DOCKED(2);
	private final byte value;
	
	/**
	 * Instantiates a new dock state event type.
	 *
	 * @param value the value
	 */
	DockState(int value) {
		this.value = (byte) value;
	}

	/* (non-Javadoc)
	 * @see net.maehtricks.libspykeej.util.EnumConverter#convert()
	 */
	@Override
	public byte convert() {
		return value;
	}
}
