# Offline Student Data Management System

A self-contained Java Swing desktop application for college staff. It stores data in a local SQLite database (`student-management.db`) and supports admissions, searching, editing, attendance/marks tracking, CSV import, and CSV report export.

## Run

Requirements: Java 17+ and Maven 3.9+.

```bash
mvn compile exec:java
```

The initial local account is **admin** / **admin123**. Change it in `LoginFrame` before deployment.

## CSV format

Imports expect a header followed by rows in this order:

```text
rollNumber,name,branch,semester,contact,email,dateOfBirth
PRN001,Ada Lovelace,Computer Science,1,9876543210,ada@example.edu,1815-12-10
```

Dates use ISO format (`yyyy-MM-dd`). CSV values containing commas must be quoted.
