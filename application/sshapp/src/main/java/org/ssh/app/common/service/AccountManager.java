package org.ssh.app.common.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;
import org.ssh.app.common.dao.RoleDao;
import org.ssh.app.common.dao.UserDao;
import org.ssh.app.common.entity.Role;
import org.ssh.app.common.entity.User;
import org.ssh.app.jms.simple.NotifyMessageProducer;
import org.ssh.app.jmx.server.ServerConfig;

/**
 * 用户管理类.
 *
 * @author calvin
 */
//Spring Service Bean的标识.
@Component
//@Service("accountManager")
//默认将类中的所有函数纳入事务管理.
@Transactional
public class AccountManager {
    private static Logger logger = LoggerFactory.getLogger(AccountManager.class);

    private UserDao userDao;
    @Autowired(required = false)
    private ServerConfig serverConfig; //系统配置
    @Autowired(required = false)
    private NotifyMessageProducer notifyProducer; //JMS消息发送

    @Autowired
    private RoleDao roleDao;

    /**
     * 在保存用户时,发送用户修改通知消息, 由消息接收者异步进行较为耗时的通知邮件发送.
     *
     * 如果企图修改超级用户,取出当前操作员用户,打印其信息然后抛出异常.
     */
    public void saveUser(User user) {

        if (isSupervisor(user)) {
            logger.warn("操作员{}尝试修改超级管理员用户", SpringSecurityUtils.getCurrentUserName());
            throw new ServiceException("不能修改超级管理员用户");
        }

        PasswordEncoder encoder = new ShaPasswordEncoder();
        String shaPassword = encoder.encodePassword(user.getPlainPassword(), null);
        user.setShaPassword(shaPassword);

        saveUserToDB(user);

        sendNotifyMessage(user);
    }

    //设置Propagation, 保证在发送通知消息前数据已保存
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveUserToDB(User user) {
        userDao.save(user);
    }

    /**
     * 判断是否超级管理员.
     */
    private boolean isSupervisor(User user) {
        return (user.getId() != null && user.getId().equals("1"));
    }

    public User getUser(String id) {
        return userDao.get(id);
    }

    /**
     * 取得用户, 并对用户的延迟加载关联进行初始化.
     */
    public User getLoadedUser(String id) {
        User user = userDao.get(id);
        userDao.initUser(user);
        return user;
    }

    /**
     * 按名称查询用户, 并对用户的延迟加载关联进行初始化.
     */
    public User searchLoadedUserByName(String name) {
        User user = userDao.findUniqueBy("name", name);
        userDao.initUser(user);
        return user;
    }

    /**
     * 取得所有用户, 预加载用户的角色.
     */
    @Transactional(readOnly = true)
    public List<User> getAllUserWithRole() {
        List<User> list = userDao.getAllUserWithRoleByDistinctHql();
        logger.info("get {} user sucessful.", list.size());
        return list;
    }

    /**
     * 获取当前用户数量.
     */
    @Transactional(readOnly = true)
    public Long getUserCount() {
        return userDao.getUserCount();
    }

    @Transactional(readOnly = true)
    public User findUserByLoginName(String loginName) {
        return userDao.findUniqueBy("loginName", loginName);
    }

    /**
     * 批量修改用户状态.
     */
    public void disableUsers(List<String> ids) {
        userDao.disableUsers(ids);
    }

    /**
     * 发送用户变更消息.
     *
     * 同时发送只有一个消费者的Queue消息与发布订阅模式有多个消费者的Topic消息.
     */
    private void sendNotifyMessage(User user) {
        if (serverConfig != null && serverConfig.isNotificationMailEnabled() && notifyProducer != null) {
            try {
                notifyProducer.sendQueue(user);
                notifyProducer.sendTopic(user);
            } catch (Exception e) {
                logger.error("消息发送失败", e);
            }
        }
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    //初始
    public void initData() {
        if (this.userDao.getUserCount().longValue() != 0) {
            return;
        }

        Role r = new Role();
        r.setName("admin");
        r.setDesc("系统管理员角色");
        this.roleDao.save(r);

        List<Role>rs = new ArrayList<Role>();
        rs.add(r);

        User u = new User();
        u.setName("管理员");
        u.setLoginName("Admin");
        u.setPlainPassword("123");
        u.setEmail("admin@gmail.com");
        u.setCreateBy("初始化");
        u.setStatus("enabled");
        //add role
        u.setRoleList(rs);
        saveUser(u);

    }
}
