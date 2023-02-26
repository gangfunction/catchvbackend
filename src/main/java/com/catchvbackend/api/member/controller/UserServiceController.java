package com.catchvbackend.api.member.controller;

import com.catchvbackend.api.member.repository.User;
import com.catchvbackend.api.member.repository.dao.UserDaoImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/user")
public class UserServiceController {
    /**
     * 컨트롤러 클래스에서 메인 로직을 결정하고 httpstatus에 대한 판단은 각 서비스 로직에서 실행한다.
     */
    private final UserDaoImpl userDao;

    @Autowired
    public UserServiceController(UserDaoImpl userDao) {
        this.userDao = userDao;
    }


    @PostMapping("/api")
    public ResponseEntity<HttpStatus> showUser(@RequestBody User user) {
        userDao.login(user.getUserEmail(), user.getUserPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/api/logout")
    public ResponseEntity<HttpStatus> logoutUser(@RequestBody User user) {
        userDao.changeStatus(user.getUserEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/api")
    public ResponseEntity<HttpStatus> registerUser(@RequestBody User user) {
        userDao.register(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/api")
    public ResponseEntity<HttpStatus> editUser(@RequestBody User user) {
        userDao.edit(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/api")
    public ResponseEntity<HttpStatus> outUser(@RequestBody User user) {
        userDao.delete(user.getUserEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
