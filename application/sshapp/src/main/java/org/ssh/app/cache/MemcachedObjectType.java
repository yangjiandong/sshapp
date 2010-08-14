package org.ssh.app.cache;

import org.ssh.app.common.service.AccountManager;

/**
 * 统一定义Memcached中存储的各种对象的Key前缀和超时时间.
 *
 * Memcached使用在AccountManager中.
 *
 * @see AccountManager#getLoadedUser(String)
 *
 * @author calvin
 */
public enum MemcachedObjectType {
    USER("user:", 60 * 60 * 1);

    private String prefix;
    private int expiredTime;

    MemcachedObjectType(String prefix, int expiredTime) {
        this.prefix = prefix;
        this.expiredTime = expiredTime;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getExpiredTime() {
        return expiredTime;
    }

}
