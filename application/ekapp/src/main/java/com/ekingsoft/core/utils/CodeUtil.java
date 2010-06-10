package com.ekingsoft.core.utils;

import java.util.StringTokenizer;
import java.util.Vector;

/**
 * 分级代码处理实用类
 *
 */
public class CodeUtil {

    /**
     * 判断指定代码是否匹配指定模式
     *
     * @param codePattern
     *            表示代码结构，格式类似 x.y.z
     * @param code
     *            表示要检查的代码
     * @return 真表示模式匹配
     */
    public static boolean validatePattern(String codePattern, String code)
            throws Exception {
        int level = 0;
        // 指定代码的长度
        int codeLength = code.length();
        Integer[] lens = getArrayLens(codePattern);

        int levels = lens.length;
        int len = 0;

        // 将指定代码的长度与各段长度和相比较
        for (int i = 0; i <= levels - 1; i++) {
            len = lens[i].intValue() + len;
            if (codeLength == len) {
                level = i + 1;
                break;
            }
        }
        if (level == 0) {
            // throw new Exception("代码模式不匹配。");
            return false;
        } else {
            return true;
        }

    }

    /**
     * 取得指定代码的上一级代码
     *
     * @param codePattern
     *            表示代码结构，格式类似 x.y.z
     * @param code
     *            表示要检查的代码
     * @return 上一级代码
     * @throws Exception
     *             当代码模式不匹配时
     */
    public static String getCodeHighLevel(String codePattern, String code)
            throws Exception {

        if (!validatePattern(codePattern, code)) {
            throw new Exception("代码模式不匹配。");
        }

        int level = 0;
        // 指定代码的长度
        int codeLength = code.length();
        Integer[] lens = getArrayLens(codePattern);

        int firstLevelLen = lens[0].intValue();
        if (code.length() == firstLevelLen) {
            throw new Exception("没有上一级代码。");
        }

        int levels = lens.length;
        int len = 0;
        int lastLen = 0;
        String highCode = "";

        for (int i = 0; i <= levels - 1; i++) {
            lastLen = len;
            len = lens[i].intValue() + len;
            if (codeLength == len) {
                highCode = code.substring(0, lastLen);
                break;
            }
        }

        return highCode;
    }

    /**
     * 确定指定代码的级数
     *
     * @param codePattern
     *            表示代码结构，格式类似 x.y.z
     * @param code
     *            表示要检查的代码
     * @return 代码级数
     * @throws Exception
     *             当代码模式不匹配时
     */
    public static int getLevel(String codePattern, String code)
            throws Exception {
        if (!validatePattern(codePattern, code)) {
            throw new Exception("代码模式不匹配。");
        }

        int level = 0;
        // 指定代码的长度
        int codeLength = code.length();
        Integer[] lens = getArrayLens(codePattern);

        int levels = lens.length;
        int len = 0;

        // 将指定代码的长度与各段长度和相比较
        for (int i = 0; i <= levels - 1; i++) {
            len = lens[i].intValue() + len;
            if (codeLength == len) {
                level = i + 1;
                break;
            }
        }
        return level;

    }

    /**
     * 确定代码的总级数
     *
     * @param codePattern
     *            表示代码结构，格式类似 x.y.z
     * @return 代码级数
     */
    public static int getLevels(String codePattern) throws Exception {
        int levels = 0;

        StringTokenizer filter = new StringTokenizer(codePattern, ".");
        while (filter.hasMoreTokens()) {
            filter.nextToken();
            levels++;
        }

        if (levels == 0) {
            throw new Exception("代码结构不对。");
        } else {
            return levels;
        }
    }

    /**
     * 判断指定代码是否属于指定级数
     *
     * @param codePattern
     *            表示代码结构，格式类似 x.y.z
     * @param currentLevel
     *            指定级数
     * @param newCode
     *            要检查的代码
     * @return 真表示指定代码属于指定级数
     * @throws Exception
     *             当代码模式参数无效时
     */
    public static boolean isThisLevel(String codePattern, int currentLevel,
            String newCode) throws Exception {
        boolean isOk = false;

        int level = getLevel(codePattern, newCode);
        if (level == currentLevel) {
            isOk = true;
        }

        return isOk;
    }

    /**
     * 判断指定代码是否属于最后一级代码
     *
     * @param codePattern
     *            表示代码结构，格式类似 x.y.z
     * @param newCode
     *            要检查的代码
     * @return 真表示指定代码属于最后一级代码
     * @throws Exception
     *             当代码模式参数无效时
     */
    public static boolean isLastLevel(String codePattern, String newCode)
            throws Exception {
        return (getLevel(codePattern, newCode) == getLevels(codePattern));
    }

    /**
     * 判断指定代码是否属于当前代码的下一级代码
     *
     * @param codePattern
     *            表示代码结构，格式类似 x.y.z
     * @param currentCode
     *            当前代码
     * @param newCode
     *            要检查的代码
     * @return 真表示指定代码属于当前代码的下一级代码
     * @throws Exception
     *             当代码模式不匹配时
     */
    public static boolean isNextLevel(String codePattern, String currentCode,
            String newCode) throws Exception {
        // 方法是取newCode的上一级代码与currentCode比较
        boolean isOk = false;

        String highCode = getCodeHighLevel(codePattern, newCode);
        if (highCode.equals(currentCode)) {
            isOk = true;
        }

        return isOk;
    }

    /**
     * 将代码结构各段的长度保存到一个数组lens中
     *
     * @param codePatter
     * @return
     */
    public static Integer[] getArrayLens(String codePattern) throws Exception {

        StringTokenizer filter = new StringTokenizer(codePattern, ".");
        String oneFilter;
        Vector v = new Vector();
        while (filter.hasMoreTokens()) {
            oneFilter = filter.nextToken();
            v.add(new Integer(oneFilter));
        }

        Object[] tempValues = v.toArray();
        Integer[] lens = new Integer[tempValues.length];

        for (int i = 0; i < tempValues.length; i++) {
            lens[i] = (Integer) tempValues[i];
        }

        if (lens.length == 0) {
            throw new Exception("代码结构不对。");
        } else {
            return lens;
        }
    }

    /**
     * 代码模式的最长长度
     *
     * @param codePattern
     * @return
     */
    public static int getMaxLength(String codePattern) throws Exception {
        Integer[] lens = getArrayLens(codePattern);
        int levels = lens.length;
        int len = 0;

        for (int i = 0; i <= levels - 1; i++) {
            len = lens[i].intValue() + len;
        }
        return len;
    }

    /**
     * 判断传递的代码是否是上级代码
     *
     * @param code
     *            被检查的代码
     * @param superCode
     *            上级代码
     * @return
     */
    public static boolean isSuperCode(String code, String superCode) {
        int length = superCode.length();
        if (code.length() <= length) {
            return false;
        }
        boolean isSuper = false;
        String codesSuper = code.substring(0, length); // 要判断代码的上级代码
        if (codesSuper.equals(superCode)) {
            isSuper = true;
        }
        return isSuper;
    }

    /**
     * 返回代码到指定级数的代码长度
     *
     * @param codePattern
     *            代码的格式
     * @param partNo
     *            所求代码的级数
     * @return
     * @throws Exception
     */
    public static int getLength(String codePattern, int level) throws Exception {
        Integer[] lens = getArrayLens(codePattern);
        int levels = lens.length;
        int len = 0;

        if (level > levels) {
            throw new Exception("所求代码的级数大于代码级数。");
        }

        for (int i = 0; i < level; i++) {
            len = lens[i].intValue() + len;
        }
        return len;
    }

    /**
     * 取得下级代码的长度 例如：代码格式为1.3.3.3,当前代码的格式为1，则下级代码长度为3。并非下级代码的总长度
     *
     * @param codePattern
     * @param code
     * @return
     * @throws Exception
     */
    public static int getNextLevelLength(String codePattern, String code)
            throws Exception {
        Integer[] lenth = CodeUtil.getArrayLens(codePattern);
        int nextLevelLenth = lenth[CodeUtil.getLevel(codePattern, code)]
                .intValue();
        return nextLevelLenth;
    }

    /**
     * 产生指定级数的代码长度对应的下划线（用于SQL查询）
     *
     * @param codePattern
     * @param level
     * @return
     */
    public static String getUnderLines(String codePattern, int level)
            throws Exception {

        String myStr = "";
        for (int i = 0; i < getLength(codePattern, level); i++) {
            myStr += "_";
        }
        return myStr;
    }

    /**
     * 产生与指定代码长度相等的下划线（用于SQL查询）
     *
     * @param codePattern
     * @param code
     * @return
     * @throws Exception
     */
    public static String getUnderLines(String codePattern, String code)
            throws Exception {

        int level = getLevel(codePattern, code);
        return getUnderLines(codePattern, level);

    }

    /**
     * 从指定的级数开始重构代码格式 即把level前的代码格式合并
     *
     * @param codePattern
     *            :原格式
     * @param level
     *            :指定开始的级数
     * @return
     * @throws Exception
     */
    public static String getNewCodePattern(String codePattern, int level)
            throws Exception {
        String newCodePattern = "";

        level = level - 1;
        Integer[] lens = getArrayLens(codePattern);
        int levels = lens.length;
        int len = 0;
        int befLevlLen = 0;
        String oneCode;
        for (int i = 0; i <= levels - 1; i++) {
            if (i <= level) {
                befLevlLen = befLevlLen + lens[i].intValue();
            } else {
                oneCode = lens[i].toString();
                newCodePattern = newCodePattern + "." + oneCode;
            }
        }

        newCodePattern = Integer.toString(befLevlLen) + newCodePattern;
        return newCodePattern;

    }

} // EOP
