package com.catchvbackend.api.FaceData.controller;

import com.catchvbackend.api.FaceData.domain.LoginStatus;
import com.catchvbackend.api.FaceData.domain.Member;
import com.catchvbackend.api.FaceData.repository.MemberRepository;
import com.catchvbackend.api.FaceData.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class MemberController {
    /**
     * 컨트롤러 클래스에서 메인 로직을 결정하고 httpstatus에 대한 판단은 각 서비스 로직에서 실행한다.
     */
    private final MemberService memberService;
    private final MemberRepository userDao;


    @PostMapping("/api")
    public ResponseEntity<HttpStatus> showUser(@RequestBody Member user) {
        memberService.login(user.getUserEmail(), user.getUserPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/api/logout")
    public ResponseEntity<HttpStatus> logoutUser(@RequestBody Member user) {
        user.setLoginStatus(LoginStatus.LOGOUT);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/api")
    public ResponseEntity<HttpStatus> registerUser(@RequestBody Member user) {
        memberService.join(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/api")
    public ResponseEntity<HttpStatus> editUser(@RequestBody Member user) {
        userDao.edit(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/api")
    public ResponseEntity<HttpStatus> outUser(@RequestBody Member user) {
        userDao.delete(user.getUserEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }
//    @PostMapping("/api/create")
//    public CreateResponse create(@RequestBody @Valid Member user) {
//        return new CreateResponse(id);
//    }
//    @GetMapping("/api/create")
//    public ResponseType<Member> createv2() {
//        List<Member> findmMembers = memberService.findMembers();
//        List<MemberDto> collect = findmMembers.stream()
//                .map(m -> new MemberDto(m.getUserEmail(), m.getUserPassword(), m.getLoginStatus()))
//                .collect(Collectors.toList());
//        return new ResponseType(collect);
//    }
//    @Data
//    static class CreateResponse {
//        private Long id;
//    }
//    @Data
//    @AllArgsConstructor
//    static class ResponseType<T>{
//        private T data;
//    }
//    @Data
//    @AllArgsConstructor
//    static class MemberDto {
//        private String userEmail;
//        private String userPassword;
//        private LoginStatus;
//    }
//    //lazy initializing이 중요하다


}
