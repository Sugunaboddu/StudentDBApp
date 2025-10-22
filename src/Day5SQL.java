import java.sql.*;

public class Day5SQL {
    public static void main(String[] args) {
        try {
            // 1️⃣ Load driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2️⃣ Connect to DB
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/studentldb",
                "root",
                "suguna@26boddu"   // <-- use your real password
            );

            // 3️⃣ Create Statement
            Statement stmt = con.createStatement();

            // 4️⃣ Execute SELECT query
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");

            System.out.println("ID\tName\tAge");
            while (rs.next()) {
                System.out.println(
                    rs.getInt("id") + "\t" +
                    rs.getString("name") + "\t" +
                    rs.getInt("age") 
                );
            }

            // 5️⃣ Close connection
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
