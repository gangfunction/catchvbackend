package com.catchvbackend.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import com.catchvbackend.user.UserRepository.UserMember.User;
import com.catchvbackend.user.UserRepository.UserMember.UserRepository;


@Slf4j
@RestController
@RequestMapping("/user")
public class UserServiceController {
    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

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

    @Autowired
    public UserServiceController(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }
    @GetMapping("/api")
    public void getUser(@RequestParam String userEmail){
        log.info("User: " + userEmail);
    }
    @PostMapping("/api")
    public void showUser(@RequestParam String userEmail, @RequestParam String userPassword ){
        log.info("showUser: " + userEmail + " " + userPassword);
        System.out.println("UserServiceController.showUser");
    }

// npm run dev


}
