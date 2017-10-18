package com.ionkin.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by mikhail on 10/4/17.
 */
public abstract class Dao<T> {

    private static final Logger logger = LoggerFactory.getLogger(Dao.class);

    protected DataSource dataSource;
    protected JdbcTemplate jdbcTemplateObject;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        logger.info("set data source");
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    protected abstract String getTableName();

    protected abstract RowMapper<T> getRowMapper();

    public abstract int create(T obj) throws SQLException;

    public T get(Integer id) {
        String SQL = String.format("SELECT * FROM %s WHERE id=?", getTableName());
        logger.debug("{}. id = {}", SQL, id);
        if (id == null) {
            return null;
        }
        return jdbcTemplateObject.queryForObject(SQL, getRowMapper(), new Object[]{id});
    }

    public List<T> getAll() {
        String SQL = String.format("SELECT * FROM %s", getTableName());
        logger.debug(SQL);
        return jdbcTemplateObject.query(SQL, getRowMapper());
    }

    public void delete(Integer id) {
        String SQL = String.format("DELETE FROM %s WHERE id=?", getTableName());
        logger.debug("{}. id = {}", SQL, id);
        jdbcTemplateObject.update(SQL, id);
    }
}