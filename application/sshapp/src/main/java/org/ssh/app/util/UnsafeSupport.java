package org.ssh.app.util;

/*
 * Copyright 2010, the original author or authors.  All rights reserved.
 */

import java.lang.reflect.Field;
import org.apache.log4j.Logger;
import sun.misc.Unsafe;

/**
 * 获取{@link sun.misc.Unsafe}实例<br>
 * 从rt.jar包中反编译该类获取的信息
 *
 * <pre>
 *
 * package sun.misc;
 *
 *  import java.lang.reflect.Field;
 *  import java.lang.reflect.Modifier;
 *  import java.security.ProtectionDomain;
 *  import sun.reflect.Reflection;
 *
 *  public final class Unsafe
 *  {
 *  private static final Unsafe theUnsafe;
 *  public static final int INVALID_FIELD_OFFSET = -1;
 *
 *  private static native void registerNatives();
 *
 *  public static Unsafe getUnsafe()
 *  {
 *  Class localClass = Reflection.getCallerClass(2);
 *  if (localClass.getClassLoader() != null)
 *  throw new SecurityException(&quot;Unsafe&quot;);
 *  return theUnsafe;
 *  }
 * static
 *  {
 *  registerNatives();
 *
 *  theUnsafe = new Unsafe();
 *  }
 * ..........................
 * </pre>
 *
 * @author <a href="mailto:xiao_jiang51@163.com">xiao jiang</a>
 * @version %I%, %G%
 * @history 2010-12-23
 */
public class UnsafeSupport {
  private static Logger log = Logger.getLogger(UnsafeSupport.class);

  private static Unsafe unsafe;

  static {
    Field field;
    try {
      // 由反编译Unsafe类获得的信息
      field = Unsafe.class.getDeclaredField("theUnsafe");
      field.setAccessible(true);
      // 获取静态属性,Unsafe在启动JVM时随rt.jar装载
      unsafe = (Unsafe) field.get(null);
    } catch (Exception e) {
      log.error("Get Unsafe instance occur error", e);
    }
  }

  /**
   * 获取{@link Unsafe }
   */
  public static Unsafe getInstance() {
    return unsafe;
  }

  public static void main(String[] args) {

  }
}