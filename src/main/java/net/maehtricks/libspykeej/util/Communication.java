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

package net.maehtricks.libspykeej.util;

import java.io.*;


public class Communication<E extends Enum<E> & EnumConverter> {
  private final ReverseEnumMap<E> reverse;

  public Communication(Class<E> ec) {
    reverse = new ReverseEnumMap<E>(ec);
  }

  public void sendOne(OutputStream out, E e) throws IOException {
    out.write(e.convert());
  }

  public E receiveOne(InputStream in) throws IOException {
    int b = in.read();
    if (b == -1) throw new EOFException();
    return reverse.get((byte) b);
  }
}
