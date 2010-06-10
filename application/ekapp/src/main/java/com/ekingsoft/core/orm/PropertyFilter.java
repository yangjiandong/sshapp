package com.ekingsoft.core.orm;

/**
 * 与具体ORM实现无关的属性过滤条件封装类.
 * 
 * PropertyFilter主要记录页面中简单的搜索过滤条件,比Hibernate的Criterion要简单.
 * 
 * TODO:扩展其他对比方式如大于、小于及其他数据类型如数字和日期.
 * 
 * @author calvin
 */
public class PropertyFilter {

	/**
	 * 多个属性间OR关系的分隔符.
	 */
	public static final String OR_SEPARATOR = "_OR_";

/**
	 * 属性比较类型.<br>
	 * STARTLIKE：字符串在最前面的位置.相当于"like 'value%'". <br>
	 * ENDLIKE：字符串在最后面的位置.相当于"like '%value'". <br>
	 * LIKE：字符串在中间匹配.相当于"like '%value%'". <br>
	 * EQ：等于.相当于"=". <br>
	 * NE：不等于.相当于"<>". <br>
	 * GT：大于.相当于">". <br>
	 * GE：大于等于.相当于">=". <br>
	 * LT：小于.相当于"<". <br>
	 * LE：小于等于.相当于"<=". <br>
	 * ISNULL：等于空值. <br>
	 * ISNOTNULL：非空值. <br>
	 */
	public enum MatchType {
		START_LIKE, END_LIKE, LIKE, EQ, NE, GT, GE, LT, LE, ISNULL, ISNOTNULL;
	}

	private String propertyName;
	private Object value;
	private MatchType matchType = MatchType.EQ;

	public PropertyFilter() {
	}

	public PropertyFilter(final String propertyName, final MatchType matchType,
			final Object value) {
		this.propertyName = propertyName;
		this.matchType = matchType;
		this.value = value;
	}

	/**
	 * 获取属性名称,可用'_OR_'分隔多个属性,此时属性间是or的关系.
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * 设置属性名称,可用'_OR_'分隔多个属性,此时属性间是or的关系.
	 */
	public void setPropertyName(final String propertyName) {
		this.propertyName = propertyName;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(final Object value) {
		this.value = value;
	}

	public MatchType getMatchType() {
		return matchType;
	}

	public void setMatchType(final MatchType matchType) {
		this.matchType = matchType;
	}
}
