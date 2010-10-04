package org.ssh.app.example.entity;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 用户.
 *
 */
public class UserVO {
    private String loginName;
    private String plainPassword;
    private String shaPassword;
    private String name;
    private String email;
    private String status;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * 演示用明文密码.
     */
    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    /**
     * 演示用SHA1散列密码.
     */
    public String getShaPassword() {
        return shaPassword;
    }

    public void setShaPassword(String shaPassword) {
        this.shaPassword = shaPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
