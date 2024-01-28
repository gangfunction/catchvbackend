package com.catchvbackend.api.FaceData.controller;

import com.catchvbackend.api.FaceData.domain.Member;
import com.catchvbackend.api.FaceData.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Tag(name = "Member", description = "회원 관련 API")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class MemberController {
    /**
     * 컨트롤러 클래스에서 메인 로직을 결정하고 httpstatus에 대한 판단은 각 서비스 로직에서 실행한다.
     */
    private final MemberService memberService;
    private final ModelMapper modelMapper;

    private Member mapMember(Member user) {
        return modelMapper.map(user, Member.class);
    }


    @Operation(summary = "회원가입", description = "회원가입을 합니다.")
    @PostMapping("/api")
    public ResponseEntity<?> loginUser(@RequestBody Member user) {
        return memberService.login(mapMember(user).getUserEmail(), mapMember(user).getUserPassword());
    }
    @Operation(summary = "로그인", description = "로그인을 합니다.")
    @PostMapping("/api/logout")
    public ResponseEntity<?> logoutUser(@RequestBody Member user) {
        return memberService.userLogout(mapMember(user));
    }

    @Operation(summary = "회원가입", description = "회원가입을 합니다.")
    @PutMapping("/api")
    public ResponseEntity<?> registerUser(@RequestBody Member user) {
        return memberService.join(mapMember(user));
    }

    @Operation(summary = "회원정보수정", description = "회원정보를 수정합니다.")
    @PatchMapping("/api")
    public ResponseEntity<?> editUser(@RequestBody Member user) {
        return memberService.updateMemberPasswordByUserEmail(mapMember(user));
    }

    @Operation(summary = "회원탈퇴", description = "회원탈퇴를 합니다.")
    @DeleteMapping("/api")
    public ResponseEntity<?> outUser(@RequestBody Member user) {
        return memberService.deleteByUserEmail(mapMember(user));
    }
}
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



