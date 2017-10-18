package com.ionkin.beans;

import com.fasterxml.jackson.annotation.JsonView;
import com.ionkin.Views;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by mikhail on 23.09.17.
 */
public class Student implements Comparable<Student> {

    private Integer id;
    private String firstName;
    private String lastName;

    public Student() {
    }

    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(Integer id, String firstName, String lastName) {
        this(firstName, lastName);
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonView(Views.Public.class)
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public int compareTo(Student secondStudent) {
        return this.getFullName().compareTo(secondStudent.getFullName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        return id.equals(student.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "Student [id=%d, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }
}
