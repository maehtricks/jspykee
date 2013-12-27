package net.maehtricks.libspykeej.datatype;

/**
 * The Enum Led encapsulated the low level led type.
 */
public enum Led {
    
    CENTER(0), LEFT(1), RIGHT(2);
    
    private final int value;

    /**
     * Instantiates a new led type.
     *
     * @param value the value
     */
    Led(int value) {
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
