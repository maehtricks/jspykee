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

package net.maehtricks.libspykeej.datatype;

/**
 * The Enum CameraResolution encapsulated the low level camera resolution.
 */
public enum CameraResolution {
    
    LOW(0), MEDIUM(1), HIGH(2);
    private final int value;

    /**
     * Instantiates a new camera resolution.
     *
     * @param value the value
     */
    CameraResolution(int value) {
        this.value = value;
    }

    /**
     * Value.
     *
     * @return the int
     */
    public int value() {
        return value;
    }
}
