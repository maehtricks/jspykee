// Copyright 2013 maehtricks.net. All Rights Reserved.

// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

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

/**
 * The Class SpykeeConnector is the main interaction object of the libspykeej
 * library.
 * 
 * Issue robot commands and read data with this class.
 */
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

    /**
     * Instantiates a new SpykeeConnector.
     */
    public SpykeeConnector() {
        this.responseReader = new ResponseReader();
        this.responseReader.setDataReceiver(new SpykeeDataDistributor());
    }

    /**
     * Adds the spykee data receiver. Use this method to register your custom
     * data handlers.
     * 
     * @param spykeeDataReceiver
     *            the spykee data receiver
     */
    public void addSpykeeDataReceiver(SpykeeDataReceiver spykeeDataReceiver) {
        this.spykeeDataReceivers.add(spykeeDataReceiver);
    }

    /**
     * Removes the specified data receiver.
     * 
     * @param spykeeDataReceiver
     *            the spykee data receiver
     */
    public void removeSpykeeDataReceiver(SpykeeDataReceiver spykeeDataReceiver) {
        this.spykeeDataReceivers.remove(spykeeDataReceiver);
    }

    /**
     * Clear all data receivers.
     */
    public void clearSpykeeDataReceivers() {
        this.spykeeDataReceivers.clear();
    }

    /**
     * Sets the username.
     * 
     * @param username
     *            the username
     * @return this
     */
    public SpykeeConnector setUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * Sets the password.
     * 
     * @param password
     *            the password
     * @return this
     */
    public SpykeeConnector setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * Sets the host (ip or hostname).
     * 
     * @param host
     *            the host
     * @return this
     */
    public SpykeeConnector setHost(String host) {
        this.host = host;
        return this;
    }

    /**
     * Sets the port.
     * 
     * @param port
     *            the port
     * @return this
     */
    public SpykeeConnector setPort(int port) {
        this.port = port;
        return this;
    }

    /**
     * Builds the login sentence.
     * 
     * @return the byte[]
     */
    private byte[] buildLoginSentence() {
        byte[] loginSentence = CommandBuilder.getLoginSentence();

        loginSentence = ArrayUtils.add(loginSentence, (byte) (username.length() + password.length() + 2));
        loginSentence = ArrayUtils.add(loginSentence, (byte) username.length());
        loginSentence = ArrayUtils.addAll(loginSentence, username.getBytes());
        loginSentence = ArrayUtils.add(loginSentence, (byte) password.length());
        loginSentence = ArrayUtils.addAll(loginSentence, password.getBytes());

        return loginSentence;
    }

    /**
     * Connect with Spykee. Evaluate LoginResponse to determine if the
     * connection is established.
     * 
     * @return the login response
     */
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

    /**
     * Builds & sends login command to the robot.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private void login() throws IOException {
        outputStream.write(buildLoginSentence());
    }

    /**
     * Processes login response.
     * 
     * @return the login response
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private LoginResponse processLoginResponse() throws IOException {
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

    /**
     * Sends the custom firmware motion command.
     * 
     * For positive speeds the motor turns forward, for negative speeds the
     * motor turns backward.
     * 
     * @param autoStop
     *            the auto stop timeout
     * @param left
     *            the left motor's speed + direction
     * @param right
     *            the right motor's speed + direction
     */
    public void sendMotionCommand(AutoStop autoStop, int left, int right) {
        byte[] cmd = CommandBuilder.getMotionCommand(autoStop.value(), left, right);
        sendCommand(cmd);
    }

    /**
     * Sends the original firmware motion command.
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
     */
    public void sendMotionCommand(int left, int right) {
        byte[] cmd = CommandBuilder.getMotionCommand(left, right);
        sendCommand(cmd);
    }

    /**
     * Send stop motion command.
     * 
     * @param autoStop
     *            the auto stop
     * @param left
     *            the left
     * @param right
     *            the right
     */
    public void sendStopMotionCommand() {
        byte[] cmd = CommandBuilder.getStopMotionCommand();
        sendCommand(cmd);
    }

    /**
     * Send light command.
     * 
     * @param led
     *            the led
     * @param enable
     *            the enable
     */
    public void sendLightCommand(Led led, boolean enable) {
        byte[] cmd = CommandBuilder.getLightCommand(led.value(), enable);
        sendCommand(cmd);
    }

    /**
     * Send camera enable command.
     * 
     * @param enable
     */
    public void sendCameraEnableCommand(boolean enable) {
        byte[] cmd = CommandBuilder.getCameraEnableCommand(enable);
        sendCommand(cmd);
    }

    /**
     * Send dock command.
     * 
     * @param dock
     */
    public void sendDockCommand(Dock dock) {
        byte[] cmd = CommandBuilder.getDockCommand(dock.value());
        sendCommand(cmd);
    }

    /**
     * Sends a command.
     * 
     * @param cmd
     *            the cmd
     */
    private void sendCommand(byte[] cmd) {
        try {
            outputStream.write(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send camera resolution command.
     * 
     * @param cameraResolution
     */
    public void sendCameraResolutionCommand(CameraResolution cameraResolution) {
        byte[] cmd = CommandBuilder.getCameraResolutionCommand(cameraResolution.value());
        sendCommand(cmd);
        DebugUtil.showBuffer("res", cmd, cmd.length);
    }

    /**
     * Send reboot command.
     */
    public void sendRebootCommand() {
        byte[] cmd = CommandBuilder.getRebootCommand();
        sendCommand(cmd);
    }

    /**
     * Send camera fps command.
     * 
     * @param fps
     *            the desired fps
     */
    public void sendCameraFpsCommand(int fps) {
        byte[] cmd = CommandBuilder.getCameraFpsCommand(fps);
        sendCommand(cmd);
    }

    /**
     * Disconnect from Spykee.
     */
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

    /**
     * The Class SpykeeDataDistributor notifies all SpykeeDatareceivers on new
     * events.
     */
    private class SpykeeDataDistributor implements SpykeeDataReceiver {

        /*
         * (non-Javadoc)
         * 
         * @see net.maehtricks.libspykeej.SpykeeDataReceiver#onBatteryLevel(int)
         */
        @Override
        public void onBatteryLevel(int level) {
            for (SpykeeDataReceiver spykeeDataReceiver : spykeeDataReceivers) {
                spykeeDataReceiver.onBatteryLevel(level);
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * net.maehtricks.libspykeej.SpykeeDataReceiver#onVideoFrame(byte[])
         */
        @Override
        public void onVideoFrame(byte[] buffer) {
            for (SpykeeDataReceiver spykeeDataReceiver : spykeeDataReceivers) {
                spykeeDataReceiver.onVideoFrame(buffer);
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * net.maehtricks.libspykeej.SpykeeDataReceiver#onUnknownMessage(int,
         * byte[])
         */
        @Override
        public void onUnknownMessage(int cmd, byte[] buffer) {
            for (SpykeeDataReceiver spykeeDataReceiver : spykeeDataReceivers) {
                spykeeDataReceiver.onUnknownMessage(cmd, buffer);
            }
        }

    }

}
