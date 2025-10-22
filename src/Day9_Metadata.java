import java.sql.*;

public class Day9_Metadata {
    public static void main(String[] args) {
        Connection con = null;
        try {
            // 1. Load Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Connect to DB
            String url = "jdbc:mysql://localhost:3306/studentldb";
            String username = "root";
            String password = "suguna@26boddu";
            con = DriverManager.getConnection(url, username, password);

            // ================= DatabaseMetaData =================
            DatabaseMetaData dbMeta = con.getMetaData();
            System.out.println("=== Database Info ===");
            System.out.println("DB Name      : " + dbMeta.getDatabaseProductName());
            System.out.println("DB Version   : " + dbMeta.getDatabaseProductVersion());
            System.out.println("Driver Name  : " + dbMeta.getDriverName());
            System.out.println("Driver Ver.  : " + dbMeta.getDriverVersion());

            // ================= ResultSetMetaData =================
            String sql = "SELECT * FROM students"; // table we want info about
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            ResultSetMetaData rsMeta = rs.getMetaData();
            int columnCount = rsMeta.getColumnCount();

            System.out.println("\n=== Students Table Columns ===");
            for (int i = 1; i <= columnCount; i++) {
                System.out.println("Column " + i + ": " 
                                   + rsMeta.getColumnName(i) 
                                   + " (" + rsMeta.getColumnTypeName(i) + ")");
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
