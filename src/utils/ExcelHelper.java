package utils;

import database.StudentDBOperations.Student;
import java.io.*;
import java.nio.file.Path;
import java.util.*;

public final class ExcelHelper {
    private ExcelHelper() { }
    public static void exportStudents(Path path, List<Student> students) throws IOException {
        try (BufferedWriter out = java.nio.file.Files.newBufferedWriter(path)) {
            out.write("rollNumber,name,branch,semester,contact,email,dateOfBirth\n");
            for (Student s : students) out.write(row(s) + "\n");
        }
    }
    public static List<Student> importStudents(Path path) throws IOException {
        List<Student> students = new ArrayList<>();
        try (BufferedReader in = java.nio.file.Files.newBufferedReader(path)) { String line = in.readLine(); if (line == null) return students; while ((line = in.readLine()) != null && !line.isBlank()) { List<String> c = parse(line); if (c.size() != 7) throw new IOException("Expected 7 columns: " + line); students.add(new Student(c.get(0), c.get(1), c.get(2), Integer.parseInt(c.get(3)), c.get(4), c.get(5), c.get(6))); } }
        return students;
    }
    private static String row(Student s) { return String.join(",", quote(s.rollNumber()),quote(s.name()),quote(s.branch()),String.valueOf(s.semester()),quote(s.contact()),quote(s.email()),quote(s.dateOfBirth())); }
    private static String quote(String value) { return "\"" + value.replace("\"", "\"\"") + "\""; }
    private static List<String> parse(String line) { List<String> result=new ArrayList<>(); StringBuilder item=new StringBuilder(); boolean quoted=false; for(int i=0;i<line.length();i++){char c=line.charAt(i);if(c=='\"'&&quoted&&i+1<line.length()&&line.charAt(i+1)=='\"'){item.append(c);i++;}else if(c=='\"'){quoted=!quoted;}else if(c==','&&!quoted){result.add(item.toString().trim());item.setLength(0);}else item.append(c);}result.add(item.toString().trim());return result; }
}
