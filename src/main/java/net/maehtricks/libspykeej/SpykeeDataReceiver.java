package net.maehtricks.libspykeej;

public interface SpykeeDataReceiver {

    void onBatteryLevel(int level);
    void onVideoFrame(byte[] buffer);
    void onUnknownMessage(int cmd, byte[] buffer);

}
