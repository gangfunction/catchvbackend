package com.catchvbackend.usermanage.UserRepository.dao;

import com.catchvbackend.usermanage.UserRepository.UserMember.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Slf4j
@Component
public class UserDaoJDBC implements UserDao {
    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(JdbcTemplate jdbcTemplate) {
        UserDaoJDBC.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User register(User user) {
        Optional<User> user1 = findByEmail(user.getUserEmail());
        String msg = "중복된 이메일 입니다.";
        if (user1.isEmpty()) {
            msg = "회원가입 성공";
            log.info(msg);
            String sql = "insert into User(id,userEmail,userPassword,loginstatus) values(?,?,?,?)";
            jdbcTemplate.update(
                    sql,
                    0, user.getUserEmail(), user.getUserPassword(), 0);
        } else {
            log.info(msg);
        }
        return null;
    }

    @Override
    public void login(String userEmail, String userPassword) {
        Optional<User> user = findByEmail(userEmail);
        String msg = "아이디 확인";
        //id확인
        if (Objects.requireNonNull(user).isPresent()) {
            if (user.get().getUserPassword().equals(userPassword)) {
                msg = "로그인 성공";
                int test = changeStatus(user.get().getUserEmail());
                log.info(msg+test);
                return;
            } else {
                msg="비밀번호 틀림";
                log.info(msg);
                return;
            }
        }
        log.info(msg);
    }

    @Override
    public int changeStatus(String userEmail){
        Optional<User> user = findByEmail(userEmail);
        log.info(user.get()+"");
        String sql = "update user set loginstatus=? where id = ?";

        if(user.get().getLoginstatus()==0) {
            jdbcTemplate.update(
                    sql,
                    1, user.get().getId());
            return 1;
        } else {
            jdbcTemplate.update(
                    sql,
                    0, user.get().getId());
            return 0;
        }
    }

    public Optional<User> findById(long id) {
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
    public void delete(String email) {
        Optional<User> user = findByEmail(email);
        String sql = "delete from User where id=?";
        int result = jdbcTemplate.update(sql, user.get().getId());
        log.info(result+"개 행 삭제 성공");
    }

    @Override
    public void edit(User user){
        Optional<User> user1 = findByEmail(user.getUserEmail());
        String sql = "update user set userpassword=? where id=?";
        jdbcTemplate.update(
                sql,
                user.getUserPassword(), user1.get().getId());
    }


    private static RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUserEmail(rs.getString("useremail"));
            user.setUserPassword(rs.getString("userpassword"));
            user.setLoginstatus(rs.getInt("loginstatus"));
            return user;
        };
    }

    private final RowMapper<User> userMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUserEmail(rs.getString("userEmail"));
        user.setUserPassword(rs.getString("userPassword"));
        return user;
    };
    public void loginProcess(String userEmail, String userPassword){
        String sql = "select * from User where useremail=?";

    }
}
