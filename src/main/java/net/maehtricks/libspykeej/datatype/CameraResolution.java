package net.maehtricks.libspykeej.datatype;

/**
 * The Enum CameraResolution encapsulated the low level camera resolution.
 */
public enum CameraResolution {
    
    LOW(0), MEDIUM(1), HIGH(2);
    private final int value;

    /**
     * Instantiates a new camera resolution.
     *
     * @param value the value
     */
    CameraResolution(int value) {
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
