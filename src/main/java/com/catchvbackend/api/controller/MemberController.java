package com.catchvbackend.api.controller;

import com.catchvbackend.api.dto.MemberDTO;
import com.catchvbackend.api.service.JsonProcessingService;
import com.catchvbackend.domain.Member;
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
    private final JsonProcessingService jsonProcessingService;
    private final ModelMapper modelMapper;

    private Member mapToEntity(MemberDTO userDto) {
        return modelMapper.map(userDto, Member.class);
    }

    @Operation(summary = "회원가입", description = "회원가입을 합니다.")
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody MemberDTO userDto) {
        Long userId = memberService.join(mapToEntity(userDto)).getBody();
        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }

    @Operation(summary = "로그인", description = "로그인을 합니다.")
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody MemberDTO userDto) {
        return memberService.login(userDto.getUserEmail(), userDto.getUserPassword());
    }

    @Operation(summary = "로그아웃", description = "로그아웃을 합니다.")
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@Valid @RequestBody MemberDTO userDto) {
        return memberService.userLogout(mapToEntity(userDto));
    }

    @Operation(summary = "회원정보수정", description = "회원정보를 수정합니다.")
    @PatchMapping("/edit")
    public ResponseEntity<?> editUser(@Valid @RequestBody MemberDTO userDto) {
        return memberService.updateMemberPasswordByUserEmail(mapToEntity(userDto));
    }

    @Operation(summary = "회원탈퇴", description = "회원탈퇴를 합니다.")
    @DeleteMapping("/delete")
    public ResponseEntity<?> outUser(@Valid @RequestBody MemberDTO userDto) {
        memberService.deleteByUserEmail(mapToEntity(userDto));
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/processJson")
    public ResponseEntity<String> processJson(@RequestBody String jsonPayload) {
        jsonProcessingService.processJsonPayload(jsonPayload);
        return ResponseEntity.ok("JSON processed successfully");
    }
}

