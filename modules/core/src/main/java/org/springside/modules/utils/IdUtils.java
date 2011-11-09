/**
 * Copyright (c) 2005-2011 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * $Id: IdUtils.java 1595 2011-05-11 16:41:16Z calvinxiu $
 */
package org.springside.modules.utils;

import java.security.SecureRandom;
import java.util.UUID;

import org.springside.modules.utils.encode.EncodeUtils;

/**
 * 封装各种生成唯一性ID算法的工具类.
 *
 * @author calvin
 */
public abstract class IdUtils {

    private static SecureRandom random = new SecureRandom();

    /**
     * 封装JDK自带的UUID, 通过Random数字生成,中间有-分割
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 使用SecureRandom随机生成Long.
     */
    public static long randomLong() {
        return random.nextLong();
    }

    /**
     * 基于Base62编码的随机生成Long.
     */
    public static String randomBase62() {
        return EncodeUtils.encodeBase62(random.nextLong());
    }
}
