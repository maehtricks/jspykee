package net.maehtricks.libspykeej;

/**
 * The Class CommandBuilder is a helper class for building Spykee's commands.
 */
public class CommandBuilder {

    /**
     * Gets the light command.
     * 
     * @param id
     *            lamp id; 0 = center, 1 = left, 2 = right
     * @param enable
     * @return the light command
     */
    public static byte[] getLightCommand(int id, boolean enable) {
        byte[] cmd = { 'P', 'K', 4, 0, 2, //
                Integer.valueOf(id).byteValue(), //
                Integer.valueOf(enable ? 1 : 0).byteValue() };
        return cmd;
    }

    /**
     * Gets the custom firmware motion command.
     * 
     * For positive speeds the motor turns forward, for negative speeds the
     * motor turns backward.
     * 
     * @param duration
     *            in ms
     * @param left
     *            the left motor's speed + direction
     * @param right
     *            the right motor's speed + direction
     * @return the motion command
     */
    public static byte[] getMotionCommand(byte duration, int left, int right) {
        byte[] cmd = { 'P', 'K', 21, 0, 3, //
                duration, //
                Integer.valueOf(left).byteValue(), //
                Integer.valueOf(right).byteValue() };
        return cmd;
    }

    /**
     * Gets the original firmware motion command.
     * 
     * For positive speeds the motor turns forward, for negative speeds the
     * motor turns backward.
     * 
     * The motors will turn until the stop command is issued.
     * 
     * @param left
     *            the left motor's speed + direction
     * @param right
     *            the right motor's speed + direction
     * @return the motion command
     */
    public static byte[] getMotionCommand(int left, int right) {
        byte[] cmd = { 'P', 'K', 5, 0, 3, 0, //
                Integer.valueOf(left).byteValue(), //
                Integer.valueOf(right).byteValue() };
        return cmd;
    }

    /**
     * Gets the stop motion command.
     * 
     * @return the stop motion command
     */
    public static byte[] getStopMotionCommand() {
        byte[] cmd = { 'P', 'K', 5, 0, 2, 0, 0 };
        return cmd;
    }

    /**
     * Gets the camera enable command.
     * 
     * @param enable
     *            true enables the camera, false disables the camera.
     * @return the camera enable command
     */
    public static byte[] getCameraEnableCommand(boolean enable) {
        byte[] cmd = { 'P', 'K', 15, 0, 2, 1, //
                Integer.valueOf(enable ? 1 : 0).byteValue() };
        return cmd;
    }

    /**
     * Gets the dock command.
     * 
     * @param id
     *            the command's id; 5 = dock, 6 = undock, 7 = cancel dock
     * @return the dock command
     */
    public static byte[] getDockCommand(int id) {
        byte[] cmd = { 'P', 'K', 16, 0, 1, //
                Integer.valueOf(id).byteValue() };
        return cmd;
    }

    /**
     * Gets the reboot command.
     * 
     * @return the reboot command
     */
    public static byte[] getRebootCommand() {
        byte[] cmd = { 'P', 'K', 24, 0, 2, 0, 0 };
        return cmd;
    }

    /**
     * Gets the camera resolution command.
     * 
     * @param id
     *            the resulution's id; 0 = 160x120, 1 = 320x240, 640x480 = high
     * @return the camera resolution command
     */
    public static byte[] getCameraResolutionCommand(int id) {
        byte[] cmd = { 'P', 'K', 35, 0, 2, Integer.valueOf(id).byteValue(), 20 };
        return cmd;
    }

    /**
     * Gets the camera fps command for setting the desired fps rate.
     * 
     * @param fps
     *            the fps
     * @return the camera fps command
     */
    public static byte[] getCameraFpsCommand(int fps) {
        byte[] cmd = { 'P', 'K', 35, 0, 2, 1, Integer.valueOf(fps).byteValue() };
        return cmd;
    }
    
    public static byte[] getLoginSentence() {
        byte[] cmd = { 'P', 'K', 0x0a, 0 };
        return cmd;
    }

}
