package com.ionkin.beans;

import com.fasterxml.jackson.annotation.JsonView;
import com.ionkin.Views;

import java.util.List;

public class AjaxResponseBody {

    @JsonView(Views.Public.class)
    private String errorMessage;
    @JsonView(Views.Public.class)
    private String code;
    @JsonView(Views.Public.class)
    private List<StudentTaskView> studentTaskViews;
    @JsonView(Views.Public.class)
    private List<Student> students;
    @JsonView(Views.Public.class)
    private List<Task> tasks;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<StudentTaskView> getStudentTaskViews() {
        return studentTaskViews;
    }

    public void setStudentTaskViews(List<StudentTaskView> studentTaskViews) {
        this.studentTaskViews = studentTaskViews;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "AjaxResponseResult [errorMessage=" + errorMessage + ", code=" + code + ", studentTaskViews=" + studentTaskViews + "]";
    }

}
