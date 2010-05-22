package org.springside.examples.miniweb.dao.account;

import org.springframework.stereotype.Component;
import org.springside.examples.miniweb.entity.account.Authority;
import org.springside.modules.orm.hibernate.HibernateDao;

/**
 * 授权对象的泛型DAO.
 * 
 * @author calvin
 */
@Component
public class AuthorityDao extends HibernateDao<Authority, Long> {
}
