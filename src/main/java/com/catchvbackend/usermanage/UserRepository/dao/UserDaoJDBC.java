package com.catchvbackend.usermanage.UserRepository.dao;

import com.catchvbackend.usermanage.UserRepository.UserMember.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Slf4j
@Component
@Repository
public class UserDaoJDBC implements UserDao {
    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(JdbcTemplate jdbcTemplate) {
        UserDaoJDBC.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void register(User user) {
        Optional<User> user1 = findByEmail(user.getUserEmail());
        if (user1.isEmpty()) {
            log.info("회원가입 성공");
            String sql = "insert into User(id,userEmail,userPassword,loginstatus) values(?,?,?,?)";
            jdbcTemplate.update(
                    sql,
                    0, user.getUserEmail(), user.getUserPassword(), 0);
        } else {
            log.info("중복된 이메일 입니다.");
        }
    }

    @Override
    public void login(String userEmail, String userPassword) {
        Optional<User> user = findByEmail(userEmail);
        //id확인
        if (Objects.requireNonNull(user).isPresent()) {
            if (user.get().getUserPassword().equals(userPassword)) {
                int test = changeStatus(user.get().getUserEmail());
                log.info("로그인 성공"+test);
            } else {
                log.info("비밀번호 틀림");
            }
            return;
        }
        log.info("아이디 확인");
    }

    @Override
    public int changeStatus(String userEmail){
        Optional<User> user = findByEmail(userEmail);
        log.info(Objects.requireNonNull(user.orElse(null)).getUserEmail());
        String sql = "update user set loginstatus=? where id = ?";

        if(user.orElse(null).getLoginstatus()==0) {
            jdbcTemplate.update(
                    sql,
                    1, user.orElse(null).getId());
            return 1;
        } else {
            jdbcTemplate.update(
                    sql,
                    0, user.orElse(null).getId());
            return 0;
        }
    }


    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "select * from User where useremail=?";
        List<User> result = jdbcTemplate.query(
                sql,
                userRowMapper(),
                email
        );
        log.info(result.toString());
        return result.stream().findAny();
    }

    @Override
    public void delete(String email) {
        Optional<User> user = findByEmail(email);
        String sql = "delete from User where id=?";
        int result = jdbcTemplate.update(sql, Objects.requireNonNull(user.orElse(null)).getId());
        log.info(result+"개 행 삭제 성공");
    }

    @Override
    public void edit(User user){
        Optional<User> user1 = findByEmail(user.getUserEmail());
        String sql = "update user set userpassword=? where id=?";
        jdbcTemplate.update(
                sql,
                user.getUserPassword(), Objects.requireNonNull(user1.orElse(null)).getId());
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
}
