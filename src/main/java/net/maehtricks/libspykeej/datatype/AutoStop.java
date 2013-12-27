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
 * The Enum AutoStop encapsulated the low level auto stop duration
 */
public enum AutoStop {

    ms0050(0), ms0100(1), ms0250(2), ms0500(3), ms1000(4);
    private final byte value;

    /**
     * Instantiates a new AutoStop.
     * 
     * @param value
     *            the value
     */
    AutoStop(int value) {
        this.value = (byte) value;
    }

    /**
     * Returns the byte value.
     * 
     * @return the byte
     */
    public byte value() {
        return value;
    }

}
