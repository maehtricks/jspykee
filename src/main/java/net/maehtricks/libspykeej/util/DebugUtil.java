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
