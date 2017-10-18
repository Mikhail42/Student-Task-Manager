package com.ionkin.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by mikhail on 10/4/17.
 */
public class Task {

    private Integer id;

    public Task() {
    }

    public Task(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String toString() {
        return String.format("Task [id=%d]", id);
    }
}
