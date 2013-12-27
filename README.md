libspykeej
==========

Java library for controlling Spykee the not ignorable spy robot :) (http://www.spykeeworld.com/spykee/UK/index.html).

Supports unofficial firmware 1.0.43 commands (https://sites.google.com/site/androkee/download).
- motion autostop
- vga camera resolution
- etc.

Original firmware commands are implemented but not tested :(

Compiling / Building
--------------------
Checkout with Eclipse or build with Maven :)

Usage
-----
```
// create SpykeeConnector
SpykeeConnector spykeeConnector = new SpykeeConnector();

// add your custom data receiver
spykeeConnector.addSpykeeDataReceiver(new SpykeeDataReceiver() {
    @Override
    public void onVideoFrame(byte[] buffer) {
    	// implement your camera handling here...
    	// e.g. writing to filesystem
    	// or send it to a view
		...
    }
    ...
});

// use dsl style connect
LoginResponse loginResponse = spykeeConnector
								.setHost("spykee")
								.setPort(9000)
								.setUsername("admin")
								.setPassword("jspykee2013")
								.connect();

// evaluate login message and issue commands as you like
if(loginResponse.isSuccess()) {
    // camera commands
    spykeeConnector.sendCameraFpsCommand(10);
    spykeeConnector.sendCameraResolutionCommand(CameraResolution.HIGH);
    spykeeConnector.sendCameraEnableCommand(true);
    
   	// put on some lights
    spykeeConnector.sendLightCommand(Led.CENTER, true);
    spykeeConnector.sendLightCommand(Led.LEFT, true);
    spykeeConnector.sendLightCommand(Led.RIGHT, true);
    
    // move your robot!
   	// go forward
	spykeeConnector.sendMotionCommand(AutoStop.ms1000, 100, 100);
    // go backward
	spykeeConnector.sendMotionCommand(AutoStop.ms1000, -100, -100);
	// turn left
	spykeeConnector.sendMotionCommand(AutoStop.ms1000, 100, -100);
	// turn right
	spykeeConnector.sendMotionCommand(AutoStop.ms1000, -100, 100);
	
    // bye
	spykeeConnector.disconnect();
}
```

Todo
----

- tests
- audio support
- connection error handling

License
-------
Copyright 2013 by maehtricks.net

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
