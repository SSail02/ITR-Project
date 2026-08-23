package utils;

import database.StudentDBOperations.Student;
import java.util.regex.Pattern;

public final class Validator {
    private static final Pattern EMAIL = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    private static final Pattern PHONE = Pattern.compile("^\\d{10}$");
    private Validator() { }
    public static String validateStudent(Student student) {
        if (blank(student.rollNumber()) || blank(student.name()) || blank(student.branch())) return "Roll number, name, and branch are required.";
        if (student.semester() < 1 || student.semester() > 12) return "Semester must be between 1 and 12.";
        if (!PHONE.matcher(student.contact()).matches()) return "Contact number must contain exactly 10 digits.";
        if (!EMAIL.matcher(student.email()).matches()) return "Enter a valid email address.";
        if (!student.dateOfBirth().matches("\\d{4}-\\d{2}-\\d{2}")) return "Date of birth must use yyyy-MM-dd.";
        return null;
    }
    private static boolean blank(String value) { return value == null || value.isBlank(); }
}
