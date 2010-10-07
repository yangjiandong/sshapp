package org.ssh.app.util.leona;

import java.util.Date;


/**
 * 数组工具.
 *
 * @author Lingo
 */
public class ArrayUtils {
    /** protected constructor. */
    protected ArrayUtils() {
    }

    /**
     * 安全复制数组.
     *
     * @param array T
     * @param <T> 泛型
     * @return T
     */
    public static <T> T copyArray(T array) {
        if (array == null) {
            return null;
        }

        Class clazz = array.getClass();

        if (clazz == int[].class) {
            int len = ((int[]) array).length;
            int[] result = new int[len];
            System.arraycopy(array, 0, result, 0, len);

            return (T) result;
        } else if (clazz == Integer[].class) {
            int len = ((Integer[]) array).length;
            Integer[] result = new Integer[len];
            System.arraycopy(array, 0, result, 0, len);

            return (T) result;
        } else if (clazz == long[].class) {
            int len = ((long[]) array).length;
            long[] result = new long[len];
            System.arraycopy(array, 0, result, 0, len);

            return (T) result;
        } else if (clazz == Long[].class) {
            int len = ((Long[]) array).length;
            Long[] result = new Long[len];
            System.arraycopy(array, 0, result, 0, len);

            return (T) result;
        } else if (clazz == double[].class) {
            int len = ((double[]) array).length;
            double[] result = new double[len];
            System.arraycopy(array, 0, result, 0, len);

            return (T) result;
        } else if (clazz == Double[].class) {
            int len = ((Double[]) array).length;
            Double[] result = new Double[len];
            System.arraycopy(array, 0, result, 0, len);

            return (T) result;
        } else if (clazz == String[].class) {
            int len = ((String[]) array).length;
            String[] result = new String[len];
            System.arraycopy(array, 0, result, 0, len);

            return (T) result;
        } else if (clazz == Date[].class) {
            int len = ((Date[]) array).length;
            Date[] result = new Date[len];
            System.arraycopy(array, 0, result, 0, len);

            return (T) result;
        } else {
            throw new UnsupportedOperationException("unimplemented: "
                + clazz);
        }
    }
}
