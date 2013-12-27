package net.maehtricks.libspykeej.datatype;

public enum Led {
    CENTER(0), LEFT(1), RIGHT(2);
    
    private final int value;

    Led(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

}
