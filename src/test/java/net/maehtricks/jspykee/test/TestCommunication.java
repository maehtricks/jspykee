package net.maehtricks.jspykee.test;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import net.maehtricks.jspykee.LoginResponse;
import net.maehtricks.jspykee.SpykeeConnector;
import net.maehtricks.jspykee.SpykeeDataReceiver;
import net.maehtricks.jspykee.datatype.CameraResolution;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class TestCommunication {
	
	@Test
	public void testConnector() throws InterruptedException {
		SpykeeConnector spykeeConnector = new SpykeeConnector();
		
		spykeeConnector.addSpykeeDataReceiver(new SpykeeDataReceiver() {
            
            @Override
            public void onVideoFrame(byte[] buffer) {
                try {
                    FileUtils.writeByteArrayToFile( //
                            new File("C:\\temp\\spykeevideo\\cam" + new Date().getTime() + ".jpg"), buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
            @Override
            public void onUnknownMessage(int cmd, byte[] buffer) {
                System.out.println(String.format("unkown cmd: id=%d len=%d", cmd, buffer.length));
            }
            
            @Override
            public void onBatteryLevel(int level) {
                System.out.println(String.format("Battery Level: %d%%", level));                
            }
        });
		
		spykeeConnector
			.setHost("spykee") //
			.setPort(9000) //
			.setUsername("admin") //
			.setPassword("jspykee2013");
		
		LoginResponse loginResponse = spykeeConnector.connect();
		if(loginResponse.isSuccess()) {
		    spykeeConnector.sendCameraFpsCommand(10);
		    spykeeConnector.sendCameraResolutionCommand(CameraResolution.HIGH);
		    spykeeConnector.sendCameraEnableCommand(true);
//			spykeeConnector.sendMotionCommand(AutoStop.ms1000, -100, -100);
//		    spykeeConnector.sendLightCommand(Led.CENTER, true);
//		    spykeeConnector.sendLightCommand(Led.LEFT, true);
//		    spykeeConnector.sendLightCommand(Led.RIGHT, true);
//		    spykeeConnector.sendRebootCommand();
//		    spykeeConnector.sendCameraResolutionCommand(CameraResolution.SMALL);
//		    spykeeConnector.sendCameraResolutionCommand(CameraResolution.MEDIUM);
		    
		    Thread.sleep(10000);
			spykeeConnector.disconnect();
		}
	}
}
