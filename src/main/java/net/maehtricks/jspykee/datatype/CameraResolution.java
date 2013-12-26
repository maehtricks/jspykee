package net.maehtricks.jspykee.datatype;

public enum CameraResolution {
    LOW(0), MEDIUM(1), HIGH(2);

    private final int value;

    CameraResolution(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
