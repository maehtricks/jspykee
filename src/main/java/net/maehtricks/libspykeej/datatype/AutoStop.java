package net.maehtricks.libspykeej.datatype;

public enum AutoStop {
	ms0050(0), ms0100(1), ms0250(2), ms0500(3), ms1000(4);

	private final byte value;

	AutoStop(int value) {
		this.value = (byte) value;
	}

	public byte value() {
		return value;
	}

}
