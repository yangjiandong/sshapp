package org.springside.examples.miniservice.dao.account;

import org.springframework.stereotype.Component;
import org.springside.examples.miniservice.entity.account.Role;
import org.springside.modules.orm.hibernate.HibernateDao;

/**
 * 角色对象的泛型DAO.
 * 
 * @author calvin
 */
@Component
public class RoleDao extends HibernateDao<Role, Long> {
}
