import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnection {
    public static void main(String[] args) {
        try {
            // Step 1: Load driver (optional in newer versions, but good practice)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step 2: Establish connection
            String url = "jdbc:mysql://localhost:3306/studentldb";
            String user = "root";   // your MySQL username
            String password = "suguna@26boddu"; // your MySQL password

            Connection conn = DriverManager.getConnection(url, user, password);

            if (conn != null) {
                System.out.println("✅ Connection Successful!");
                conn.close();
            } else {
                System.out.println("❌ Connection Failed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
