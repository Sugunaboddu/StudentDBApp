import java.sql.*;

public class InsertStudent {
    public static void main(String[] args) {
        try {
            // 1. Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Create connection
            String url = "jdbc:mysql://localhost:3306/studentldb";
            String username = "root";
            String password = "suguna@26boddu";  // replace with your password
            Connection con = DriverManager.getConnection(url, username, password);

            // 3. Create statement
            Statement stmt = con.createStatement();

            // 4. Write SQL query
            String sql = "INSERT INTO students(name, age) VALUES('Suguna', 22)";

            // 5. Execute query
            int rows = stmt.executeUpdate(sql);
            System.out.println(rows + " row(s) inserted.");

            // 6. Close connection
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
