package org.ssh.app.util.leona;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * 调试工具.
 *
 * @author Lingo
 */
public class DebugUtils {
    /** protected constructor. */
    protected DebugUtils() {
    }

    /**
     * 把obj拼装成一个字符串.
     *
     * @param obj 需要转换的对象
     * @return 生成的字符串
     */
    public static String toString(Object obj) {
        StringBuffer buff = new StringBuffer();
        Class clazz = obj.getClass();
        buff.append("\n").append(clazz.toString()).append("\n");

        Method[] methods = clazz.getMethods();

        for (Method m : methods) {
            try {
                if (m.getName().startsWith("get")) {
                    Object result = m.invoke(obj);
                    buff.append(m.getName()).append(" : ").append(result)
                        .append("\n");
                }
            } catch (IllegalAccessException ex) {
                System.err.println(ex);
            } catch (InvocationTargetException ex) {
                System.err.println(ex);
            }
        }

        return buff.toString();
    }

    /**
     * 把obj生成的字符串打印到控制台.
     *
     * @param obj 对象
     */
    public static void info(Object obj) {
        System.out.println(" ============================== " + obj);
        System.out.println(toString(obj));
    }
}
