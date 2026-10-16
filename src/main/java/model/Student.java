
package model;

public class Student extends Person {

    private String studentNumber;

    public Student(String name, String nationalId, String studentNumber) {
        super(name, nationalId);
        this.studentNumber = studentNumber;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    @Override
    public String toString() {
        return "Student: " + getName()
                + " | National ID: " + getNationalId()
                + " | Student Number: " + studentNumber
                + " | Gender: " + getGender();
    }
}
