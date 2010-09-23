#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao.account;

import org.springframework.stereotype.Component;
import ${package}.entity.account.User;
import org.springside.modules.orm.hibernate.HibernateDao;

/**
 * 用户对象的泛型DAO类.
 * 
 * @author calvin
 */
@Component
public class UserDao extends HibernateDao<User, Long> {
}
