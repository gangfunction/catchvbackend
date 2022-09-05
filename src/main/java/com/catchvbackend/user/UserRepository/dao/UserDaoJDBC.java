package com.catchvbackend.user.UserRepository.dao;

import com.catchvbackend.user.UserRepository.UserMember.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Slf4j
@Component
public class UserDaoJDBC implements UserDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User register(User user) {
        String sql = "insert into User values(?,?,?)";
        jdbcTemplate.update(
                sql,
                user.getId(), user.getUserEmail(), user.getUserPassword());
        return null;
    }


    @Override
    public User login(Long id, String userPassword) {
        Optional<User> user = findById(id);
        String msg = "아이디 인확";
        if (!user.isEmpty()) {
            if (user.get().getUserPassword().equals(userPassword)) {
                msg = "로그인 성공";
                log.info(msg);
                return user.get();
            } else {
                msg="비밀번호 틀림";
                log.info(msg);
                return null;
            }
        }
        log.info(msg);
        return null;
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = "select * from User where id = ?";
        List<User> result = jdbcTemplate.query(
                sql,
                userRowMapper(),
                id
        );
        return result.stream().findAny();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "select * from User where useremail=?";
        List<User> result = jdbcTemplate.query(
                sql,
                userRowMapper(),
                email
        );
        return result.stream().findAny();
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from User where id=?";
        int result = jdbcTemplate.update(sql, id);
        log.info(result+"개 행 삭제 성공");
    }


    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUserEmail(rs.getString("useremail"));
            user.setUserPassword(rs.getString("userpassword"));
            return user;
        };
    }

}
/**
 * public User get(String id ){
 * return this.jdbcTemplate.queryForObject("select *from users where id =?",
 * new Object[]{id} ,
 * new RowMapper<User>(){
 * public User mapRow(ResultSet rs, int rowNum) throws SQLEXCEPTION{
 * User user = new User();
 * user.setUserEmail(rs.getString("userEmail"));
 * user.setUserEmail(rs.getString("userPassword"))
 * return user;
 * }
 * );
 * }
 * private RowMapper<User> userMapper =
 * new RowMapper<User>(){
 * public User mapRow(ResultSet rs, int rowNum) throws SQLException{
 * User user = new User();
 * user.setUserEmail(rs.getString("userEmail"));
 * user.setUserEmail(rs.getString("userPassword"))
 * return user;
 * }
 * }
 */