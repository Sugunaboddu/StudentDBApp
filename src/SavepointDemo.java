import java.sql.*;

public class SavepointDemo {
    public static void main(String[] args) {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/studentldb";
            String user = "root";
            String pass = "suguna@26boddu";
            con = DriverManager.getConnection(url, user, pass);

            con.setAutoCommit(false);

            // Insert English marks
            PreparedStatement ps1 = con.prepareStatement(
                "INSERT INTO marks(name, subject, marks) VALUES(?, ?, ?)");
            ps1.setString(1, "Ravi");
            ps1.setString(2, "English");
            ps1.setInt(3, 88);
            ps1.executeUpdate();

            // Savepoint after English
            Savepoint sp = con.setSavepoint("AfterEnglish");

            // Insert Science marks
            PreparedStatement ps2 = con.prepareStatement(
                "INSERT INTO marks(name, subject, marks) VALUES(?, ?, ?)");
            ps2.setString(1, "Ravi");
            ps2.setString(2, "Science");
            ps2.setInt(3, 77);
            ps2.executeUpdate();

            // Rollback only Science
            con.rollback(sp);

            con.commit();
            System.out.println("English committed, Science rolled back ✅");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
