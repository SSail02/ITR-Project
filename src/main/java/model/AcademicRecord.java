package model;

public record AcademicRecord(String rollNumber, String subject, double internalMarks,
                             double externalMarks, int attendedLectures, int totalLectures) {
    public double totalMarks() { return internalMarks + externalMarks; }
    public double attendancePercentage() { return totalLectures == 0 ? 0 : attendedLectures * 100.0 / totalLectures; }
    public String grade() {
        double percentage = totalMarks();
        if (percentage >= 90) return "A+";
        if (percentage >= 80) return "A";
        if (percentage >= 70) return "B";
        if (percentage >= 60) return "C";
        if (percentage >= 50) return "D";
        return "F";
    }
}
