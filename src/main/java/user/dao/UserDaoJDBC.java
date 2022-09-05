package user.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import user.User.UserData;

import java.util.Optional;
@Component
public class UserDaoJDBC implements UserDao {
    @Autowired
    UserData userData;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserData register(String userEmail) {
        return null;
    }


    @Override
    public UserData login(String UserEmail, String userPassword) {
        return null;
    }

    @Override
    public Optional<UserData> findById(String userEmail) {
        return Optional.empty();
    }

    @Override
    public UserData delete(String userEmail) {
        return null;
    }


}
