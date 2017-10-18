package com.ionkin.beans;

import com.fasterxml.jackson.annotation.JsonView;
import com.ionkin.Views;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by mikhail on 10/4/17.
 */
public class StudentTask {

    private Integer id;
    @JsonView(Views.Public.class)
    private Integer studentId;
    @JsonView(Views.Public.class)
    private Integer taskId;

    public StudentTask() {
    }

    public StudentTask(Integer studentId, Integer taskId) {
        this.studentId = studentId;
        this.taskId = taskId;
    }

    public StudentTask(Integer id, Integer studentId, Integer taskId) {
        this(studentId, taskId);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String toString() {
        return String.format("StudentTask [id=%d, studentId=%d, taskId=%d]", id, studentId, taskId);
    }
}
