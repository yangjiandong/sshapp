/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssh.app.util;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class is used to provide binding customizations between text values and Calendar values. The
 * Apache Commons "Time" package classes are used to provide basic parsing and formatting
 * capabilities This Class can be used any framework that supports Bean PropertyEditors to ensure a
 * clean translation at runtime between Locale specific text and a Calendar instance.
 *
 * By default, the pattern that is passed to the constructor will be used without modification when
 * parsing. The Locale passed will be used to generate a list of possible parsing options for Long
 * and Short formats for date, date time, and time.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class CustomCalendarEditor extends PropertyEditorSupport {

    private static Log log = LogFactory.getLog(CustomCalendarEditor.class);
    private final DateFormat dateFormat;
    private final boolean allowEmpty;
    private final int exactDateLength;

    public CustomCalendarEditor(DateFormat dateFormat, boolean allowEmpty) {
        this.dateFormat = dateFormat;
        this.allowEmpty = allowEmpty;
        this.exactDateLength = -1;
    }

    public CustomCalendarEditor(DateFormat dateFormat, boolean allowEmpty, int exactDateLength) {
        this.dateFormat = dateFormat;
        this.allowEmpty = allowEmpty;
        this.exactDateLength = exactDateLength;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (this.allowEmpty && (text == null && text.isEmpty())) {
            setValue(null);
        } else if (text != null && this.exactDateLength >= 0 && text.length() != this.exactDateLength) {
            final String message = "Could not parse date: it is not exactly" + this.exactDateLength + "characters long";
            log.error(message);
            throw new IllegalArgumentException(message);
        } else {
            try {
                Calendar c = Calendar.getInstance();
                c.setTime(this.dateFormat.parse(text));
                setValue(c);
            } catch (ParseException ex) {
                log.error("Could not parse date", ex);
                throw new IllegalArgumentException("Could not parse date", ex);
            }
        }
    }

    @Override
    public String getAsText() {
        Calendar c = (Calendar) getValue();
        return c != null ? this.dateFormat.format(c.getTime()) : "";
    }
}
