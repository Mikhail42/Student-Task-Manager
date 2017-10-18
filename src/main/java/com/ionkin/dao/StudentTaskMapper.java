package com.ionkin.dao;

import com.ionkin.beans.StudentTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by mikhail on 10/4/17.
 */
@Component
public class StudentTaskMapper implements RowMapper<StudentTask> {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private TaskDao taskDao;

    @Override
    public StudentTask mapRow(ResultSet rs, int rowNum) throws SQLException {
        StudentTask studentTask = new StudentTask();
        studentTask.setId(rs.getInt("id"));

        Integer studentId = rs.getInt("student_id");
        if (rs.wasNull()) {
            studentId = null;
        }
        studentTask.setStudentId(studentId);

        Integer taskId = rs.getInt("task_id");
        if (rs.wasNull()) {
            taskId = null;
        }
        studentTask.setTaskId(taskId);

        return studentTask;
    }
}
