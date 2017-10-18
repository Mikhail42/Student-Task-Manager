package com.ionkin.dao;


import com.ionkin.beans.Student;
import com.ionkin.beans.StudentTask;
import com.ionkin.beans.StudentTaskView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by mikhail on 10/4/17.
 */
@Component
public class StudentTaskDao extends Dao<StudentTask> {

    private static final Logger logger = LoggerFactory.getLogger(StudentTaskDao.class);

    @Autowired
    private StudentTaskMapper studentTaskMapper;

    @Override
    protected String getTableName() {
        return "StudentTask";
    }

    @Override
    protected RowMapper<StudentTask> getRowMapper() {
        return studentTaskMapper;
    }

    @Override
    public int create(StudentTask studentTask) throws SQLException {
        return create(studentTask.getStudentId(), studentTask.getTaskId());
    }

    public int create(Integer studentId, Integer taskId) throws SQLException {
        logger.debug("Create new StudentTask: student={}, taskId={}", studentId, taskId);
        String SQL = "INSERT INTO StudentTask (student_id, task_id) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, studentId);
            if (taskId != null) {
                statement.setInt(2, taskId);
            }
            int affectedRows = statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating studentTask failed, no ID obtained.");
                }
            }
        }
    }

    public void addOrUpdate(StudentTask studentTask) throws SQLException {
        Optional<StudentTask> studentTaskOptional = getAll().stream()
                .filter(st -> st.getStudentId().equals(studentTask.getStudentId()))
                .findFirst();
        if (studentTaskOptional.isPresent()) {
            String SQL = String.format(
                    "UPDATE %s SET student_id=%d, task_id=%d WHERE id=%d",
                    getTableName(),
                    studentTask.getStudentId(),
                    studentTask.getTaskId(),
                    studentTaskOptional.get().getId());
            jdbcTemplateObject.update(SQL);
        } else {
            create(studentTask.getStudentId(), studentTask.getTaskId());
        }
    }

    public List<StudentTaskView> createStudentTaskViewList(List<Student> studentList) {
        List<StudentTask> studentTaskList = this.getAll();
        Map<Integer, StudentTask> studentIdTaskIdMap = new HashMap<>();
        for (StudentTask studentTask : studentTaskList) {
            studentIdTaskIdMap.put(studentTask.getStudentId(), studentTask);
        }
        return studentList.stream()
                .map(student -> {
                            if (studentIdTaskIdMap.containsKey(student.getId())) {
                                StudentTask studentTask = studentIdTaskIdMap.get(student.getId());
                                return new StudentTaskView(studentTask.getId(), student, studentTask.getTaskId());
                            } else {
                                return new StudentTaskView(null, student, null);
                            }
                        }
                ).collect(Collectors.toList());
    }
}