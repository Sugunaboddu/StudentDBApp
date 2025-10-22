import java.sql.*;
import java.util.Scanner;

public class CallProcedure {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            // 1. Load Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Connect to DB
            String url = "jdbc:mysql://localhost:3306/studentldb";
            String username = "root";
            String password = "suguna@26boddu";
            Connection con = DriverManager.getConnection(url, username, password);

            // 3. Take input from user
            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Age: ");
            int age = sc.nextInt();

            // 4. Call Stored Procedure
            CallableStatement cs = con.prepareCall("{ call insertStudent(?, ?) }");
            cs.setString(1, name);
            cs.setInt(2, age);

            cs.execute();
            System.out.println("✅ Student inserted successfully using procedure!");

            // 5. Close connection
            cs.close();
            con.close();
            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
