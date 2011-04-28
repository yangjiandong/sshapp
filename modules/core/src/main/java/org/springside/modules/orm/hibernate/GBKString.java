package org.springside.modules.orm.hibernate;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

//import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.lang.SerializationException;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

/**
 *用友解决Oracle数据库字符集为US7ASCII(字符编码为ISO-8859-1)时的中文乱码情况
 *
 *用法：在相应的dbo类的get方法上，加@Type(type="com.ekingsoft.core.orm.hibernate.GBKString")注释
 *
 *在直接用sql语句的hibernate SQLQuery查询中，查询条件和查询结果，需要进行转码处理，本类不能直接处理
 *
 */
public class GBKString implements UserType {

    public GBKString() {
        super();
    }

    public int[] sqlTypes() {
        //return new int[] { OracleTypes.VARCHAR };
        return new int[] {Types.VARCHAR};
    }

    public Class<String> returnedClass() {
        return String.class;
    }

    public boolean equals(Object x, Object y) throws HibernateException {
        return (x == y) || (x != null && y != null && (x.equals(y)));
    }

    /**
     * hibernate读取实体属性数据时调用的方法
     */
    public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
            throws HibernateException, SQLException {
        String val = rs.getString(names[0]);
        if (null == val) {
            return null;
        } else {
            try {
                val = new String(val.getBytes("ISO-8859-1"), "GBK");
            } catch (UnsupportedEncodingException e) {
                throw new HibernateException(e.getMessage());
            }
            return val;
        }
    }

    /**
     * hibernate保存实体时或查询设置过滤条件时调用的方法
     */
    public void nullSafeSet(PreparedStatement st, Object value, int index)
            throws HibernateException, SQLException {
        if (value == null) {
            //st.setNull(index, OracleTypes.VARCHAR);
            st.setNull(index, Types.VARCHAR);
        } else {
            String val = (String) value;
            try {
                val = new String(val.getBytes("GBK"), "ISO-8859-1");
            } catch (UnsupportedEncodingException e) {
                throw new HibernateException(e.getMessage());
            }
            //st.setObject(index, val, OracleTypes.VARCHAR);
            st.setObject(index, val, Types.VARCHAR);
        }
    }

    public Object deepCopy(Object value) throws HibernateException {
        if (value == null)
            return null;
        return new String((String) value);
    }

    public boolean isMutable() {
        return false;
    }

    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return deepCopy(cached);
    }

    public Serializable disassemble(Object val) throws HibernateException {
        if (val == null)
            return null;

        Object deepCopy = deepCopy(val);
        if (!(deepCopy instanceof Serializable)) {
            throw new SerializationException(val.getClass().getName() + " is not serializable ",
                    null);
        }

        return (Serializable) deepCopy;

    }

    public int hashCode(Object arg0) throws HibernateException {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return deepCopy(original);
    }
}