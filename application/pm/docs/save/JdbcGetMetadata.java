
import java.sql.*;

public class JdbcGetMetadata {

    public static void main(String args[]) {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://localhost:3306/";
        String db = "komal";
        String driver = "com.mysql.jdbc.Driver";
        String user = "root";
        String pass = "root";

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url + db, user, pass);
            st = con.createStatement();

            String sql = "select * from person";
            rs = st.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();

            int rowCount = metaData.getColumnCount();

            System.out.println("Table Name : " + metaData.getTableName(2));
            System.out.println("Field  \tsize\tDataType");

            for (int i = 0; i < rowCount; i++) {
                System.out.print(metaData.getColumnName(i + 1) + "  \t");
                System.out.print(metaData.getColumnDisplaySize(i + 1) + "\t");
                System.out.println(metaData.getColumnTypeName(i + 1));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
