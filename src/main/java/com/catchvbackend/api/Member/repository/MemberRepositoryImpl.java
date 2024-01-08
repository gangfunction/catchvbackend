package com.catchvbackend.api.Member.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;


@Slf4j
@Repository
public class MemberRepositoryImpl implements MemberRepository {
    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(JdbcTemplate jdbcTemplate) {
        MemberRepositoryImpl.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void register(Member user) {
        Member userEmail = findByEmail(user.getUserEmail());
        if (ObjectUtils.isEmpty(userEmail)) {
            log.info("회원가입 성공");
            String sql = "insert into user(id,userEmail,userPassword,loginstatus) values(?,?,?,?)";
            jdbcTemplate.update(
                    sql,
                    0, user.getUserEmail(), user.getUserPassword(), 0);
        } else {
            log.info("중복된 이메일 입니다.");
        }
    }

    @Override
    public ResponseEntity<HttpStatus> login(String userEmail, String userPassword) {
        Member user = findByEmail(userEmail);
        log.info(user.getUserEmail() + " " + user.getUserPassword());
        log.info(userEmail + " " + userPassword);
        log.info("아이디 확인");
        if (Objects.equals(user.getUserPassword(), userPassword)) {
            int test = changeStatus(user.getUserEmail());
            log.info("로그인 성공"+test);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            log.info("비밀번호 틀림");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @Override
    public int changeStatus(String userEmail){
        Member user = findByEmail(userEmail);
        String sql = "update user set loginstatus=? where id = ?";
        if(user.getLoginStatus()==0) {
            jdbcTemplate.update(
                    sql,
                    1, user.getId());
            return 1;
        } else {
            jdbcTemplate.update(
                    sql,
                    0, user.getId());
            return 0;
        }
    }


    @Override
    public Member findByEmail(String email) {
        String sql = "select * from user where useremail=?";
        List<Member> result = jdbcTemplate.query(
                sql,
                userRowMapper(),
                email
        );
        log.info(result.toString());

        if(result.isEmpty()){
          return null;
        }
        return result.get(0);
    }

    @Override
    public void delete(String email) {
        Member user = findByEmail(email);
        String sql = "delete from user where id=?";
        int result = jdbcTemplate.update(sql, user.getId());
        log.info(result+"개 행 삭제 성공");
    }

    @Override
    public void edit(Member user){
        Member user1 = findByEmail(user.getUserEmail());
        String sql = "update user set userpassword=? where id=?";
        jdbcTemplate.update(
                sql,
                user.getUserPassword(), user1.getId());
    }


    private static RowMapper<Member> userRowMapper() {
        return (rs, rowNum) -> {
            Member user = new Member();
            user.setId(rs.getLong("id"));
            user.setUserEmail(rs.getString("useremail"));
            user.setUserPassword(rs.getString("userpassword"));
            user.setLoginStatus(rs.getInt("loginstatus"));
            return user;
        };
    }
}