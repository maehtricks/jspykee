package net.maehtricks.libspykeej.datatype;

import net.maehtricks.libspykeej.util.EnumConverter;


public enum DockState implements EnumConverter {
	UNDEFINED(0),
	UNDOCKED(1),
	DOCKED(2);
	
	private final byte value;
	
	DockState(int value) {
		this.value = (byte) value;
	}

	@Override
	public byte convert() {
		return value;
	}
}
