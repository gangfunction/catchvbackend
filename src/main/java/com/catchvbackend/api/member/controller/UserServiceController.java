package com.catchvbackend.api.member.controller;

import com.catchvbackend.api.member.repository.User;
import com.catchvbackend.api.member.repository.dao.UserDaoImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    @ResponseBody
    public User loginUser(@RequestBody User user){
        log.info("User get: " + user.getId());
        return null;
    }
    @PostMapping
    @ResponseBody
    public User saveUser(@RequestBody User user){
        log.info("User post: " + user.getId());
        return null;
    }


    @PostMapping("/api")
    public void showUser(@RequestBody User user) {
        log.info("showUser: " + user.getUserEmail() + " " + user.getUserPassword());
        userDao.login(user.getUserEmail(), user.getUserPassword());
    }

    @PostMapping("/api/logout")
    public void logoutUser(@RequestBody User user){
        int test = userDao.changeStatus(user.getUserEmail());
        log.info("logout."+test);
    }

    @PutMapping("/api")
    public void registerUser(@RequestBody User user) {
        log.info("register"+user);
        userDao.register(user);
    }

    @PatchMapping("/api")
    public void editUser(@RequestBody User user) {
        log.info("edit :"+user);
        userDao.edit(user);
    }

    @DeleteMapping("/api")
    public void outUser(@RequestBody User user) {
        log.info("delete user");
        userDao.delete(user.getUserEmail());
    }


}
