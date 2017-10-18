package com.ionkin.beans;

import com.fasterxml.jackson.annotation.JsonView;
import com.ionkin.Views;

/**
 * Created by mikhail on 10/7/17.
 */
public class StudentTaskView implements Comparable<StudentTaskView> {

    private Integer id;
    @JsonView(Views.Public.class)
    private Student student;
    @JsonView(Views.Public.class)
    private Integer taskId;

    public StudentTaskView() {
    }

    public StudentTaskView(Integer id, Student student, Integer taskId) {
        this.id = id;
        this.student = student;
        this.taskId = taskId;
    }

    public StudentTask toStudentTask() {
        return new StudentTask(id, student.getId(), taskId);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    @Override
    public int compareTo(StudentTaskView o) {
        return student.compareTo(o.student);
    }
}
