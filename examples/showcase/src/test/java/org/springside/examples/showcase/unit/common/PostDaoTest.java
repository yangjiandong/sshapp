package org.springside.examples.showcase.unit.common;

import static org.junit.Assert.*;

import java.util.Date;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springside.examples.showcase.common.dao.ReplyDao;
import org.springside.examples.showcase.common.dao.SubjectDao;
import org.springside.examples.showcase.common.dao.UserDao;
import org.springside.examples.showcase.common.entity.Reply;
import org.springside.examples.showcase.common.entity.Subject;
import org.springside.examples.showcase.common.entity.User;
import org.springside.modules.test.spring.SpringTxTestCase;
import org.springside.modules.test.utils.DbUnitUtils;

/**
 * PostDao的集成测试用例,测试ORM映射及特殊的DAO操作.
 * 
 * @author calvin
 */
@ContextConfiguration(locations = { "/applicationContext-test.xml" })
@TransactionConfiguration(transactionManager = "defaultTransactionManager")
public class PostDaoTest extends SpringTxTestCase {

	private static DataSource dataSourceHolder = null;

	@Autowired
	private SubjectDao subjectDao;
	@Autowired
	private ReplyDao replyDao;
	@Autowired
	private UserDao userDao;

	@Before
	public void loadDefaultData() throws Exception {
		if (dataSourceHolder == null) {
			DbUnitUtils.loadData(dataSource, "/data/default-data.xml");
			dataSourceHolder = dataSource;
		}
	}

	@AfterClass
	public static void cleanDefaultData() throws Exception {
		DbUnitUtils.removeData(dataSourceHolder, "/data/default-data.xml");
	}

	@Test
	public void getSubjectDetail() {
		Subject subject = subjectDao.getDetailWithReply("1");
		subjectDao.getSession().evict(subject);

		assertEquals(1, subject.getReplyList().size());
		assertEquals("Hello World!!", subject.getContent());
		assertEquals("Good Morning!!", subject.getReplyList().get(0).getContent());
	}

	@Test
	public void createSubject() {
		Subject subject = new Subject();
		subject.setTitle("Good Night");
		subject.setContent("Good Night!!");
		subject.setModifyTime(new Date());

		User user = userDao.get("1");
		subject.setUser(user);

		subjectDao.save(subject);
		subjectDao.flush();
		subject = subjectDao.getDetail(subject.getId());
		assertEquals("Good Night!!", subject.getContent());
	}

	@Test
	public void updateSubject() {
		Subject subject = subjectDao.getDetail("1");
		subject.setTitle("Good Afternoon");
		subject.setContent("Good Afternoon!!!");
		subject.setModifyTime(new Date());

		subjectDao.save(subject);
		subjectDao.flush();
		subject = subjectDao.getDetail(subject.getId());
		assertEquals("Good Afternoon!!!", subject.getContent());
	}

	@Test
	public void deleteSubject() {
		subjectDao.delete("1");
		subjectDao.flush();
		Subject subject = subjectDao.findUniqueBy("id", "1");
		assertNull(subject);
	}

	@Test
	public void createReply() {
		Reply reply = new Reply();
		reply.setTitle("GoodAfternoon");
		reply.setContent("Good Afternoon!!!");
		reply.setModifyTime(new Date());

		User user = userDao.get("1");
		reply.setUser(user);

		Subject subject = subjectDao.get("1");
		reply.setSubject(subject);

		replyDao.save(reply);
		replyDao.flush();
	}

	@Test
	public void updateReply() {
		Reply reply = replyDao.getDetail("2");
		reply.setTitle("GoodEvening");
		reply.setContent("Good Evening!!!");

		replyDao.save(reply);
		replyDao.flush();
	}

	@Test
	public void deleteReply() {
		replyDao.delete("2");
	}
}
