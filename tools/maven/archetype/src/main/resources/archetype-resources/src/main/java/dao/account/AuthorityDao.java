#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao.account;

import org.springframework.stereotype.Component;
import ${package}.entity.account.Authority;
import org.springside.modules.orm.hibernate.HibernateDao;

/**
 * 授权对象的泛型DAO.
 * 
 * @author calvin
 */
@Component
public class AuthorityDao extends HibernateDao<Authority, Long> {
}
