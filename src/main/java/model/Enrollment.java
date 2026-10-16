package model;

import java.io.Serializable;

public class Enrollment implements Serializable {
    private static final long serialVersionUID = 1L;

    private Student student;
    private Offering offering;
    private double grade = -1; // مقدار منفی یعنی هنوز نمره ثبت نشده است.

    public Enrollment(Student student, Offering offering) {
        this.student = student;
        this.offering = offering;
    }

    public Student getStudent() { return student; }
    public Offering getOffering() { return offering; }
    public double getGrade() { return grade; }

    public void setGrade(double grade) {
        if (grade < 0 || grade > 20) {
            throw new IllegalArgumentException("نمره باید بین صفر و بیست باشد.");
        }
        this.grade = grade;
    }

    public boolean hasGrade() {
        return grade >= 0;
    }
}
