package org.springside.examples.showcase.common.dao;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.springframework.stereotype.Component;
import org.springside.examples.showcase.common.entity.Subject;
import org.springside.modules.orm.hibernate.HibernateDao;

/**
 * 主题对象的泛型Hibernate Dao.
 * 
 * @author calvin
 */
@Component
public class SubjectDao extends HibernateDao<Subject, String> {

	private static final String QUERY_WITH_DETAIL = "select s from Subject s fetch all properties where s.id=?";
	private static final String QUERY_WITH_DETAIL_AND_REPLY = "select s from Subject s fetch all properties left join fetch s.replyList fetch all properties where s.id=?";

	/**
	 * 获取帖子内容.
	 */
	public Subject getDetail(String id) {
		return (Subject) findUnique(QUERY_WITH_DETAIL, id);
	}

	/**
	 * 获取帖子内容及回复.
	 */
	public Subject getDetailWithReply(String id) {
		Query query = createQuery(QUERY_WITH_DETAIL_AND_REPLY, id);
		return (Subject) distinct(query).uniqueResult();
	}

	/**
	 * 初始化帖子的内容及回复.
	 */
	public void initAllProperties(Subject subject) {
		Hibernate.initialize(subject.getReplyList());
		Hibernate.initialize(subject.getContent());
	}
}
