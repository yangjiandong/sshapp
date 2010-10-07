package org.ssh.app.util.leona;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.security.AccessController;
import java.security.PrivilegedAction;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;


/**
 * 扩展Apache Commons BeanUtils, 提供一些反射方面缺失功能的封装.
 */
public class BeanUtils /*extends org.apache.commons.beanutils.BeanUtils*/ {
    /** * logger. */
    private static Log logger = LogFactory.getLog(BeanUtils.class);

    /** * 保护的构造方法. */
    protected BeanUtils() {
    }

    /**
     * 循环向上转型,获取对象的DeclaredField.
     *
     * @param object 对象实例
     * @param propertyName 属性名
     * @return 返回对应的Field
     * @throws NoSuchFieldException 如果没有该Field时抛出
     */
    public static Field getDeclaredField(Object object, String propertyName)
        throws NoSuchFieldException {
        Assert.notNull(object);
        Assert.hasText(propertyName);

        return getDeclaredField(object.getClass(), propertyName);
    }

    /**
     * 循环向上转型,获取对象的DeclaredField.
     *
     * @param clazz 类型
     * @param propertyName 属性名
     * @return 返回对应的Field
     * @throws NoSuchFieldException 如果没有该Field时抛出.
     */
    public static Field getDeclaredField(Class clazz, String propertyName)
        throws NoSuchFieldException {
        Assert.notNull(clazz);
        Assert.hasText(propertyName);

        for (Class superClass = clazz; superClass != Object.class;
                superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(propertyName);
            } catch (NoSuchFieldException ex) {
                // Field不在当前类定义,继续向上转型
                System.err.println(ex);
            }
        }

        throw new NoSuchFieldException("No such field: " + clazz.getName()
            + '.' + propertyName);
    }

    /**
     * 暴力获取对象变量值,忽略private,protected修饰符的限制.
     *
     * @param object 对象实例
     * @param propertyName 属性名
     * @return 强制获得属性值
     * @throws NoSuchFieldException 如果没有该Field时抛出.
     */
    public static Object forceGetProperty(final Object object,
        final String propertyName) throws NoSuchFieldException {
        Assert.notNull(object);
        Assert.hasText(propertyName);

        final Field field = getDeclaredField(object, propertyName);

        return AccessController.doPrivileged(new PrivilegedAction() {
                /** * run. */
                public Object run() {
                    boolean accessible = field.isAccessible();
                    field.setAccessible(true);

                    Object result = null;

                    try {
                        result = field.get(object);
                    } catch (IllegalAccessException e) {
                        logger.info("error wont' happen");
                    }

                    field.setAccessible(accessible);

                    return result;
                }
            });
    }

    /**
     * 暴力设置对象变量值,忽略private,protected修饰符的限制.
     *
     * @param object 对象实例
     * @param propertyName 属性名
     * @param newValue 赋予的属性值
     * @throws NoSuchFieldException 如果没有该Field时抛出.
     */
    public static void forceSetProperty(final Object object,
        final String propertyName, final Object newValue)
        throws NoSuchFieldException {
        Assert.notNull(object);
        Assert.hasText(propertyName);

        final Field field = getDeclaredField(object, propertyName);

        AccessController.doPrivileged(new PrivilegedAction() {
                /** * run. */
                public Object run() {
                    boolean accessible = field.isAccessible();
                    field.setAccessible(true);

                    try {
                        field.set(object, newValue);
                    } catch (IllegalAccessException e) {
                        logger.info("Error won't happen");
                    }

                    field.setAccessible(accessible);

                    return null;
                }
            });
    }

    /**
     * 暴力调用对象函数,忽略private,protected修饰符的限制.
     *
     * @param object 对象实例
     * @param methodName 方法名
     * @param params 方法参数
     * @return Object 方法调用返回的结果对象
     * @throws NoSuchMethodException 如果没有该Method时抛出.
     */
    public static Object invokePrivateMethod(final Object object,
        final String methodName, final Object... params)
        throws NoSuchMethodException {
        Assert.notNull(object);
        Assert.hasText(methodName);

        Class[] types = new Class[params.length];

        for (int i = 0; i < params.length; i++) {
            types[i] = params[i].getClass();
        }

        Class clazz = object.getClass();
        Method method = null;

        for (Class superClass = clazz; superClass != Object.class;
                superClass = superClass.getSuperclass()) {
            try {
                method = superClass.getDeclaredMethod(methodName, types);

                break;
            } catch (NoSuchMethodException ex) {
                // 方法不在当前类定义,继续向上转型
                System.err.println(ex);
            }
        }

        if (method == null) {
            throw new NoSuchMethodException("No Such Method:"
                + clazz.getSimpleName() + methodName);
        }

        final Method m = method;

        return AccessController.doPrivileged(new PrivilegedAction() {
                /** * run. */
                public Object run() {
                    boolean accessible = m.isAccessible();
                    m.setAccessible(true);

                    Object result = null;

                    try {
                        result = m.invoke(object, params);
                    } catch (Exception e) {
                        ReflectionUtils.handleReflectionException(e);
                    }

                    m.setAccessible(accessible);

                    return result;
                }
            });
    }

    /**
     * 按Field的类型取得Field列表.
     *
     * @param object 对象实例
     * @param type 类型
     * @return 属性对象列表
     */
    public static List<Field> getFieldsByType(Object object, Class type) {
        List<Field> list = new ArrayList<Field>();
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.getType().isAssignableFrom(type)) {
                list.add(field);
            }
        }

        return list;
    }

    /**
     * 按FieldName获得Field的类型.
     *
     * @param type 类型
     * @param name 属性名
     * @return 属性的类型
     * @throws NoSuchFieldException 指定属性不存在时，抛出异常
     */
    public static Class getPropertyType(Class type, String name)
        throws NoSuchFieldException {
        return getDeclaredField(type, name).getType();
    }

    /**
     * 获得field的getter函数名称.
     *
     * @param type 类型
     * @param fieldName 属性名
     * @return getter方法名
     * @throws NoSuchFieldException field不存在时抛出异常
     */
    public static String getGetterName(Class type, String fieldName)
        throws NoSuchFieldException {
        Assert.notNull(type, "Type required");
        Assert.hasText(fieldName, "FieldName required");

        Class fieldType = getDeclaredField(type, fieldName).getType();

        if ((fieldType == boolean.class) || (fieldType == Boolean.class)) {
            return "is" + StringUtils.capitalize(fieldName);
        } else {
            return "get" + StringUtils.capitalize(fieldName);
        }
    }

    /**
     * 获得field的getter函数,如果找不到该方法,返回null.
     *
     * @param type 类型
     * @param fieldName 属性名
     * @return getter方法对象
     */
    public static Method getGetterMethod(Class type, String fieldName) {
        try {
            return type.getMethod(getGetterName(type, fieldName));
        } catch (NoSuchMethodException ex) {
            logger.error(ex.getMessage(), ex);
        } catch (NoSuchFieldException ex) {
            logger.error(ex.getMessage(), ex);
        }

        return null;
    }
}
