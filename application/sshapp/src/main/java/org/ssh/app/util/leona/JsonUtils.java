package org.ssh.app.util.leona;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Writer;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * json工具类.
 *
 * @author Lingo
 * @since 2007-09-19
 */
public class JsonUtils {
    /** * logger. */
    private static Log logger = LogFactory.getLog(JsonUtils.class);

    /** * 工具类需要的保护构造方法. */
    protected JsonUtils() {
    }

    /**
     * write.
     *
     * @param bean obj
     * @param writer 输出流
     * @param excludes 不转换的属性数组
     * @param datePattern date到string转换的模式
     * @throws Exception 写入数据可能出现异常
     */
    public static void write(Object bean, Writer writer,
        String[] excludes, String datePattern) throws Exception {
        String pattern = null;

        if (datePattern != null) {
            pattern = datePattern;
        } else {
            pattern = "yyyy.MM.dd";
        }

        JsonConfig jsonConfig = configJson(excludes, pattern);

        JSON json = JSONSerializer.toJSON(bean, jsonConfig);
        logger.debug(json);
        json.write(writer);
    }

    /**
     * 把bean转换成json字符串，并写入writer.
     *
     * @param bean 实例
     * @param writer 输出流
     * @param excludes 不使用json-lib转换的字段
     * @param datePattern 日期转换格式
     * @throws Exception 可能抛出任何异常
     */
    public static void write(Object bean, Writer writer, String excludes,
        String datePattern) throws Exception {
        if (excludes == null) {
            JsonUtils.write(bean, writer,
                new String[] {"hibernateLazyInitializer"}, datePattern);
        } else {
            JsonUtils.write(bean, writer, excludes.split(","), datePattern);
        }
    }

    /**
     * 把bean转换成json字符串，并写入writer.
     *
     * @param bean 实例
     * @param writer 输出流
     * @throws Exception 可能抛出任何异常
     */
    public static void write(Object bean, Writer writer)
        throws Exception {
        JsonUtils.write(bean, writer,
            new String[] {"hibernateLazyInitializer"}, "yyyy.MM.dd");
    }

    /**
     * 配置json-lib需要的excludes和datePattern.
     *
     * @param excludes 不需要转换的属性数组
     * @param datePattern 日期转换模式
    w     * @return JsonConfig 根据excludes和dataPattern生成的jsonConfig，用于write
     */
    public static JsonConfig configJson(String[] excludes,
        String datePattern) {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(excludes);
        jsonConfig.setIgnoreDefaultExcludes(false);
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        jsonConfig.registerJsonValueProcessor(Date.class,
            new DateJsonValueProcessor(datePattern));
        jsonConfig.registerJsonValueProcessor(java.sql.Date.class,
            new DateJsonValueProcessor(datePattern));
        jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class,
            new DateJsonValueProcessor(datePattern));

        return jsonConfig;
    }

    /**
     * data={"id":"1"}用json的数据创建指定的pojo.
     *
     * @param <T> Object
     * @param data json字符串
     * @param clazz 需要转换成bean的具体类型
     * @param excludes 不需要转换的属性数组
     * @param datePattern 日期转换模式
     * @return T
     * @throws Exception java.lang.InstantiationException,
     *                   java.beans.IntrospectionException,
     *                   java.lang.IllegalAccessException
     */
    public static <T extends Object> T json2Bean(String data,
        Class<T> clazz, String[] excludes, String datePattern)
        throws Exception {
        // JsonUtils.configJson(excludes, datePattern);
        T entity = clazz.newInstance();

        return json2Bean(data, entity, excludes, datePattern);
    }

    /**
     * json转换成bean.
     *
     * @param data json字符串
     * @param clazz 需要转换的类型
     * @param <T> 泛型
         * @return clazz实例
         * @throws Exception 可能抛出任何异常
     */
    public static <T extends Object> T json2Bean(String data,
        Class<T> clazz) throws Exception {
        return json2Bean(data, clazz, null, null);
    }

    /**
     * data={"id":"1"}用json里的数据，填充指定的pojo.
     *
     * @param <T> Object
     * @param data json字符串
     * @param entity 需要填充数据的bean
     * @param excludes 不需要转换的属性数组
     * @param datePattern 日期转换模式
     * @return T
     * @throws Exception java.lang.InstantiationException,
     *                   java.beans.IntrospectionException,
     *                   java.lang.IllegalAccessException
     */
    public static <T extends Object> T json2Bean(String data, T entity,
        String[] excludes, String datePattern) throws Exception {
        // JsonUtils.configJson(excludes, datePattern);
        JSONObject jsonObject = JSONObject.fromObject(data);

        return json2Bean(jsonObject, entity, excludes, datePattern);
    }

    /**
     * json转换成bean.
     *
     * @param data json字符串
     * @param entity 实例
     * @param <T> 泛型
     * @return 实例
     * @throws Exception 可能抛出任何异常
     */
    public static <T extends Object> T json2Bean(String data, T entity)
        throws Exception {
        return json2Bean(data, entity, null, null);
    }

    /**
     * 根据Class生成entity，再把JSONObject中的数据填充进去.
     *
     * @param <T> Object
     * @param jsonObject json对象
     * @param clazz 需要转换成bean的具体类型
     * @param excludes 不需要转换的属性数组
     * @param datePattern 日期转换模式
     * @return T
     * @throws Exception java.lang.InstantiationException,
     *                   java.beans.IntrospectionException,
     *                   java.lang.IllegalAccessException
     */
    public static <T extends Object> T json2Bean(JSONObject jsonObject,
        Class<T> clazz, String[] excludes, String datePattern)
        throws Exception {
        // JsonUtils.configJson(excludes, datePattern);
        T entity = clazz.newInstance();

        return json2Bean(jsonObject, entity, excludes, datePattern);
    }

    /**
     * json转换成bean.
     *
     * @param jsonObject JSONObject
     * @param clazz 类型
     * @param <T> 泛型
     * @return 实例
     * @throws Exception 可能抛出任何异常
     */
    public static <T extends Object> T json2Bean(JSONObject jsonObject,
        Class<T> clazz) throws Exception {
        return json2Bean(jsonObject, clazz, null, null);
    }

    /**
     * 把JSONObject中的数据填充到entity中.
     *
     * @param <T> Object
     * @param jsonObject json对象
     * @param entity 需要填充数据的node
     * @param excludes 不需要转换的属性数组
     * @param datePattern 日期转换模式
     * @return T
     * @throws Exception java.lang.InstantiationException,
     *                   java.beans.IntrospectionException,
     *                   java.lang.IllegalAccessException
     */
    public static <T extends Object> T json2Bean(JSONObject jsonObject,
        T entity, String[] excludes, String datePattern)
        throws Exception {
        // JsonUtils.configJson(excludes, datePattern);
        Set<String> excludeSet = createExcludeSet(excludes);

        for (Object object : jsonObject.entrySet()) {
            Map.Entry entry = (Map.Entry) object;
            String propertyName = entry.getKey().toString();

            if (excludeSet.contains(propertyName)) {
                continue;
            }

            String propertyValue = entry.getValue().toString();

            try {
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(propertyName,
                        entity.getClass());
                Class propertyType = propertyDescriptor.getPropertyType();

                Method writeMethod = propertyDescriptor.getWriteMethod();
                invokeWriteMethod(entity, writeMethod, propertyType,
                    propertyValue, datePattern);
            } catch (IntrospectionException ex) {
                logger.warn(ex);

                continue;
            } catch (NumberFormatException ex) {
                logger.warn(ex);

                continue;
            } catch (ParseException ex) {
                logger.warn(ex);

                continue;
            }
        }

        return entity;
    }

    /**
     * 配置排除列表.
     *
     * @param excludes String[]
     * @return exclude set
     */
    public static Set<String> createExcludeSet(String[] excludes) {
        Set<String> excludeSet = new HashSet<String>();

        if (excludes != null) {
            for (String exclude : excludes) {
                excludeSet.add(exclude);
            }
        } else {
            excludeSet.add("hibernateLazyInitializer");
        }

        return excludeSet;
    }

    /**
     * 根据类型，反射调用setter方法.
     *
     * @param entity 实例
     * @param writeMethod setter方法
     * @param propertyType 数据类型
     * @param propertyValue 数据值
     * @param datePattern 日期格式
     * @throws Exception 异常
     */
    public static void invokeWriteMethod(Object entity,
        Method writeMethod, Class propertyType, String propertyValue,
        String datePattern) throws Exception {
        if (isPrimivite(propertyType)) {
            invokePrimivite(entity, writeMethod, propertyType,
                propertyValue);
        } else if (propertyType == String.class) {
            writeMethod.invoke(entity, propertyValue);
        } else if (propertyType == Date.class) {
            SimpleDateFormat dateFormat = getDateFormat(datePattern);

            writeMethod.invoke(entity, dateFormat.parse(propertyValue));
        }
    }

    /**
     * 处理基本类型.
     *
     * @param entity 实例
     * @param writeMethod setter方法
     * @param propertyType 数据类型
     * @param propertyValue 数据值
     * @throws Exception 异常
     */
    public static void invokePrimivite(Object entity, Method writeMethod,
        Class propertyType, String propertyValue) throws Exception {
        if (isByte(propertyType)) {
            writeMethod.invoke(entity, Byte.parseByte(propertyValue));
        } else if (isShort(propertyType)) {
            writeMethod.invoke(entity, Short.parseShort(propertyValue));
        } else if (isInt(propertyType)) {
            writeMethod.invoke(entity, Integer.parseInt(propertyValue));
        } else if (isLong(propertyType)) {
            writeMethod.invoke(entity, Long.parseLong(propertyValue));
        } else if (isFloat(propertyType)) {
            writeMethod.invoke(entity, Float.parseFloat(propertyValue));
        } else if (isDouble(propertyType)) {
            writeMethod.invoke(entity, Double.parseDouble(propertyValue));
        } else if (isBoolean(propertyType)) {
            writeMethod.invoke(entity, Boolean.parseBoolean(propertyValue));
        } else if (isChar(propertyType)) {
            writeMethod.invoke(entity, propertyValue.charAt(0));
        }
    }

    /**
     * 是否为八个基本类型.
     *
     * @param clazz 类型
     * @return boolean
     */
    public static boolean isPrimivite(Class clazz) {
        if (isByte(clazz)) {
            return true;
        } else if (isShort(clazz)) {
            return true;
        } else if (isInt(clazz)) {
            return true;
        } else if (isLong(clazz)) {
            return true;
        } else if (isFloat(clazz)) {
            return true;
        } else if (isDouble(clazz)) {
            return true;
        } else if (isBoolean(clazz)) {
            return true;
        } else if (isChar(clazz)) {
            return true;
        }

        return false;
    }

    /**
     * 是否为byte类型.
     *
     * @param clazz 类型
     * @return boolean
     */
    public static boolean isByte(Class clazz) {
        return (clazz == Byte.class) || (clazz == byte.class);
    }

    /**
     * 是否为short类型.
     *
     * @param clazz 类型
     * @return boolean
     */
    public static boolean isShort(Class clazz) {
        return (clazz == Short.class) || (clazz == short.class);
    }

    /**
     * 是否为int类型.
     *
     * @param clazz 类型
     * @return boolean
     */
    public static boolean isInt(Class clazz) {
        return (clazz == Integer.class) || (clazz == int.class);
    }

    /**
     * 是否为long类型.
     *
     * @param clazz 类型
     * @return boolean
     */
    public static boolean isLong(Class clazz) {
        return (clazz == Long.class) || (clazz == long.class);
    }

    /**
     * 是否为float类型.
     *
     * @param clazz 类型
     * @return boolean
     */
    public static boolean isFloat(Class clazz) {
        return (clazz == Float.class) || (clazz == float.class);
    }

    /**
     * 是否为double类型.
     *
     * @param clazz 类型
     * @return boolean
     */
    public static boolean isDouble(Class clazz) {
        return (clazz == Double.class) || (clazz == double.class);
    }

    /**
     * 是否为boolean类型.
     *
     * @param clazz 类型
     * @return boolean
     */
    public static boolean isBoolean(Class clazz) {
        return (clazz == Boolean.class) || (clazz == boolean.class);
    }

    /**
     * 是否为char类型.
     *
     * @param clazz 类型
     * @return boolean
     */
    public static boolean isChar(Class clazz) {
        return (clazz == Character.class) || (clazz == char.class);
    }

    /**
     * 获得日期格式.
     *
     * @param datePattern String
     * @return SimpleDateFormat
     */
    public static SimpleDateFormat getDateFormat(String datePattern) {
        if (datePattern == null) {
            return new SimpleDateFormat("yyyy.MM.dd'T'HH:mm:ss");
        } else {
            return new SimpleDateFormat(datePattern);
        }
    }

    /**
     * json转换成bean.
     *
     * @param jsonObject JSONObject
     * @param entity 实例
     * @param <T> 泛型
     * @return 实例
     * @throws Exception 可能抛出任何异常
     */
    public static <T extends Object> T json2Bean(JSONObject jsonObject,
        T entity) throws Exception {
        return json2Bean(jsonObject, entity, null, null);
    }

    /**
     * data=[{"id":"1"},{"id":2}]用json里的数据，创建pojo队列.
     *
     * @param <T> Object
     * @param data json字符串
     * @param clazz 需要转换成node的具体类型
     * @param excludes 不需要转换的属性数组
     * @param datePattern 日期转换模式
     * @return List
     * @throws Exception java.lang.InstantiationException,
     *                   java.beans.IntrospectionException,
     *                   java.lang.IllegalAccessException
     */
    public static <T extends Object> List<T> json2List(String data,
        Class<T> clazz, String[] excludes, String datePattern)
        throws Exception {
        JSONArray jsonArray = JSONArray.fromObject(data);

        return json2List(jsonArray, clazz, excludes, datePattern);
    }

    /**
     * json转成list列表.
     *
     * @param data json字符串
     * @param clazz 类型
     * @param <T> 泛型
     * @return 列表
     * @throws Exception 可能抛出任何异常
     */
    public static <T extends Object> List<T> json2List(String data,
        Class<T> clazz) throws Exception {
        return json2List(data, clazz, null, null);
    }

    /**
     * data=[{"id":"1"},{"id":2}]用json里的数据，创建pojo队列.
     *
     * @param <T> Object
     * @param jsonArray JSONArray
     * @param clazz 需要转换成node的具体类型
     * @param excludes 不需要转换的属性数组
     * @param datePattern 日期转换模式
     * @return List
     * @throws Exception java.lang.InstantiationException,
     *                   java.beans.IntrospectionException,
     *                   java.lang.IllegalAccessException
     */
    public static <T extends Object> List<T> json2List(
        JSONArray jsonArray, Class<T> clazz, String[] excludes,
        String datePattern) throws Exception {
        List<T> list = new ArrayList<T>();

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            T node = json2Bean(jsonObject, clazz, excludes, datePattern);
            list.add(node);
        }

        return list;
    }

    /**
     * json转成list列表.
     *
     * @param jsonArray JSONArray
     * @param clazz 类型
     * @param <T> 泛型
     * @return 列表
     * @throws Exception 可能抛出任何异常
     */
    public static <T extends Object> List<T> json2List(
        JSONArray jsonArray, Class<T> clazz) throws Exception {
        return json2List(jsonArray, clazz, null, null);
    }

    public static class Bean {
        private boolean success;
        private String msg;
        private Object info;
        private int count;//record count

        public Bean(boolean success, String msg, Object info) {
            this.success = success;
            this.msg = msg;
            this.info = info;
        }

        public boolean getSuccess() {
            return success;
        }

        public String getMsg() {
            return msg;
        }

        public Object getInfo() {
            return info;
        }
    }
}
