package service.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
@Slf4j
@Repository
public class MainServiceRepository {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public MainServiceRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

}
