package com.ionkin.dao;

import com.ionkin.beans.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.*;

/**
 * Created by mikhail on 10/4/17.
 */
@Component
public class StudentDao extends Dao<Student> {

    private static final Logger logger = LoggerFactory.getLogger(StudentDao.class);

    @Autowired
    private StudentMapper studentRowMapper;

    @Override
    protected String getTableName() {
        return "Student";
    }

    @Override
    protected RowMapper<Student> getRowMapper() {
        return studentRowMapper;
    }

    @Override
    public int create(Student student) throws SQLException {
        logger.debug("Student: create new {}", student);
        String SQL = "INSERT INTO Student (first_name, last_name) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            int affectedRows = statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }
    }
}
