package org.ssh.app.util.leona;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Generics的util类.
 * 来自www.springside.org.cn
 *
 * @author sshwsfc
 * @since 2007-03-14
 * @version 1.0
 */
public class GenericsUtils {
    /**
     * 日志.
     */
    private static final Log LOGGER = LogFactory.getLog(GenericsUtils.class);

    /**
     * 构造方法.
     */
    protected GenericsUtils() {
    }

    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型.
     * 如public BookManager extends GenricManager&lt;Book&gt;
     *
     * @param clazz The class to introspect
     * @return the first generic declaration, or <code>Object.class</code> if cannot be determined
     */
    public static Class getSuperClassGenricType(Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型.
     * 如public BookManager extends GenricManager&lt;Book&gt;
     *
     * @param clazz clazz The class to introspect
     * @param index the Index of the generic ddeclaration,start from 0.
     * @return the index generic declaration, or <code>Object.class</code> if cannot be determined
     */
    public static Class getSuperClassGenricType(Class clazz, int index) {
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            LOGGER.warn(clazz.getSimpleName()
                + "'s superclass not ParameterizedType");

            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType)
            .getActualTypeArguments();

        if ((index >= params.length) || (index < 0)) {
            LOGGER.warn("Index: " + index + ", Size of "
                + clazz.getSimpleName() + "'s Parameterized Type: "
                + params.length);

            return Object.class;
        }

        if (!(params[index] instanceof Class)) {
            LOGGER.warn(clazz.getSimpleName()
                + " not set the actual class on superclass generic parameter");

            return Object.class;
        }

        return (Class) params[index];
    }
}
