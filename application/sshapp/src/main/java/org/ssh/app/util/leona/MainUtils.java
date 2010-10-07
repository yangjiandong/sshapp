package org.ssh.app.util.leona;

import java.lang.reflect.Method;

import java.text.SimpleDateFormat;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;


/**
 * 用来复制想通过对象之间的属性的工具类.
 * 实际上，有了dozer之后，基本不需要使用它了
 *
 * @author Lingo
 */
public class MainUtils {
    /** * 默认的日期转换格式. */
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    /** 允许复制的类型. */
    private static final Set<Class> ALLOW_TYPES;

    static {
        Set<Class> set = new HashSet<Class>();
        set.add(byte.class);
        set.add(Byte.class);
        set.add(short.class);
        set.add(Short.class);
        set.add(int.class);
        set.add(Integer.class);
        set.add(long.class);
        set.add(Long.class);
        set.add(float.class);
        set.add(Float.class);
        set.add(double.class);
        set.add(Double.class);
        set.add(char.class);
        set.add(Character.class);
        set.add(boolean.class);
        set.add(Boolean.class);
        set.add(String.class);
        set.add(Date.class);

        ALLOW_TYPES = Collections.unmodifiableSet(set);
    }

    /** setter方法对应的字段名，开始的位置. */
    public static final int SET_START = "set".length();

    /** setter方法对应的字段名，第一个字母结束的位置. */
    public static final int SET_END = SET_START + 1;

    /** is方法对应的字段名，开始的为位置. */
    public static final int IS_START = "is".length();

    /** is方法对应的字段名，第一个字母结束的位置. */
    public static final int IS_END = IS_START + 1;

    /** * 保护的构造方法. */
    protected MainUtils() {
    }

    /**
     * Byte.
     *
     * @param value 需要转换的字符串
     * @param defaultValue 默认值
     * @return 转换结果
     */
    public static Byte getByte(String value, Byte defaultValue) {
        try {
            return Byte.valueOf(value);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * Byte.
     *
     * @param value 需要转换的字符串
     * @return 转换结果
     */
    public static Byte getByte(String value) {
        return getByte(value, null);
    }

    /**
     * Short.
     *
     * @param value 需要转换的字符串
     * @param defaultValue 默认值
     * @return 转换结果
     */
    public static Short getShort(String value, Short defaultValue) {
        try {
            return Short.valueOf(value);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * Short.
     *
     * @param value 需要转换的字符串
     * @return 转换结果
     */
    public static Short getShort(String value) {
        return getShort(value, null);
    }

    /**
     * Integer.
     *
     * @param value 需要转换的字符串
     * @param defaultValue 默认值
     * @return 转换结果
     */
    public static Integer getInt(String value, Integer defaultValue) {
        try {
            return Integer.valueOf(value);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * Integer.
     *
     * @param value 需要转换的字符串
     * @return 转换结果
     */
    public static Integer getInt(String value) {
        return getInt(value, null);
    }

    /**
     * Long.
     *
     * @param value 需要转换的字符串
     * @param defaultValue 默认值
     * @return 转换结果
     */
    public static Long getLong(String value, Long defaultValue) {
        try {
            return Long.valueOf(value);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * Long.
     *
     * @param value 需要转换的字符串
     * @return 转换结果
     */
    public static Long getLong(String value) {
        return getLong(value, null);
    }

    /**
     * Float.
     *
     * @param value 需要转换的字符串
     * @param defaultValue 默认值
     * @return 转换结果
     */
    public static Float getFloat(String value, Float defaultValue) {
        try {
            return Float.valueOf(value);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * Float.
     *
     * @param value 需要转换的字符串
     * @return 转换结果
     */
    public static Float getFloat(String value) {
        return getFloat(value, null);
    }

    /**
     * Double.
     *
     * @param value 需要转换的字符串
     * @param defaultValue 默认值
     * @return 转换结果
     */
    public static Double getDouble(String value, Double defaultValue) {
        try {
            return Double.valueOf(value);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * Double.
     *
     * @param value 需要转换的字符串
     * @return 转换结果
     */
    public static Double getDouble(String value) {
        return getDouble(value, null);
    }

    /**
     * Character.
     *
     * @param value 需要转换的字符串
     * @param defaultValue 默认值
     * @return 转换结果
     */
    public static Character getChar(String value, Character defaultValue) {
        try {
            return Character.valueOf(value.charAt(0));
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * Character.
     *
     * @param value 需要转换的字符串
     * @return 转换结果
     */
    public static Character getChar(String value) {
        return getChar(value, null);
    }

    /**
     * Boolean.
     * 本以为Boolean.valueOf()方法只能解析true,false的字符串
     * 后来发现，只要字符串不是true，一律都会返回false，所以也没办法设置默认值了
     *
     * @param value 需要转换的字符串
     * @return 转换结果
     */
    public static Boolean getBoolean(String value) {
        return Boolean.valueOf(value);
    }

    /**
     * Date.
     *
     * @param value 需要转换的字符串
     * @param defaultValue 默认值
     * @param datePattern 日期格式
     * @return 转换结果
     */
    public static Date getDate(String value, Date defaultValue,
        String datePattern) {
        try {
            return new SimpleDateFormat(datePattern).parse(value);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * Date.
     *
     * @param value 需要转换的字符串
     * @param defaultValue 默认值
     * @return 转换结果
     */
    public static Date getDate(String value, Date defaultValue) {
        return getDate(value, defaultValue, DEFAULT_DATE_PATTERN);
    }

    /**
     * Date.
     *
     * @param value 需要转换的字符串
     * @return 转换结果
     */
    public static Date getDate(String value) {
        return getDate(value, null);
    }

    /**
     * 将货币类型的字符串，转换成小数.
     *
     * @param value String
     * @return double
     */
    public static double getCurrency(String value) {
        return getCurrency(value, 0D);
    }

    /**
     * 将货币类型的字符串，转换成小数.
     *
     * @param value String
     * @param defaultValue double
     * @return double
     */
    public static double getCurrency(String value, double defaultValue) {
        try {
            return Double.parseDouble(value.replaceAll("[￥,]", ""));
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * copy from src to dest.
     *
     * @param src 从哪里复制数据
     * @param dest 把数据复制到哪里
     */
    public static void copyAll(Object src, Object dest) {
        Class srcClass = src.getClass();
        Class destClass = dest.getClass();

        //System.out.println(" ==");
        //System.out.println(srcClass);
        //System.out.println(destClass);
        for (Method method : srcClass.getMethods()) {
            //System.out.println(method);
            if (method.getName().equals("getClass")) {
                continue;
            }

            if (isGetter(method)) {
                //System.out.println("is getter: " + method);
                try {
                    //System.out.println("start");

                    //
                    String methodName = getter2Setter(method.getName());

                    //System.out.println(methodName);
                    Class methodParam = method.getReturnType();

                    //System.out.println(methodParam);
                    Method setter = destClass.getMethod(methodName,
                            methodParam);

                    //System.out.println(setter);

                    //
                    Object result = method.invoke(src);

                    //System.out.println(result);
                    if (result == null) {
                        continue;
                    } else if (result instanceof Collection) {
                        if (((Collection) result).isEmpty()) {
                            continue;
                        }
                    } else if (isEmptyArray(result)) {
                        continue;
                    }

                    //
                    setter.invoke(dest, result);
                } catch (Throwable ex) {
                    //ex.printStackTrace();
                    System.err.println(ex);
                }
            }

            //System.out.println(" ////");
        }
    }

    /**
     * 判断是否为一个空数组.
     *
     * @param result Object
     * @return 是否为空数组
     */
    public static boolean isEmptyArray(Object result) {
        if (result.getClass().isArray()) {
            if (result instanceof byte[]) {
                return (((byte[]) result).length == 0);
            } else if (result instanceof short[]) {
                return (((short[]) result).length == 0);
            } else if (result instanceof int[]) {
                return (((int[]) result).length == 0);
            } else if (result instanceof long[]) {
                return (((long[]) result).length == 0);
            } else if (result instanceof float[]) {
                return (((float[]) result).length == 0);
            } else if (result instanceof double[]) {
                return (((double[]) result).length == 0);
            } else if (result instanceof char[]) {
                return (((char[]) result).length == 0);
            } else if (result instanceof boolean[]) {
                return (((boolean[]) result).length == 0);
            } else {
                //result instanceof Object[]
                return (((Object[]) result).length == 0);
            }
        }

        return false;
    }

    /**
     * 只复制8个基本类型和String, Date.
     *
     * @param src Object
     * @param dest Object
     */
    public static void copy(Object src, Object dest) {
        Class srcClass = src.getClass();
        Class destClass = dest.getClass();

        for (Method method : srcClass.getMethods()) {
            Class returnType = method.getReturnType();

            if (!ALLOW_TYPES.contains(returnType)) {
                continue;
            }

            if (isGetter(method)) {
                try {
                    Object result = method.invoke(src);

                    if (result == null) {
                        continue;
                    }

                    String methodName = getter2Setter(method.getName());
                    Method setter = destClass.getMethod(methodName,
                            returnType);
                    setter.invoke(dest, result);
                } catch (NoSuchMethodException ex) {
                    // ignore
                    continue;
                } catch (Throwable ex) {
                    System.err.println(ex);
                }
            }
        }
    }

    /**
     * 方法名转换成属性名.
     *
     * @param methodName 方法名
     * @return 属性名
     */
    public static String m2f(String methodName) {
        if (methodName.startsWith("set") || methodName.startsWith("get")) {
            return methodName.substring(SET_START, SET_END)
                             .toLowerCase(Locale.CHINA)
            + methodName.substring(SET_END);
        } else if (methodName.startsWith("is")) {
            return methodName.substring(IS_START, IS_END)
                             .toLowerCase(Locale.CHINA)
            + methodName.substring(IS_END);
        } else {
            throw new IllegalArgumentException(
                "method not start with get or is.");
        }
    }

    /**
     * getter方法名转换成setter方法名.
     *
     * @param methodName getter方法名
     * @return setter方法名
     */
    public static String getter2Setter(String methodName) {
        if (methodName.startsWith("get")) {
            return "s" + methodName.substring(1);
        } else if (methodName.startsWith("is")) {
            return "set" + methodName.substring(2);
        } else {
            throw new IllegalArgumentException(
                "method not start with get or is.");
        }
    }

    /**
     * 判断方法名是否符合setter.
     * 1.方法名是3个字符以上
     * 2.方法名以set开头
     * 3.方法只有一个参数
     * 满足这三个条件，就被认为是setter
     *
     * @param method 方法
     * @return boolean
     */
    public static boolean isSetter(Method method) {
        String name = method.getName();
        boolean hasOneParam = method.getParameterTypes().length == 1;
        boolean startsWithGet = (name.length() > SET_START)
            && name.startsWith("set");

        return startsWithGet && hasOneParam;
    }

    /**
     * 判断方法名是否符合getter.
     * 1.方法名以get开头，并在3个字符以上
     * 2.方法名以is开头，并在2个字符以上
     * 3.方法没有参数
     * 满足这三个条件，就被认为是getter
     *
     * @param method 方法
     * @return boolean
     */
    public static boolean isGetter(Method method) {
        String name = method.getName();
        boolean hasNoParam = method.getParameterTypes().length == 0;
        boolean startsWithGet = (name.length() > SET_START)
            && name.startsWith("get");
        boolean startsWithIs = (name.length() > IS_START)
            && name.startsWith("is");

        return hasNoParam && (startsWithGet || startsWithIs);
    }
}
