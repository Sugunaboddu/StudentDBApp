import java.sql.*;
import java.util.*;

public class BatchInsertDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            // 1. Load Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Connection
            String url = "jdbc:mysql://localhost:3306/studentldb";
            String user = "root";
            String pass = "suguna@26boddu"; // change to your password
            Connection con = DriverManager.getConnection(url, user, pass);

            // 3. Prepare SQL
            String sql = "INSERT INTO students(name, age) VALUES(?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            // 4. Take input for multiple students
            System.out.print("How many students you want to insert? ");
            int n = sc.nextInt();

            for (int i = 1; i <= n; i++) {
                System.out.println("Enter details for student " + i + ":");
                System.out.print("Name: ");
                String name = sc.next();
                System.out.print("Age: ");
                int age = sc.nextInt();

                // Set values
                ps.setString(1, name);
                ps.setInt(2, age);

                // Add to batch
                ps.addBatch();
            }

            // 5. Execute batch
            int[] result = ps.executeBatch();
            System.out.println(result.length+ " records inserted successfully!");

            // 6. Close
            ps.close();
            con.close();
            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
