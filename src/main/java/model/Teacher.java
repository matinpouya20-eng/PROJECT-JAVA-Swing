package model;

public class Teacher extends Person {
    private static final long serialVersionUID = 1L;

    private String employeeNumber;

    public Teacher(String name, String nationalId, String employeeNumber) {
        super(name, nationalId);
        this.employeeNumber = employeeNumber;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    @Override
    public String toString() {
        return getName() + " (شماره کارمندی: " + employeeNumber + ")";
    }
}
