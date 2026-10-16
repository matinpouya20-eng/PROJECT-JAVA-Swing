package bussiness;

import dao.DataStore;
import model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class OfferingCourseBL {
    private static final String DATA_FILE = "university-data-swing.ser";
    private final Universitysystem university;

    public OfferingCourseBL() {
        if (!Files.exists(Path.of(DATA_FILE))) {
            university = new Universitysystem();
            return;
        }

        try {
            university = DataStore.load(DATA_FILE);
        } catch (IOException | ClassNotFoundException exception) {
            throw new IllegalStateException("خواندن فایل اطلاعات ناموفق بود: " + exception.getMessage(), exception);
        }
    }

    public void addStudent(String name, String nationalId, String studentNumber, String gender) {
        name = required(name, "نام دانشجو");
        nationalId = required(nationalId, "کد ملی");
        studentNumber = required(studentNumber, "شماره دانشجویی");
        gender = required(gender, "جنسیت");
        if (university.findStudentByNumber(studentNumber) != null) {
            throw new IllegalArgumentException("این شماره دانشجویی قبلاً ثبت شده است.");
        }
        university.addStudent(new Student(name, nationalId, studentNumber));
    }

    public void addTeacher(String name, String nationalId, String employeeNumber) {
        name = required(name, "نام استاد");
        nationalId = required(nationalId, "کد ملی");
        employeeNumber = required(employeeNumber, "شماره کارمندی");

        for (Teacher teacher : university.getTeachers()) {
            if (teacher.getEmployeeNumber().equals(employeeNumber)) {
                throw new IllegalArgumentException("این شماره کارمندی قبلاً ثبت شده است.");
            }
        }
        university.addTeacher(new Teacher(name, nationalId, employeeNumber));
    }

    public void addCourse(String courseCode, String courseName, int units) {
        courseCode = required(courseCode, "کد درس");
        courseName = required(courseName, "نام درس");
        if (university.findCourseByCode(courseCode) != null) {
            throw new IllegalArgumentException("این کد درس قبلاً ثبت شده است.");
        }
        if (units <= 0) throw new IllegalArgumentException("تعداد واحد باید بیشتر از صفر باشد.");
        university.addCourse(new Course(courseCode, courseName, units));
    }

    public void addClassRoom(String classCode, String className, String roomNumber, int capacity) {
        classCode = required(classCode, "کد کلاس");
        className = required(className, "نام کلاس");
        roomNumber = required(roomNumber, "شماره اتاق");
        if (university.findClassRoom(classCode) != null) {
            throw new IllegalArgumentException("این کد کلاس قبلاً ثبت شده است.");
        }
        if (capacity <= 0) throw new IllegalArgumentException("ظرفیت کلاس باید بیشتر از صفر باشد.");
        university.addClassRoom(new ClassRoom(classCode, className, roomNumber, capacity));
    }

    public void createOffering(String offeringCode, String courseCode,
                               String teacherEmployeeNumber, String semester) {
        offeringCode = required(offeringCode, "کد ارائه");
        courseCode = required(courseCode, "کد درس");
        teacherEmployeeNumber = required(teacherEmployeeNumber, "شماره کارمندی استاد");
        semester = required(semester, "ترم");

        if (university.findOfferingByCode(offeringCode) != null) {
            throw new IllegalArgumentException("این کد ارائه قبلاً ثبت شده است.");
        }
        Course course = university.findCourseByCode(courseCode);
        if (course == null) throw new IllegalArgumentException("درس با این کد پیدا نشد؛ ابتدا درس را ثبت کن.");

        Teacher selectedTeacher = null;
        for (Teacher teacher : university.getTeachers()) {
            if (teacher.getEmployeeNumber().equals(teacherEmployeeNumber)) {
                selectedTeacher = teacher;
                break;
            }
        }
        if (selectedTeacher == null) {
            throw new IllegalArgumentException("استادی با این شماره کارمندی پیدا نشد.");
        }
        university.addOffering(new Offering(offeringCode, course, selectedTeacher, semester));
    }

    public void enrollStudent(String studentNumber, String offeringCode) {
        studentNumber = required(studentNumber, "شماره دانشجویی");
        offeringCode = required(offeringCode, "کد ارائه");
        if (university.findStudentByNumber(studentNumber) == null) {
            throw new IllegalArgumentException("دانشجو پیدا نشد.");
        }
        if (university.findOfferingByCode(offeringCode) == null) {
            throw new IllegalArgumentException("ارائه‌ی درس پیدا نشد.");
        }
        if (!university.enrollStudent(studentNumber, offeringCode)) {
            throw new IllegalArgumentException("این دانشجو قبلاً در این ارائه ثبت‌نام شده است.");
        }
    }

    public void recordGrade(String studentNumber, String offeringCode, double grade) {
        studentNumber = required(studentNumber, "شماره دانشجویی");
        offeringCode = required(offeringCode, "کد ارائه");
        if (grade < 0 || grade > 20) {
            throw new IllegalArgumentException("نمره باید عددی بین صفر و بیست باشد.");
        }
        if (!university.recordGrade(studentNumber, offeringCode, grade)) {
            throw new IllegalArgumentException("ثبت‌نام این دانشجو در این ارائه پیدا نشد.");
        }
    }

    public String getTranscript(String studentNumber) {
        studentNumber = required(studentNumber, "شماره دانشجویی");
        Student student = university.findStudentByNumber(studentNumber);
        if (student == null) throw new IllegalArgumentException("دانشجو با این شماره پیدا نشد.");

        StringBuilder transcript = new StringBuilder();
        transcript.append("کارنامه دانشجو\n")
                .append("نام: ").append(student.getName()).append('\n')
                .append("شماره دانشجویی: ").append(student.getStudentNumber()).append('\n')
                .append("--------------------------------\n");

        boolean found = false;
        for (Enrollment enrollment : university.getEnrollments()) {
            if (enrollment.getStudent().getStudentNumber().equals(studentNumber)) {
                found = true;
                Offering offering = enrollment.getOffering();
                transcript.append("درس: ").append(offering.getCourse().getCourseName()).append('\n')
                        .append("کد ارائه: ").append(offering.getOfferingCode()).append('\n')
                        .append("استاد: ").append(offering.getTeacher().getName()).append('\n')
                        .append("ترم: ").append(offering.getSemester()).append('\n')
                        .append("نمره: ");
                if (enrollment.hasGrade()) transcript.append(enrollment.getGrade());
                else transcript.append("ثبت نشده");
                transcript.append("\n--------------------------------\n");
            }
        }
        if (!found) transcript.append("این دانشجو هنوز در درسی ثبت‌نام نکرده است.\n");
        return transcript.toString();
    }

    public void saveData() {
        try {
            DataStore.save(university, DATA_FILE);
        } catch (IOException exception) {
            throw new IllegalStateException("ذخیره اطلاعات انجام نشد: " + exception.getMessage(), exception);
        }
    }

    private String required(String value, String label) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("فیلد «" + label + "» نباید خالی باشد.");
        }
        return value.trim();
    }
}
