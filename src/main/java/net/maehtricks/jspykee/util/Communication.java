package net.maehtricks.jspykee.util;

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
