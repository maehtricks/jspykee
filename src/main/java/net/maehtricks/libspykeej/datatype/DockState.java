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

import net.maehtricks.libspykeej.util.EnumConverter;

/**
 * The Enum DockState represents the low level dock state event type.
 */
public enum DockState implements EnumConverter {
	
	UNDEFINED(0), UNDOCKED(1), DOCKED(2);
	private final byte value;
	
	/**
	 * Instantiates a new dock state event type.
	 *
	 * @param value the value
	 */
	DockState(int value) {
		this.value = (byte) value;
	}

	/* (non-Javadoc)
	 * @see net.maehtricks.libspykeej.util.EnumConverter#convert()
	 */
	@Override
	public byte convert() {
		return value;
	}
}
