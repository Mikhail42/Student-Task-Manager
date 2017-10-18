package com.ionkin.dao;

import com.ionkin.beans.Task;
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
public class TaskDao extends Dao<Task> {

    private static final Logger logger = LoggerFactory.getLogger(TaskDao.class);

    @Autowired
    private TaskMapper taskMapper;

    public int create(Task task) throws SQLException {
        logger.debug("Create new Task: {}", task);
        String SQL = "INSERT INTO Task VALUES (default)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            int affectedRows = statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating task failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    protected String getTableName() {
        return "Task";
    }

    @Override
    protected RowMapper<Task> getRowMapper() {
        return taskMapper;
    }
}
