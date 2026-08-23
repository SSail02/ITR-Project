package database;

import java.sql.*;

public final class DBDriver {
    private static final String URL = "jdbc:sqlite:student-management.db";
    private DBDriver() { }
    public static Connection connect() throws SQLException {
        Connection connection = DriverManager.getConnection(URL);
        try (Statement statement = connection.createStatement()) { statement.execute("PRAGMA foreign_keys = ON"); }
        return connection;
    }
    public static void initialize() throws SQLException {
        try (Connection c = connect(); Statement s = c.createStatement()) {
            s.executeUpdate("CREATE TABLE IF NOT EXISTS students (roll_number TEXT PRIMARY KEY, name TEXT NOT NULL, branch TEXT NOT NULL, semester INTEGER NOT NULL, contact TEXT NOT NULL, email TEXT NOT NULL, date_of_birth TEXT NOT NULL)");
            s.executeUpdate("CREATE TABLE IF NOT EXISTS academic_records (roll_number TEXT NOT NULL, subject TEXT NOT NULL, internal_marks REAL NOT NULL, external_marks REAL NOT NULL, attended_lectures INTEGER NOT NULL, total_lectures INTEGER NOT NULL, PRIMARY KEY (roll_number, subject), FOREIGN KEY (roll_number) REFERENCES students(roll_number) ON DELETE CASCADE)");
        }
    }
}
