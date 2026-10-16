
package model;

import java.io.Serializable;
import java.sql.Date;

public abstract class Person implements Serializable {

    private String name;
    private String nationalId;
    private boolean isAlived;
    private Date birthDate;
    private String gender = "Unknown";

    public Person(String name, String nationalId) {
        this.name = name;
        this.nationalId = nationalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public boolean isAlived() {
        return isAlived;
    }

    public void setAlived(boolean alived) {
        this.isAlived = alived;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (gender == null || gender.trim().isEmpty()) {
            this.gender = "Unknown";
        } else {
            this.gender = gender;
        }
    }
}
