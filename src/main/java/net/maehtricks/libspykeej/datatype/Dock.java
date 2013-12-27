package net.maehtricks.libspykeej.datatype;

public enum Dock {
    DOCK(6), UNDOCK(5), CANCEL(7);

    private final int value;

    Dock(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
