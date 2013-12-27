package net.maehtricks.libspykeej;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

import net.maehtricks.libspykeej.datatype.AutoStop;
import net.maehtricks.libspykeej.datatype.CameraResolution;
import net.maehtricks.libspykeej.datatype.Dock;
import net.maehtricks.libspykeej.datatype.Led;
import net.maehtricks.libspykeej.util.DebugUtil;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;

public class SpykeeConnector {
    private String username = null;
    private String password = null;
    private String host = null;
    private int port = 9000;

    private Socket socket = null;
    private DataInputStream inputStream = null;
    private DataOutputStream outputStream = null;
    
    private ResponseReader responseReader = null;
    private volatile List<SpykeeDataReceiver> spykeeDataReceivers = new Vector<SpykeeDataReceiver>();


    public SpykeeConnector() {
        this.responseReader = new ResponseReader();
        this.responseReader.setDataReceiver(new SpykeeDataDistributor());
    }
    
    public void addSpykeeDataReceiver(SpykeeDataReceiver spykeeDataReceiver) {
        this.spykeeDataReceivers.add(spykeeDataReceiver);
    }
    
    public void removeSpykeeDataReceiver(SpykeeDataReceiver spykeeDataReceiver) {
        this.spykeeDataReceivers.remove(spykeeDataReceiver);
    }
    
    public void clearSpykeeDataReceivers() {
        this.spykeeDataReceivers.clear();
    }
    
    public SpykeeConnector setUsername(String username) {
        this.username = username;
        return this;
    }

    public SpykeeConnector setPassword(String password) {
        this.password = password;
        return this;
    }

    public SpykeeConnector setHost(String host) {
        this.host = host;
        return this;
    }

    public SpykeeConnector setPort(int port) {
        this.port = port;
        return this;
    }

    public byte[] buildLoginSentence() {
        byte[] loginCmd = { 'P', 'K', 0x0a, 0 };
        byte[] loginSentence = ArrayUtils.clone(loginCmd);

        loginSentence = ArrayUtils.add(loginSentence, (byte) (username.length() + password.length() + 2));
        loginSentence = ArrayUtils.add(loginSentence, (byte) username.length());
        loginSentence = ArrayUtils.addAll(loginSentence, username.getBytes());
        loginSentence = ArrayUtils.add(loginSentence, (byte) password.length());
        loginSentence = ArrayUtils.addAll(loginSentence, password.getBytes());

        return loginSentence;
    }

    public LoginResponse connect() {
        try {
            socket = new Socket(host, port);
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());

            login();
            return processLoginResponse();
        } catch (IOException e) {
            e.printStackTrace();
            return new LoginResponse();
        }
    }

    public void login() throws IOException {
        outputStream.write(buildLoginSentence());
    }

    public LoginResponse processLoginResponse() throws IOException {
        byte[] buffer = new byte[2048];

        IOUtils.readFully(inputStream, buffer, 0, 5);

        int len = buffer[4]; // number of remaining bytes
        IOUtils.readFully(inputStream, buffer, 0, len);

        if (len < 8) {
            System.out.println("login failed");
            return new LoginResponse();
        }

        LoginResponse loginResponse = LoginResponse.fromByteArray(buffer);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        
        responseReader.setInputStream(inputStream);
        Thread thread = new Thread(responseReader, "ResponseReaderThread");
        thread.start();
        
        return loginResponse;
    }

    public void sendMotionCommand(AutoStop autoStop, int left, int right) {
        byte[] cmd = CommandBuilder.getMotionCommand(autoStop.value(), left, right);
        sendCommand(cmd);
    }

    public void sendLightCommand(Led led, boolean enable) {
        byte[] cmd = CommandBuilder.getLightCommand(led.value(), enable);
        sendCommand(cmd);
    }

    public void sendCameraEnableCommand(boolean enable) {
        byte[] cmd = CommandBuilder.getCameraEnableCommand(enable);
        sendCommand(cmd);
    }

    public void sendDockCommand(Dock dock) {
        byte[] cmd = CommandBuilder.getDockCommand(dock.value());
        sendCommand(cmd);
    }

    private void sendCommand(byte[] cmd) {
        try {
            outputStream.write(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void sendCameraResolutionCommand(CameraResolution cameraResolution) {
        byte[] cmd = CommandBuilder.getCameraResolutionCommand(cameraResolution.value());
        sendCommand(cmd);
        DebugUtil.showBuffer("res", cmd, cmd.length);
    }
    
    public void sendRebootCommand() {
        byte[] cmd = CommandBuilder.getRebootCommand();
        sendCommand(cmd);        
    }
    
    public void sendCameraFpsCommand(int fps) {
        byte[] cmd = CommandBuilder.getCameraFpsCommand(fps);
        sendCommand(cmd);
    }

    public void disconnect() {
        try {
            responseReader.stop();
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private class SpykeeDataDistributor implements SpykeeDataReceiver {

        @Override
        public void onBatteryLevel(int level) {
            for (SpykeeDataReceiver spykeeDataReceiver : spykeeDataReceivers) {
                spykeeDataReceiver.onBatteryLevel(level);
            }
        }

        @Override
        public void onVideoFrame(byte[] buffer) {
            for (SpykeeDataReceiver spykeeDataReceiver : spykeeDataReceivers) {
                spykeeDataReceiver.onVideoFrame(buffer);
            }
        }

        @Override
        public void onUnknownMessage(int cmd, byte[] buffer) {
            for (SpykeeDataReceiver spykeeDataReceiver : spykeeDataReceivers) {
                spykeeDataReceiver.onUnknownMessage(cmd, buffer);
            }
        }
        
    }
    
}
