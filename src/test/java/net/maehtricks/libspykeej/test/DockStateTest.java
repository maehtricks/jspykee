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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import net.maehtricks.libspykeej.datatype.DockState;
import net.maehtricks.libspykeej.util.Communication;

import org.junit.Test;

public class DockStateTest {

	@Test
	public void testDockState() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Communication<DockState> dCom = new Communication<DockState>(DockState.class);
		dCom.sendOne(baos, DockState.DOCKED);
		baos.close();
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		DockState dockState = dCom.receiveOne(bais);
		System.out.println(dockState);
		
		dCom.sendOne(baos, DockState.UNDOCKED);
		bais = new ByteArrayInputStream(baos.toByteArray());
		dockState = dCom.receiveOne(bais);
	}

}
