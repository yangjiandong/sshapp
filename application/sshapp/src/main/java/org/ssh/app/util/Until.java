package org.ssh.app.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Until {

    public static String snprintf(int size, String format, Object... args) {
        StringWriter writer = new StringWriter(size);
        PrintWriter out = new PrintWriter(writer);
        out.printf(format, args);
        out.close();
        return writer.toString();
    }

}
