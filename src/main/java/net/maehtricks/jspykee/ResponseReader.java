package net.maehtricks.jspykee;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.SocketException;
import net.maehtricks.jspykee.datatype.MessageType;
import org.apache.commons.io.IOUtils;

public class ResponseReader implements Runnable {

    private DataInputStream inputStream;
    private volatile boolean isStopped = false;
    private volatile SpykeeDataReceiver dataReceiver;

    public void setDataReceiver(SpykeeDataReceiver dataReceiver) {
        this.dataReceiver = dataReceiver;
    }

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

    public void setInputStream(DataInputStream inputStream) {
        this.inputStream = inputStream;
        this.isStopped = false;
    }

    public void stop() {
        this.isStopped = true;
    }
    
}