package org.springside.modules.unit.mapper;

import static org.junit.Assert.*;

import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springside.modules.mapper.ObjectMapper;

public class CovertUtilsTest {

    @Test
    public void convertStringToObject() {
        assertEquals(1, ObjectMapper.convertToObject("1", Integer.class));

        Date date = (Date) ObjectMapper.convertToObject("2010-06-01", Date.class);
        assertEquals(2010, new DateTime(date).getYear());

        Date dateTime = (Date) ObjectMapper.convertToObject("2010-06-01 12:00:04", Date.class);
        assertEquals(12, new DateTime(dateTime).getHourOfDay());
    }
}
