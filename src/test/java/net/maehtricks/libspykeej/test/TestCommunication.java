// Copyright 2013 maehtricks.net

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

package net.maehtricks.libspykeej.test;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import net.maehtricks.libspykeej.LoginResponse;
import net.maehtricks.libspykeej.SpykeeConnector;
import net.maehtricks.libspykeej.SpykeeDataReceiver;
import net.maehtricks.libspykeej.datatype.CameraResolution;

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
