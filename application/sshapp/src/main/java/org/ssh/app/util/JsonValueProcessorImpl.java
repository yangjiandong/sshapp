package org.ssh.app.util;

import java.sql.Date;
import java.text.SimpleDateFormat;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

//http://bbs.tarena.com.cn/archiver/tid-230720.html
//json 处理date型字段
public class JsonValueProcessorImpl implements JsonValueProcessor {
    private String format = "yyyy-MM-dd";

    public JsonValueProcessorImpl() {

    }

    public JsonValueProcessorImpl(String format) {
        this.format = format;
    }

    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
        String[] obj = {};
        if (value instanceof Date[]) {
            SimpleDateFormat sf = new SimpleDateFormat(format);
            Date[] dates = (Date[]) value;
            obj = new String[dates.length];
            for (int i = 0; i < dates.length; i++) {
                obj[i] = sf.format(dates[i]);
            }
        }
        return obj;
    }

    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
        if (value instanceof java.util.Date) {
            String str = new SimpleDateFormat(format).format((Date) value);
            return str;
        }
        return value.toString();
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

}
