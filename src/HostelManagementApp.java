import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HostelManagementApp {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection == null) {
                System.out.println("Unable to connect to the database.");
                return;
            }
            System.out.println("Connected to the database successfully!");

            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("\nHostel Management System");
                System.out.println("1. Add Student");
                System.out.println("2. View Students");
                System.out.println("3. Update Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1 -> addStudent(connection, scanner);
                    case 2 -> viewStudents(connection);
                    case 3 -> updateStudent(connection, scanner);
                    case 4 -> deleteStudent(connection, scanner);
                    case 5 -> running = false;
                    default -> System.out.println("Invalid option, try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addStudent(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Student Name: ");
        String studentName = scanner.nextLine();
        System.out.print("Enter Room Number: ");
        int roomNumber = scanner.nextInt();

        String sql = "INSERT INTO Students (StudentID, Name, RoomNumber) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            statement.setString(2, studentName);
            statement.setInt(3, roomNumber);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Student added successfully!");
            }
        }
    }

    private static void viewStudents(Connection connection) throws SQLException {
        String sql = "SELECT * FROM Students";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("\nStudent List:");
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("StudentID") +
                        ", Name: " + resultSet.getString("Name") +
                        ", Room: " + resultSet.getInt("RoomNumber"));
            }
        }
    }

    private static void updateStudent(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Student ID to update: ");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new name: ");
        String newName = scanner.nextLine();

        String sql = "UPDATE Students SET Name = ? WHERE StudentID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newName);
            statement.setInt(2, studentId);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Student updated successfully!");
            }
        }
    }

    private static void deleteStudent(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Student ID to delete: ");
        int studentId = scanner.nextInt();

        String sql = "DELETE FROM Students WHERE StudentID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Student deleted successfully!");
            }
        }
    }
}
