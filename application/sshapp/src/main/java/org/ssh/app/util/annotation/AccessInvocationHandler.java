package org.ssh.app.util.annotation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.security.AccessControlException;
import java.util.Arrays;

//例子
public class AccessInvocationHandler<T> implements InvocationHandler {
    final T accessObj;
    public AccessInvocationHandler(T accessObj) {
        this.accessObj = accessObj;
    }
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RequiredRoles annotation = method.getAnnotation(RequiredRoles.class); //通过反射API获取注解
        if (annotation != null) {
            String[] roles = annotation.value();
            //String role = AccessControl.getCurrentRole();
            String role = "admin";
            if (!Arrays.asList(roles).contains(role)) {
                throw new AccessControlException("该用户每权限访问该方法.");
            }
        }
        return method.invoke(accessObj, args);
    }
}
