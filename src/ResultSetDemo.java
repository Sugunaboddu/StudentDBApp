import java.sql.*;

public class ResultSetDemo {
    public static void main(String[] args) {
        try {
            // 1. Load Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Connect to DB
            String url = "jdbc:mysql://localhost:3306/studentldb";
            String user = "root";
            String pass = "suguna@26boddu";
            Connection con = DriverManager.getConnection(url, user, pass);

            // 3. Create Statement with scroll + update
            Statement stmt = con.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE
            );

            // 4. Execute Query
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");

            // 5. Print in table format
            System.out.println("ID\tName\tAge");
            System.out.println("-------------------------");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" +
                                   rs.getString("name") + "\t" +
                                   rs.getInt("age"));
            }

            // 6. Scroll back to first row
            rs.first();
            System.out.println("\nFirst Row -> " + rs.getString("name"));

            // 7. Update second row
            rs.absolute(2); // move to 2nd row
            rs.updateString("name", "priya");
            rs.updateInt("age", 25);
            rs.updateRow(); // apply changes
            System.out.println("Row 2 updated successfully!");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
