package net.maehtricks.jspykee;

public class CommandBuilder {

    public static byte[] getLightCommand(int id, boolean enable) {
        byte[] cmd = { 'P', 'K', 4, 0, 2, //
                Integer.valueOf(id).byteValue(), //
                Integer.valueOf(enable ? 1 : 0).byteValue() };
        return cmd;
    }

    public static byte[] getMotionCommand(byte duration, int left, int right) {
        byte[] cmd = { 'P', 'K', 21, 0, 3, //
                duration, //
                Integer.valueOf(left).byteValue(), //
                Integer.valueOf(right).byteValue() };
        return cmd;
    }
    
    public static byte[] getCameraEnableCommand(boolean enable) {
        byte[] cmd = { 'P', 'K', 15, 0, 2, 1, //
                Integer.valueOf(enable ? 1 : 0).byteValue() };
        return cmd;        
    }
    
    public static byte[] getDockCommand(int id) {
        byte[] cmd = { 'P', 'K', 16, 0, 1, //
                Integer.valueOf(id).byteValue() };
        return cmd;        
    }

    public static byte[] getRebootCommand() {
        byte[] cmd = { 'P', 'K', 24, 0, 2, 0, 0 };
        return cmd;        
    }

    public static byte[] getCameraResolutionCommand(int id) {
        byte[] cmd = { 'P', 'K', 35, 0, 2, Integer.valueOf(id).byteValue(), 20 };
        return cmd;        
    }

    public static byte[] getCameraFpsCommand(int fps) {
        byte[] cmd = { 'P', 'K', 35, 0, 2, 1, Integer.valueOf(fps).byteValue() };
        return cmd;
    }

}
