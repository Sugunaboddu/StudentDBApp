import java.sql.*;

public class TransactionDemo {
    public static void main(String[] args) {
        try {
            // 1. Connect to DB
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/studentldb", "root", "suguna@26boddu");

            // 2. Turn off auto-commit
            con.setAutoCommit(false);

            // 3. Insert into students
            PreparedStatement ps1 = con.prepareStatement(
                "INSERT INTO students(name, age) VALUES(?, ?)");
            ps1.setString(1, "Ravi");
            ps1.setInt(2, 22);
            ps1.executeUpdate();

            // 4. Insert into marks
            PreparedStatement ps2 = con.prepareStatement(
                "INSERT INTO marks(name, subject, marks) VALUES(?, ?, ?)");
            ps2.setString(1, "Ravi");
            ps2.setString(2, "Maths");
            ps2.setInt(3, 95);
            ps2.executeUpdate();

            // 5. Commit
            con.commit();
            System.out.println("✅ Both inserts committed");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Error, transaction rolled back");
        }
    }
}
