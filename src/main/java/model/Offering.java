package model;

import java.io.Serializable;

public class Offering implements Serializable {
    private static final long serialVersionUID = 1L;

    private String offeringCode;
    private Course course;
    private Teacher teacher;
    private String semester;

    public Offering(String offeringCode, Course course, Teacher teacher, String semester) {
        this.offeringCode = offeringCode;
        this.course = course;
        this.teacher = teacher;
        this.semester = semester;
    }

    public String getOfferingCode() { return offeringCode; }
    public Course getCourse() { return course; }
    public Teacher getTeacher() { return teacher; }
    public String getSemester() { return semester; }

    @Override
    public String toString() {
        return course.getCourseName() + " | استاد: " + teacher.getName()
                + " | ترم: " + semester + " | کد ارائه: " + offeringCode;
    }
}
