package org.ssh.app.httpinvoke.service;

import org.ssh.app.httpinvoke.domain.User;

public interface UserService {

    /**
     * 获得用户
     *
     * @param username
     *            用户名
     * @return
     */
    User getUser(String username);
}
