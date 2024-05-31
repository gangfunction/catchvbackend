package com.catchvbackend.api.controller;

import com.catchvbackend.model.Member;
import com.catchvbackend.api.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Tag(name = "Member", description = "회원 관련 API")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final ModelMapper modelMapper;

    private Member mapMember(Member user) {
        return modelMapper.map(user, Member.class);
    }


    @Operation(summary = "회원가입", description = "회원가입을 합니다.")
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody Member user) {
        try {
            ResponseEntity<Long> response = memberService.join(mapMember(user));
            return ResponseEntity.status(HttpStatus.CREATED).body(response.getBody());
        } catch (Exception e) {
            log.error("Error during registration", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during registration");
        }
    }

    @Operation(summary = "로그인", description = "로그인을 합니다.")
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody Member user) {
        try {
          return memberService.login(mapMember(user).getUserEmail(), mapMember(user).getUserPassword());
        } catch (Exception e) {
            log.error("Error during login", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during login");
        }
    }
    @Operation(summary = "로그아웃", description = "로그아웃을 합니다.")
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@Valid @RequestBody Member user) {
        try {
          return memberService.userLogout(mapMember(user));
        } catch (Exception e) {
            log.error("Error during logout", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during logout");
        }
    }
    @Operation(summary = "회원정보수정", description = "회원정보를 수정합니다.")
    @PatchMapping("/edit")
    public ResponseEntity<?> editUser(@Valid @RequestBody Member user) {
        try {
          return memberService.updateMemberPasswordByUserEmail(mapMember(user));
        } catch (Exception e) {
            log.error("Error during user update", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during user update");
        }
    }



    @Operation(summary = "회원탈퇴", description = "회원탈퇴를 합니다.")
    @DeleteMapping("/delete")
    public ResponseEntity<?> outUser(@Valid @RequestBody Member user) {
        try {
            memberService.deleteByUserEmail(mapMember(user));
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error during user deletion", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during user deletion");
        }
    }
}



