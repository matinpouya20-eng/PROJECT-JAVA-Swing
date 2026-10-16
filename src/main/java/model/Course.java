package model;

import java.io.Serial;
import java.io.Serializable;

public class Course implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String courseCode;
    private String courseName;
    private final int units;

    public Course(String courseCode, String courseName, int units) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.units = units;
    }

    public String getCourseCode() { return courseCode; }
    public String getCourseName() { return courseName; }
    public int getUnits() { return units; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    @Override
    public String toString() {
        return courseName + " (کد: " + courseCode + ", واحد: " + units + ")";
    }
}
