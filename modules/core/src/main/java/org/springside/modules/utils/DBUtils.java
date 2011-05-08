package org.springside.modules.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

@SuppressWarnings("deprecation")
public final class DBUtils {

    private static final Log logger = LogFactory.getLog(DBUtils.class);

    /**
     * 调用存储过程，获取某个数据表的自增长属性Oid的值
     *
     * @param tableName
     */
    public static Long getSysOid(Session session, String tableName) {
        Long result = 0L;
        Connection conn = null;
        CallableStatement cstmt = null;

        try {
            conn = session.connection();
            conn.setAutoCommit(false);
            String procedure = "{call  getsysid(?,?) }";
            cstmt = conn.prepareCall(procedure);
            cstmt.setString(1, tableName);
            cstmt.registerOutParameter(2, Types.BIGINT);
            cstmt.executeUpdate();
            result = Long.valueOf(cstmt.getString(2));
            conn.commit();
        } catch (SQLException e) {
            logger.error("获取自增长序号失败：" + e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (cstmt != null) {
                    cstmt.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

//    /**
//     * 判断当前Sesseion连接的数据库是否为HSQL数据库
//     * */
//    public static boolean isHSQL(Session session) throws SQLException {
//        Connection conn = session.connection();
//        return isHSQL(conn);
//    }

    /**
     * 判断当前Connection连接的数据库是否为HSQL数据库
     */
    public static boolean isHSQL(Connection conn) throws SQLException {
        String databaseName = conn.getMetaData().getDatabaseProductName();

        if ("HSQL Database Engine".equals(databaseName)) {
            return true;
        } else {
            return false;
        }
    }

//    /**
//     * 判断当前Sesseion连接的数据库是否为H2数据库
//     * */
//    public static boolean isH2(Session session) throws SQLException {
//        Connection conn = session.connection();
//        return isH2(conn);
//    }

    /**
     * 判断当前Connection连接的数据库是否为H2数据库
     */
    public static boolean isH2(Connection conn) throws SQLException {
        String databaseName = conn.getMetaData().getDatabaseProductName();

        if ("H2".equals(databaseName)) {
            return true;
        } else {
            return false;
        }
    }

//    /**
//     * 判断当前Sesseion连接的数据库是否为MySql数据库
//     * */
//    public static boolean isMySql(Session session) throws SQLException {
//        Connection conn = session.connection();
//        return isMySql(conn);
//    }

    /**
     * 判断当前Connection连接的数据库是否为MySql数据库
     */
    public static boolean isMySql(Connection conn) throws SQLException {
        String databaseName = conn.getMetaData().getDatabaseProductName();

        if ("MySQL".equals(databaseName)) {
            return true;
        } else {
            return false;
        }
    }

//    /**
//     * 判断当前Sesseion连接的数据库是否为PostgreSQL数据库
//     * */
//    public static boolean isPostgreSQL(Session session) throws SQLException {
//        Connection conn = session.connection();
//        return isPostgreSQL(conn);
//    }

    /**
     * 判断当前Connection连接的数据库是否为PostgreSQL数据库
     */
    public static boolean isPostgreSQL(Connection conn) throws SQLException {
        String databaseName = conn.getMetaData().getDatabaseProductName();

        if ("PostgreSQL".equals(databaseName)) {
            return true;
        } else {
            return false;
        }
    }

//    /**
//     * 判断当前Sesseion连接的数据库是否为Derby数据库
//     * */
//    public static boolean isDerby(Session session) throws SQLException {
//        Connection conn = session.connection();
//        return isDerby(conn);
//    }

    /**
     * 判断当前Connection连接的数据库是否为Derby数据库
     */
    public static boolean isDerby(Connection conn) throws SQLException {
        String databaseName = conn.getMetaData().getDatabaseProductName();

        if ("Apache Derby".equals(databaseName)) {
            return true;
        } else {
            return false;
        }
    }

//    /**
//     * 判断当前Sesseion连接的数据库是否为ingres数据库
//     * */
//    public static boolean isIngres(Session session) throws SQLException {
//        Connection conn = session.connection();
//        return isIngres(conn);
//    }

    /**
     * 判断当前Connection连接的数据库是否为ingres数据库
     */
    public static boolean isIngres(Connection conn) throws SQLException {
        String databaseName = conn.getMetaData().getDatabaseProductName();

        if ("ingres".equalsIgnoreCase(databaseName)) {
            return true;
        } else {
            return false;
        }
    }

//    /**
//     * 判断当前Sesseion连接的数据库是否为Microsoft SQL Server数据库
//     * */
//    public static boolean isMSSqlServer(Session session) throws SQLException {
//        Connection conn = session.connection();
//        return isMSSqlServer(conn);
//    }

    /**
     * 判断当前Connection连接的数据库是否为Microsoft SQL Server数据库
     */
    public static boolean isMSSqlServer(Connection conn) throws SQLException {
        String databaseName = conn.getMetaData().getDatabaseProductName();

        if (databaseName.startsWith("Microsoft SQL Server")) {
            return true;
        } else {
            return false;
        }
    }

//    /**
//     * 判断当前Sesseion连接的数据库是否为Sybase数据库
//     * */
//    public static boolean isSybase(Session session) throws SQLException {
//        Connection conn = session.connection();
//        return isSybase(conn);
//    }

    /**
     * 判断当前Connection连接的数据库是否为Sybase数据库
     */
    public static boolean isSybase(Connection conn) throws SQLException {
        String databaseName = conn.getMetaData().getDatabaseProductName();

        if ("Sybase SQL Server".equals(databaseName) || "Adaptive Server Enterprise".equals(databaseName)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断当前Connection连接的数据库是否为Informix数据库
     */
    public static boolean isInformix(Connection conn) throws SQLException {
        String databaseName = conn.getMetaData().getDatabaseProductName();

        if ("Informix Dynamic Server".equals(databaseName)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断当前Connection连接的数据库是否为DB2数据库
     */
    public static boolean isDB2(Connection conn) throws SQLException {
        String databaseName = conn.getMetaData().getDatabaseProductName();

        if (databaseName.startsWith("DB2/")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断当前Connection连接的数据库是否为Oracle数据库
     */
    public static boolean isOracle(Connection conn) throws SQLException {
        String databaseName = conn.getMetaData().getDatabaseProductName();

        if ("Oracle".equals(databaseName)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * 传入连接来执行 SQL 脚本文件，这样可与其外的数据库操作同处一个事务中
     *
     * @param conn
     *            传入数据库连接
     *
     * @param sqlFile
     *            SQL 脚本文件(sql文件中允许含有单行注释，不允许含有多行的注释块)
     *
     * @throws Exception
     */
    public static void execute(Connection conn, String sqlFile) throws Exception {
        Statement stmt = null;

        String buf = null;
        BufferedReader in = null;

        try {
            String lineSep = System.getProperty("line.separator");

            stmt = conn.createStatement();

            in = new BufferedReader(new FileReader(sqlFile));
            buf = in.readLine();

            while (buf != null) {
                stmt.addBatch(buf + lineSep);
                buf = in.readLine();
            }

            stmt.executeBatch();
        } catch (Exception ex) {
            logger.error("执行sql文件[" + sqlFile + "]失败" + ex.getMessage());
            throw new Exception(ex.getMessage());
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    /**
     * 判断某数据库中的存储过程是否存在
     *
     * meta.getProcedures(catalog,schemaPattern,procedureNamePattern)
     *
     * 其中:<BR/>
     * catalog:为数据库名(为null时，表示所有的数据库)<BR/>
     * schemaPattern:为数据库角色(为null时，表示所有的数据库角色)<BR/>
     * procedureNamePattern:为存储过程名(若设置为"%", 表示所有的存储过程)
     */
    public static boolean checkSpExisted(Connection conn, String dbName, String spName) throws Exception {
        boolean exist = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            if (conn != null) {
                if (DBUtils.isMSSqlServer(conn)) {
                    String sql = "select * from sysobjects where id=object_id(?) and xtype=?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, spName);
                    pstmt.setString(2, "P");
                    rs = pstmt.executeQuery();
                    if (rs.next()) {
                        exist = true;
                    }

                } else {
                    DatabaseMetaData meta = conn.getMetaData();

                    rs = meta.getProcedures(dbName, "dbo", spName);

                    while (rs.next()) {
                        String proceName = rs.getString("PROCEDURE_NAME");

                        if (StringUtils.equalsIgnoreCase(proceName, spName)) {
                            exist = true;
                            break;
                        }
                    }
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("检查存储过程是否存在失败");
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }

                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return exist;
    }
}
