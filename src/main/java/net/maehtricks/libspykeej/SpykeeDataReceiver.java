package net.maehtricks.libspykeej;

/**
 * The Interface SpykeeDataReceiver is for implementing event handlers.
 */
public interface SpykeeDataReceiver {

    /**
     * Gets called if new battery level is received.
     * 
     * @param level
     *            the level
     */
    void onBatteryLevel(int level);

    /**
     * Gets called if new video frame is received.
     * 
     * @param buffer
     *            the buffer
     */
    void onVideoFrame(byte[] buffer);

    /**
     * Gets called if unknown message is received.
     * 
     * @param cmd
     *            the cmd
     * @param buffer
     *            the buffer
     */
    void onUnknownMessage(int cmd, byte[] buffer);

}
