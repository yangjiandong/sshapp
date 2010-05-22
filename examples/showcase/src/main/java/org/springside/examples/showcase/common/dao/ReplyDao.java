package org.springside.examples.showcase.common.dao;

import org.springframework.stereotype.Component;
import org.springside.examples.showcase.common.entity.Reply;
import org.springside.modules.orm.hibernate.HibernateDao;

/**
 * 回复对象的泛型Hibernate Dao.
 * 
 * @author calvin
 */
@Component
public class ReplyDao extends HibernateDao<Reply, String> {

	private static final String QUERY_WITH_DETAIL = "from Reply r fetch all properties where r.id=?";

	/**
	 * 获取回复内容.
	 */
	public Reply getDetail(String id) {
		return (Reply) findUnique(QUERY_WITH_DETAIL, id);
	}
}
