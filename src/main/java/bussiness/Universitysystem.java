package bussiness;

import model.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Universitysystem implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final List<Student> students = new ArrayList<>();
    private final List<Teacher> teachers = new ArrayList<>();
    private final List<Course> Courses = new ArrayList<>();
    private final List<ClassRoom> classRooms = new ArrayList<>();
    private final List<Offering> offerings = new ArrayList<>();
    private final List<Enrollment> enrollments = new ArrayList<>();

    public void addStudent(Student student) { students.add(student); }
    public void addTeacher(Teacher teacher) { teachers.add(teacher); }
    public void addCourse(Course course) { Courses.add(course); }
    public void addClassRoom(ClassRoom classRoom) { classRooms.add(classRoom); }
    public void addOffering(Offering offering) { offerings.add(offering); }

    public Student findStudentByNumber(String studentNumber) {
        for (Student student : students) {
            if (student.getStudentNumber().equals(studentNumber)) return student;
        }
        return null;
    }

    public Course findCourseByCode(String courseCode) {
        for (Course course : Courses) {
            if (course.getCourseCode().equals(courseCode)) return course;
        }
        return null;
    }

    public Offering findOfferingByCode(String offeringCode) {
        for (Offering offering : offerings) {
            if (offering.getOfferingCode().equals(offeringCode)) return offering;
        }
        return null;
    }

    public ClassRoom findClassRoom(String classCode) {
        for (ClassRoom classRoom : classRooms) {
            if (classRoom.getClassCode().equals(classCode)) return classRoom;
        }
        return null;
    }

    public boolean enrollStudent(String studentNumber, String offeringCode) {
        Student student = findStudentByNumber(studentNumber);
        Offering offering = findOfferingByCode(offeringCode);
        if (student == null || offering == null) return false;

        for (Enrollment enrollment : enrollments) {
            boolean sameStudent = enrollment.getStudent().getStudentNumber().equals(studentNumber);
            boolean sameOffering = enrollment.getOffering().getOfferingCode().equals(offeringCode);
            if (sameStudent && sameOffering) return false;
        }

        enrollments.add(new Enrollment(student, offering));
        return true;
    }

    /** فقط برای دانشجویی که قبلاً در همان ارائه ثبت‌نام شده باشد نمره ذخیره می‌کند. */
    public boolean recordGrade(String studentNumber, String offeringCode, double grade) {
        for (Enrollment enrollment : enrollments) {
            boolean sameStudent = enrollment.getStudent().getStudentNumber().equals(studentNumber);
            boolean sameOffering = enrollment.getOffering().getOfferingCode().equals(offeringCode);
            if (sameStudent && sameOffering) {
                enrollment.setGrade(grade);
                return true;
            }
        }
        return false;
    }

    public List<Student> getStudents() { return students; }
    public List<Teacher> getTeachers() { return teachers; }
    public List<Course> getCourses() { return Courses; }
    public List<ClassRoom> getClassRooms() { return classRooms; }
    public List<Offering> getOfferings() { return offerings; }
    public List<Enrollment> getEnrollments() { return enrollments; }
}
