package net.maehtricks.libspykeej.datatype;

/**
 * The Enum Dock encapsulated the low level dock state command type.
 */
public enum Dock {
    
    DOCK(6), UNDOCK(5), CANCEL(7);
    private final int value;

    /**
     * Instantiates a new dock state command type.
     *
     * @param value the value
     */
    Dock(int value) {
        this.value = value;
    }

    /**
     * Value.
     *
     * @return the int
     */
    public int value() {
        return value;
    }
}
