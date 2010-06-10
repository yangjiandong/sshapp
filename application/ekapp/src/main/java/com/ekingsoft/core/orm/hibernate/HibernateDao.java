package com.ekingsoft.core.orm.hibernate;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.springframework.util.Assert;

import com.ekingsoft.core.orm.Page;
import com.ekingsoft.core.orm.PropertyFilter;
import com.ekingsoft.core.orm.PropertyFilter.MatchType;
import com.ekingsoft.core.utils.DBUtils;
import com.ekingsoft.core.utils.ReflectionUtils;
import com.ekingsoft.core.utils.UtilDateTime;

/**
 * 封装SpringSide扩展功能的Hibernat DAO泛型基类.
 * 
 * 扩展功能包括分页查询,按属性过滤条件列表查询. 可在Service层直接使用,也可以扩展泛型DAO子类使用,见两个构造函数的注释.
 * 
 * @param <T>
 *            DAO操作的对象类型
 * @param <PK>
 *            主键类型
 * 
 * @author calvin
 */
public class HibernateDao<T, PK extends Serializable> extends
		SimpleHibernateDao<T, PK> {
	/**
	 * 用于Dao层子类使用的构造函数. 通过子类的泛型定义取得对象类型Class. eg. public class UserDao extends
	 * HibernateDao<User, Long>{ }
	 */
	public HibernateDao() {
		super();
	}

	/**
	 * 用于省略Dao层, Service层直接使用通用HibernateDao的构造函数. 在构造函数中定义对象类型Class. eg.
	 * HibernateDao<User, Long> userDao = new HibernateDao<User,
	 * Long>(sessionFactory, User.class);
	 */
	public HibernateDao(final SessionFactory sessionFactory,
			final Class<T> entityClass) {
		super(sessionFactory, entityClass);
	}

	// 分页查询函数 //

	/**
	 * 分页获取全部对象.
	 */
	public Page<T> getAll(final Page<T> page) {
		return find(page);
	}

	/**
	 * 按HQL分页查询.
	 * 
	 * @param page
	 *            分页参数.不支持其中的orderBy参数.
	 * @param hql
	 *            hql语句.
	 * @param values
	 *            数量可变的查询参数,按顺序绑定.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询时的参数.
	 */
	@SuppressWarnings("unchecked")
	public Page<T> find(final Page<T> page, final String hql,
			final Object... values) {
		Assert.notNull(page, "page不能为空");

		Query q = createQuery(hql, values);
		q.setCacheable(true);

		if (page.isAutoCount()) {
			long totalCount = countHqlResult(hql, values);
			page.setTotalCount(totalCount);
		}

		setPageParameter(q, page);
		List result = q.list();
		page.setResult(result);
		return page;
	}

	/**
	 * 按HQL分页查询.
	 * 
	 * @param page
	 *            分页参数.(不支持orderBy参数)
	 * @param hql
	 *            hql语句.
	 * @param values
	 *            命名参数,按名称绑定.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询时的参数.
	 */
	@SuppressWarnings("unchecked")
	public Page<T> find(final Page<T> page, final String hql,
			final Map<String, Object> values) {
		Assert.notNull(page, "page不能为空");

		Query q = createQuery(hql, values);
		q.setCacheable(true);

		if (page.isAutoCount()) {
			long totalCount = countHqlResult(hql, values);
			page.setTotalCount(totalCount);
		}

		setPageParameter(q, page);

		List result = q.list();
		page.setResult(result);
		return page;
	}

	/**
	 * 按SQL分页查询,直接返回JSON格式的字符串结果集.
	 * 
	 * @param page
	 *            分页参数,若数据库为MS SQL SERVER，则必须初始化page的orderBy属性
	 * @param sql
	 *            sql语句.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询时的参数.
	 */

	@SuppressWarnings("deprecation")
	public Page<T> find(final Page<T> page, final String sql) {
		long totalCount = 0L;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		JSONArray jsonArray = new JSONArray();
		try {
			String countSql = "select count(*) from (" + sql + ")";

			conn = getSession().connection();
			Statement stmt = conn.createStatement();
			ResultSet rsCount = stmt.executeQuery(countSql);
			if (rsCount.next()) {
				totalCount = rsCount.getLong(1);
			}
			stmt.close();

			String query = "";

			if (DBUtils.isOracle(conn)) {
				if (page.getPageNo() == 1) {
					query = "select * from (" + sql + ") where rownum <= "
							+ page.getPageSize();
				} else {
					query = "select * from (select row_.*,rownum rownum_ from ("
							+ sql
							+ ") row_ where rownum <= "
							+ page.getPageNo()
							* page.getPageSize()
							+ ") where rownum_ > " + page.getStart();
				}

			} else if (DBUtils.isMSSqlServer(conn)) {
				if (page.getPageNo() == 1) {
					query = "select top " + page.getPageSize() + " * from ("
							+ sql + ")";
				} else {
					query = "select * from (select top " + page.getPageSize()
							+ " *  from (select top " + page.getPageSize()
							* page.getPageNo() + " * from (" + sql
							+ ") TableA) TableB order by " + page.getOrderBy()
							+ " DESC) TableC order by " + page.getOrderBy();
				}
			}

			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery(query);

			if (rs != null) {
				ResultSetMetaData md = rs.getMetaData();
				int num = md.getColumnCount();

				while (rs.next()) {
					JSONObject mapOfColValues = new JSONObject();
					for (int i = 1; i <= num; i++) {
						mapOfColValues
								.put(md.getColumnName(i), rs.getObject(i));
					}
					jsonArray.add(mapOfColValues);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		page.setTotalCount(totalCount);
		page.setJsonResult(jsonArray.toString());
		return page;
	}

	/**
	 * 按Criteria分页查询.
	 * 
	 * @param page
	 *            分页参数.
	 * @param criterions
	 *            数量可变的Criterion.
	 * 
	 * @return 分页查询结果.附带结果列表及所有查询时的参数.
	 */
	@SuppressWarnings("unchecked")
	public Page<T> find(final Page<T> page, final Criterion... criterions) {
		Assert.notNull(page, "page不能为空");

		Criteria c = createCriteria(criterions);
		c.setCacheable(true);

		if (page.isAutoCount()) {
			int totalCount = countCriteriaResult(c);
			page.setTotalCount(totalCount);
		}

		setPageParameter(c, page);
		List result = c.list();
		page.setResult(result);
		return page;
	}

	/**
	 * 设置分页参数到Query对象,辅助函数.
	 */
	protected Query setPageParameter(final Query q, final Page<T> page) {
		// hibernate的firstResult的序号从0开始
		q.setFirstResult(page.getStart());
		q.setMaxResults(page.getPageSize());
		return q;
	}

	/**
	 * 设置分页参数到Criteria对象,辅助函数.
	 */
	protected Criteria setPageParameter(final Criteria c, final Page<T> page) {
		// hibernate的firstResult的序号从0开始
		c.setFirstResult(page.getStart());
		c.setMaxResults(page.getPageSize());

		if (page.isOrderBySetted()) {
			String[] orderByArray = StringUtils.split(page.getOrderBy(), ',');
			String[] orderArray = StringUtils.split(page.getOrderDir(), ',');

			Assert.isTrue(orderByArray.length == orderArray.length,
					"分页多重排序参数中,排序字段与排序方向的个数不相等");

			for (int i = 0; i < orderByArray.length; i++) {
				if (Page.ASC.equals(orderArray[i])) {
					c.addOrder(Order.asc(orderByArray[i]));
				} else {
					c.addOrder(Order.desc(orderByArray[i]));
				}
			}
		}
		return c;
	}

	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	public long countHqlResult(final String hql, final Object... values) {
		long count = 0;
		String fromHql = hql;
		// select子句与order by子句会影响count查询,进行简单的排除.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(*) " + fromHql;

		try {
			count = findLong(countHql, values);
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:"
					+ countHql, e);
		}
		return count;
	}

	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql,
			final Map<String, Object> values) {
		long count = 0;
		String fromHql = hql;
		// select子句与order by子句会影响count查询,进行简单的排除.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(*) " + fromHql;

		try {
			count = findLong(countHql, values);
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:"
					+ countHql, e);
		}

		return count;
	}

	/**
	 * 执行count查询获得本次Criteria查询所能获得的对象总数.
	 */
	@SuppressWarnings("unchecked")
	protected int countCriteriaResult(final Criteria c) {
		CriteriaImpl impl = (CriteriaImpl) c;

		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		List<CriteriaImpl.OrderEntry> orderEntries = null;
		try {
			orderEntries = (List) ReflectionUtils.getFieldValue(impl,
					"orderEntries");
			ReflectionUtils
					.setFieldValue(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		// 执行Count查询
		int totalCount = (Integer) c.setProjection(Projections.rowCount())
				.uniqueResult();

		// 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
		c.setProjection(projection);

		if (projection == null) {
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			c.setResultTransformer(transformer);
		}
		try {
			ReflectionUtils.setFieldValue(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		return totalCount;
	}

	// 属性过滤条件查询函数 //

	/**
	 * 按属性查找对象列表,支持多种匹配方式.
	 * 
	 * @param matchType
	 *            匹配方式,目前支持的取值为"EQUAL"与"LIKE".
	 */
	public List<T> findBy(final String propertyName, final Object value,
			final MatchType matchType) {
		Criterion criterion = buildPropertyFilterCriterion(propertyName, value,
				matchType);
		return find(criterion);
	}

	/**
	 * 按属性过滤条件列表查找对象列表.
	 */
	public List<T> find(List<PropertyFilter> filters) {
		Criterion[] criterions = buildPropertyFilterCriterions(filters);
		return find(criterions);
	}

	/**
	 * 按属性过滤条件列表分页查找对象.
	 */
	public Page<T> find(final Page<T> page, final List<PropertyFilter> filters) {
		Criterion[] criterions = buildPropertyFilterCriterions(filters);
		return find(page, criterions);
	}

	/**
	 * 按属性条件列表创建Criterion数组,辅助函数.
	 */
	protected Criterion[] buildPropertyFilterCriterions(
			final List<PropertyFilter> filters) {
		List<Criterion> criterionList = new ArrayList<Criterion>();
		for (PropertyFilter filter : filters) {
			String propertyName = filter.getPropertyName();

			boolean multiProperty = StringUtils.contains(propertyName,
					PropertyFilter.OR_SEPARATOR);
			if (!multiProperty) { // properNameName中只有一个属性的情况.
				Criterion criterion = buildPropertyFilterCriterion(
						propertyName, filter.getValue(), filter.getMatchType());
				criterionList.add(criterion);
			} else {// properName中包含多个属性的情况,进行or处理.
				Disjunction disjunction = Restrictions.disjunction();
				String[] params = StringUtils.split(propertyName,
						PropertyFilter.OR_SEPARATOR);

				for (String param : params) {
					Criterion criterion = buildPropertyFilterCriterion(param,
							filter.getValue(), filter.getMatchType());
					disjunction.add(criterion);
				}
				criterionList.add(disjunction);
			}
		}
		return criterionList.toArray(new Criterion[criterionList.size()]);
	}

	/**
	 * 按属性条件参数创建Criterion,辅助函数.
	 */
	protected Criterion buildPropertyFilterCriterion(final String propertyName,
			final Object value, final MatchType matchType) {
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = null;

		if (MatchType.EQ.equals(matchType)) {
			criterion = Restrictions.eq(propertyName, value);
		}

		if (MatchType.NE.equals(matchType)) {
			criterion = Restrictions.ne(propertyName, value);
		}

		if (MatchType.GT.equals(matchType)) {
			criterion = Restrictions.gt(propertyName, value);
		}

		if (MatchType.GE.equals(matchType)) {
			criterion = Restrictions.ge(propertyName, value);
		}

		if (MatchType.LT.equals(matchType)) {
			criterion = Restrictions.lt(propertyName, value);
		}

		if (MatchType.LE.equals(matchType)) {
			criterion = Restrictions.le(propertyName, value);
		}

		if (MatchType.ISNULL.equals(matchType)) {
			criterion = Restrictions.isNull(propertyName);
		}

		if (MatchType.ISNOTNULL.equals(matchType)) {
			criterion = Restrictions.isNotNull(propertyName);
		}

		if (MatchType.LIKE.equals(matchType)) {
			criterion = Restrictions.like(propertyName, (String) value,
					MatchMode.ANYWHERE).ignoreCase();
		}

		if (MatchType.START_LIKE.equals(matchType)) {
			criterion = Restrictions.like(propertyName, (String) value,
					MatchMode.START).ignoreCase();
		}

		if (MatchType.END_LIKE.equals(matchType)) {
			criterion = Restrictions.like(propertyName, (String) value,
					MatchMode.END).ignoreCase();
		}

		return criterion;
	}

	/**
	 * 判断对象的属性值在数据库内是否唯一.
	 * 
	 * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
	 */
	public boolean isPropertyUnique(final String propertyName,
			final Object newValue, final Object oldValue) {
		if (newValue == null || newValue.equals(oldValue))
			return true;
		Object object = findByUnique(propertyName, newValue);
		return (object == null);
	}

	public String getNowString(String format) {
		String sdate = UtilDateTime.nowDateString();
		try {

			String sql = "select to_char(sysdate,'" + format
					+ "') as sys_date from dual";
			if (DBUtils.isMSSqlServer(getSession())) {
				sql = "";// TODO
			}

			Object result = getSession().createSQLQuery(sql).uniqueResult();
			if (result != null) {
				sdate = result.toString();
			}

		} catch (SQLException se) {
			logger.error(se.toString());
		}

		return sdate;
	}

	public String getNowString() {
		return getNowString("yyyy.MM.dd HH:mm:ss");
	}
}
