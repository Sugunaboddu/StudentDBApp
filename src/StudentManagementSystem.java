import java.sql.*;
import java.util.Scanner;

public class StudentManagementSystem {
    private static final String URL = "jdbc:mysql://localhost:3306/studentldb";
    private static final String USER = "root";
    private static final String PASS = "suguna@26boddu";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. Update Student");
            System.out.println("3. Delete Student");
            System.out.println("4. Display Students");
            System.out.println("5. Batch Insert");
            System.out.println("6. Transaction Demo");
            System.out.println("7. Metadata Info");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1: addStudent(); break;
                case 2: updateStudent(); break;
                case 3: deleteStudent(); break;
                case 4: displayStudents(); break;
                case 5: batchInsert(); break;
                case 6: transactionDemo(); break;
                case 7: metadataInfo(); break;
                case 0: System.out.println("Exiting..."); System.exit(0);
                default: System.out.println("Invalid choice, try again.");
            }
        }
    }

    private static void addStudent() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter student name: ");
        String name = sc.next();
        System.out.print("Enter student age: ");
        int age = sc.nextInt();

        String insertQuery = "INSERT INTO students (name, age) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(insertQuery)) {

            ps.setString(1, name);
            ps.setInt(2, age);

            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Student added successfully ✅");
            } else {
                System.out.println("Failed to add student ❌");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateStudent() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter student id to update: ");
        int id = sc.nextInt();
        System.out.print("Enter new name: ");
        String newName = sc.next();
        System.out.print("Enter new age: ");
        int newAge = sc.nextInt();

        String updateQuery = "UPDATE students SET name = ?, age = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(updateQuery)) {

            ps.setString(1, newName);
            ps.setInt(2, newAge);
            ps.setInt(3, id);

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Student updated successfully ✅");
            } else {
                System.out.println("No student found with the given id ❌");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteStudent() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter student id to delete: ");
        int id = sc.nextInt();

        String deleteQuery = "DELETE FROM students WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(deleteQuery)) {

            ps.setInt(1, id);

            int rowsDeleted = ps.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Student deleted successfully ✅");
            } else {
                System.out.println("No student found with the given id ❌");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayStudents() {
        String selectQuery = "SELECT * FROM students";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectQuery)) {

            System.out.println("\nID\tName\tAge");
            System.out.println("-------------------------");

            boolean found = false;
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");

                System.out.println(id + "\t" + name + "\t" + age);
                found = true;
            }

            if (!found) {
                System.out.println("No students found in the database ❌");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void batchInsert() {
        Scanner sc = new Scanner(System.in);
        System.out.print("How many students do you want to insert? ");
        int count = sc.nextInt();

        String insertQuery = "INSERT INTO students (name, age) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(insertQuery)) {

            for (int i = 1; i <= count; i++) {
                System.out.print("Enter name of student " + i + ": ");
                String name = sc.next();
                System.out.print("Enter age of student " + i + ": ");
                int age = sc.nextInt();

                ps.setString(1, name);
                ps.setInt(2, age);
                ps.addBatch();
            }

            int[] result = ps.executeBatch();
            System.out.println(result.length + " students inserted successfully ✅");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void transactionDemo() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Transaction Demo: Adding two students atomically.");

        String insertQuery = "INSERT INTO students (name, age) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(insertQuery)) {

            conn.setAutoCommit(false);  // Start transaction

            System.out.print("Enter name of first student: ");
            String name1 = sc.next();
            System.out.print("Enter age of first student: ");
            int age1 = sc.nextInt();

            ps.setString(1, name1);
            ps.setInt(2, age1);
            ps.executeUpdate();

            System.out.print("Enter name of second student: ");
            String name2 = sc.next();
            System.out.print("Enter age of second student: ");
            int age2 = sc.nextInt();

            ps.setString(1, name2);
            ps.setInt(2, age2);
            ps.executeUpdate();

            conn.commit();  // Commit transaction

            System.out.println("Both students added successfully ✅ (Transaction Committed)");

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                System.out.println("Rolling back transaction ❌");
                DriverManager.getConnection(URL, USER, PASS).rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void metadataInfo() {
        String selectQuery = "SELECT * FROM students";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectQuery)) {

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            System.out.println("\n--- Metadata Info ---");
            System.out.println("Table Column Details:");
            for (int i = 1; i <= columnCount; i++) {
                System.out.println("Column " + i + ":");
                System.out.println("  Name: " + rsmd.getColumnName(i));
                System.out.println("  Type: " + rsmd.getColumnTypeName(i));
                System.out.println("  Column Size: " + rsmd.getColumnDisplaySize(i));
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
