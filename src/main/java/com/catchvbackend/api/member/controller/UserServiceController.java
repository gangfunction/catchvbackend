package com.catchvbackend.api.member.controller;

import com.catchvbackend.api.member.repository.User;
import com.catchvbackend.api.member.repository.dao.UserDaoImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/user")
public class UserServiceController {
    private final UserDaoImpl userDao;

    @Autowired
    public UserServiceController(UserDaoImpl userDao, JdbcTemplate jdbcTemplate) {
        this.userDao = userDao;
    }


    @PostMapping("/api")
    public HttpStatus showUser(@RequestBody User user) {
        if(!ObjectUtils.isEmpty(userDao.findByEmail(user.getUserEmail()))){
            log.info("정상적인 접근");
            userDao.login(user.getUserEmail(), user.getUserPassword());
            return HttpStatus.ACCEPTED;
        }else{
            log.info("비정상적인 접근");
            return HttpStatus.BAD_REQUEST;
        }
    }

    @PostMapping("/api/logout")
    public void logoutUser(@RequestBody User user){
        int test = userDao.changeStatus(user.getUserEmail());
        log.info("logout."+test);
    }
    @PutMapping("/api")
    public HttpStatus registerUser(@RequestBody User user) {
        log.info("register"+user);
        if(!ObjectUtils.isEmpty(user))
        {
            userDao.register(user);
            return HttpStatus.ACCEPTED;
        }
        else{
            return HttpStatus.BAD_REQUEST;
        }
    }

    @PatchMapping("/api")
    public HttpStatus editUser(@RequestBody User user) {
        log.info("edit :"+user);
        if(!ObjectUtils.isEmpty(user))
        {
            userDao.edit(user);
            return HttpStatus.ACCEPTED;
        }
        else{
            return HttpStatus.BAD_REQUEST;
        }
    }

    @DeleteMapping("/api")
    public void outUser(@RequestBody User user) {
        log.info("delete user");
        userDao.delete(user.getUserEmail());
    }


}
