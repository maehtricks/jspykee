package net.maehtricks.libspykeej.datatype;

/**
 * The Enum AutoStop encapsulated the low level auto stop duration
 */
public enum AutoStop {

    ms0050(0), ms0100(1), ms0250(2), ms0500(3), ms1000(4);
    private final byte value;

    /**
     * Instantiates a new AutoStop.
     * 
     * @param value
     *            the value
     */
    AutoStop(int value) {
        this.value = (byte) value;
    }

    /**
     * Returns the byte value.
     * 
     * @return the byte
     */
    public byte value() {
        return value;
    }

}
