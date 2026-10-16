package model;

import java.io.Serial;
import java.io.Serializable;

public class ClassRoom implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String classCode;
    private String className;
    private String roomNumber;
    private int capacity;

    public ClassRoom(String classCode, String className, String roomNumber, int capacity) {
        this.classCode = classCode;
        this.className = className;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
    }

    public String getClassCode() { return classCode; }
    public String getClassName() { return className; }
    public String getRoomNumber() { return roomNumber; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    @Override
    public String toString() {
        return className + " (کد: " + classCode + ", اتاق: " + roomNumber + ")";
    }
}
