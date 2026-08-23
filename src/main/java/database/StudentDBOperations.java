package database;

import model.AcademicRecord;
import model.Student;
import java.sql.*;
import java.util.*;

public class StudentDBOperations {
    public void saveStudent(Student s) throws SQLException {
        String sql = "INSERT INTO students VALUES (?, ?, ?, ?, ?, ?, ?) ON CONFLICT(roll_number) DO UPDATE SET name=excluded.name, branch=excluded.branch, semester=excluded.semester, contact=excluded.contact, email=excluded.email, date_of_birth=excluded.date_of_birth";
        try (Connection c = DBDriver.connect(); PreparedStatement p = c.prepareStatement(sql)) { bind(p, s); p.executeUpdate(); }
    }
    public void deleteStudent(String rollNumber) throws SQLException {
        try (Connection c = DBDriver.connect(); PreparedStatement p = c.prepareStatement("DELETE FROM students WHERE roll_number=?")) { p.setString(1, rollNumber); p.executeUpdate(); }
    }
    public List<Student> findStudents(String query, String branch, Integer semester) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM students WHERE (roll_number LIKE ? OR name LIKE ?)"); List<Object> values = new ArrayList<>(); String text = "%" + query.trim() + "%"; values.add(text); values.add(text);
        if (branch != null && !branch.equals("All")) { sql.append(" AND branch=?"); values.add(branch); }
        if (semester != null) { sql.append(" AND semester=?"); values.add(semester); }
        sql.append(" ORDER BY roll_number");
        try (Connection c = DBDriver.connect(); PreparedStatement p = c.prepareStatement(sql.toString())) { for (int i=0;i<values.size();i++) p.setObject(i+1, values.get(i)); try (ResultSet r = p.executeQuery()) { List<Student> out = new ArrayList<>(); while(r.next()) out.add(student(r)); return out; } }
    }
    public Set<String> branches() throws SQLException { Set<String> result = new TreeSet<>(); try (Connection c=DBDriver.connect(); Statement s=c.createStatement(); ResultSet r=s.executeQuery("SELECT DISTINCT branch FROM students ORDER BY branch")) { while(r.next()) result.add(r.getString(1)); } return result; }
    public void saveAcademic(AcademicRecord a) throws SQLException { String sql="INSERT INTO academic_records VALUES (?, ?, ?, ?, ?, ?) ON CONFLICT(roll_number,subject) DO UPDATE SET internal_marks=excluded.internal_marks, external_marks=excluded.external_marks, attended_lectures=excluded.attended_lectures, total_lectures=excluded.total_lectures"; try(Connection c=DBDriver.connect(); PreparedStatement p=c.prepareStatement(sql)){p.setString(1,a.rollNumber());p.setString(2,a.subject());p.setDouble(3,a.internalMarks());p.setDouble(4,a.externalMarks());p.setInt(5,a.attendedLectures());p.setInt(6,a.totalLectures());p.executeUpdate();}}
    public List<AcademicRecord> academicRecords(String roll) throws SQLException { try(Connection c=DBDriver.connect(); PreparedStatement p=c.prepareStatement("SELECT * FROM academic_records WHERE roll_number=? ORDER BY subject")){p.setString(1,roll);try(ResultSet r=p.executeQuery()){List<AcademicRecord> out=new ArrayList<>();while(r.next())out.add(new AcademicRecord(r.getString(1),r.getString(2),r.getDouble(3),r.getDouble(4),r.getInt(5),r.getInt(6)));return out;}}}
    private void bind(PreparedStatement p, Student s) throws SQLException { p.setString(1,s.rollNumber());p.setString(2,s.name());p.setString(3,s.branch());p.setInt(4,s.semester());p.setString(5,s.contact());p.setString(6,s.email());p.setString(7,s.dateOfBirth()); }
    private Student student(ResultSet r) throws SQLException { return new Student(r.getString("roll_number"),r.getString("name"),r.getString("branch"),r.getInt("semester"),r.getString("contact"),r.getString("email"),r.getString("date_of_birth")); }
}
