package com.catchvbackend.api.FaceData.controller;

import com.catchvbackend.api.FaceData.domain.Member;
import com.catchvbackend.api.FaceData.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class MemberController {
    /**
     * 컨트롤러 클래스에서 메인 로직을 결정하고 httpstatus에 대한 판단은 각 서비스 로직에서 실행한다.
     */
    private final MemberService memberService;


    @PostMapping("/api")
    public ResponseEntity<?> loginUser(@RequestBody Member user) {
        return memberService.login(user.getUserEmail(), user.getUserPassword());
    }

    @PostMapping("/api/logout")
    public ResponseEntity<?> logoutUser(@RequestBody Member user) {
        return memberService.userLogout(user);
    }

    @PutMapping("/api")
    public ResponseEntity<?> registerUser(@RequestBody Member user) {
        return memberService.join(user);
    }

    @PatchMapping("/api")
    public ResponseEntity<?> editUser(@RequestBody Member user) {
        return memberService.updateMemberPasswordByUserEmail(user);
    }

    @DeleteMapping("/api")
    public ResponseEntity<?> outUser(@RequestBody Member user) {
        return memberService.deleteByUserEmail(user);
    }
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



