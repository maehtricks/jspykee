package net.maehtricks.libspykeej.util;

public class DebugUtil {
    // The number of characters decoded per line in the hex dump
    private static final int CHARS_PER_LINE = 32;

    // After this many characters in the hex dump, an extra space is inserted
    // (this must be a power of two).
    private static final int EXTRA_SPACE_FREQ = 8;
    private static final int EXTRA_SPACE_MASK = EXTRA_SPACE_FREQ - 1;

    public static void showBuffer(String tag, byte[] bytes, int len) {
        if (len > 256) {
            len = 256;
        }
        int charsPerLine = CHARS_PER_LINE;
        if (len < charsPerLine) {
            charsPerLine = len;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i += charsPerLine) {
            builder.append(tag + " ");
            for (int j = 0; j < charsPerLine; j++) {
                if (i + j >= len) {
                    break;
                }
                byte val = bytes[i + j];
                builder.append(String.format("%02x ", val));
                if ((j & EXTRA_SPACE_MASK) == EXTRA_SPACE_MASK) {
                    builder.append(' ');
                }
            }
            if (len - i < charsPerLine) {
                for (int j = len - i; j < charsPerLine; j++) {
                    builder.append("   ");
                    if ((j & EXTRA_SPACE_MASK) == EXTRA_SPACE_MASK) {
                        builder.append(' ');
                    }
                }
            }

            // Put an extra space before the ascii character dump
            builder.append(' ');
            for (int j = 0; j < charsPerLine; j++) {
                if (i + j >= len) {
                    break;
                }
                byte val = bytes[i + j];
                if (val < 0x20 || val > 0x7e) {
                    val = '.';
                }
                builder.append(String.format("%c", val));
                if ((j & EXTRA_SPACE_MASK) == EXTRA_SPACE_MASK) {
                    builder.append(' ');
                }
            }
            System.out.println(builder.toString());
            builder.setLength(0);
        }
    }
}
