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
import java.io.IOException;
import java.net.SocketException;

import net.maehtricks.libspykeej.datatype.MessageType;

import org.apache.commons.io.IOUtils;

/**
 * The Class ResponseReader reads data from Spykee via InputStream and calls the
 * appropriate handler for each event.
 */
public class ResponseReader implements Runnable {

    private DataInputStream inputStream;
    private volatile boolean isStopped = false;
    private volatile SpykeeDataReceiver dataReceiver;

    /**
     * Sets the data receiver.
     * 
     * @param dataReceiver
     *            the new data receiver
     */
    public void setDataReceiver(SpykeeDataReceiver dataReceiver) {
        this.dataReceiver = dataReceiver;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        byte[] header = new byte[5];
        while (!isStopped) {
            try {
                int cmd = -1;
                int len = 0;
                try {

                    int bytesRead = IOUtils.read(inputStream, header, 0, 5);
                    if (bytesRead == 5 //
                            && (header[0] & 0xff) == 'P' //
                            && (header[1] & 0xff) == 'K') {
                        cmd = header[2] & 0xff;
                        len = ((header[3] & 0xff) << 8) | (header[4] & 0xff);

                        byte[] buffer = new byte[len];
                        bytesRead = IOUtils.read(inputStream, buffer, 0, len);

                        handleMessage(cmd, buffer);
                    }
                } catch (SocketException e) {
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handles messages and calls the appropriate event receiver method.
     * 
     * @param cmd
     *            the cmd
     * @param buffer
     *            the buffer
     */
    private void handleMessage(int cmd, byte[] buffer) {
        switch (cmd) {
        case MessageType.NONE:
            System.out.println("NONE");
            break;
        case MessageType.AUDIO:
            System.out.println("AUDIO");
            break;
        case MessageType.VIDEO:
            dataReceiver.onVideoFrame(buffer);
            break;
        case MessageType.BATTERY:
            int level = buffer[0] & 0xff;
            dataReceiver.onBatteryLevel(level);
            break;
        case MessageType.LED:
            System.out.println("LED");
            break;
        default:
            dataReceiver.onUnknownMessage(cmd, buffer);
            break;
        }
    }

    /**
     * Sets the input stream.
     * 
     * @param inputStream
     *            the new input stream
     */
    public void setInputStream(DataInputStream inputStream) {
        this.inputStream = inputStream;
        this.isStopped = false;
    }

    /**
     * Stops the reader loop.
     */
    public void stop() {
        this.isStopped = true;
    }

}