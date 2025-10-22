import java.sql.*;

public class Mains {
    public static void main(String[] args) {
        Connection con = null;
        try {
            // Load Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            String url = "jdbc:mysql://localhost:3306/studentldb";
            String username = "root";
            String password = "suguna@26boddu";
            con = DriverManager.getConnection(url, username, password);

            // Execute query
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM studentsss"); // table must exist!

            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2));
            }

        } catch (SQLException e) {
            System.out.println("Database error occurred!");
            System.out.println("Message: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("ErrorCode: " + e.getErrorCode());
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found!");
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
