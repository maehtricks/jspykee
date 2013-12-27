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
