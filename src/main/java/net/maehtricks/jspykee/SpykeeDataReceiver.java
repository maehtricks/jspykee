package net.maehtricks.jspykee;

public interface SpykeeDataReceiver {

    void onBatteryLevel(int level);
    void onVideoFrame(byte[] buffer);
    void onUnknownMessage(int cmd, byte[] buffer);

}
