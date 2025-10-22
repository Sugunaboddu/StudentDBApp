import java.sql.*;
import java.util.Scanner;

public class InsertStudentPS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            // 1. Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Connect to database
            String url = "jdbc:mysql://localhost:3306/studentldb";
            String username = "root";
            String password = "suguna@26boddu"; // <-- replace with your password
            Connection con = DriverManager.getConnection(url, username, password);

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Age: ");
            int age = sc.nextInt();

            // 4. Create PreparedStatement
            String sql = "INSERT INTO students(name, age) VALUES(?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            // 5. Set values
            ps.setString(1, name);
            ps.setInt(2, age);

            // 6. Execute query
            int rows = ps.executeUpdate();
            System.out.println(rows + " row(s) inserted.");

            // 7. Close resources
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }
}
