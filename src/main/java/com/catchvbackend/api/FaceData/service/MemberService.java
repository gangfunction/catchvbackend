package com.catchvbackend.api.FaceData.service;


import com.catchvbackend.api.FaceData.domain.LoginStatus;
import com.catchvbackend.api.FaceData.domain.Member;
import com.catchvbackend.api.FaceData.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    /**
     * 회원가입
     *
     * @param member : 회원가입 정보
     * @return : 회원가입 성공시 회원 id 반환
     */
    public ResponseEntity<?> join(Member member) {
        Optional<Member> findMember = Optional.ofNullable(memberRepository.findMemberByUserEmail(member.getUserEmail()));
        Member optionalMember = findMember.orElseThrow(() -> new IllegalStateException("이미 존재하는 회원입니다."));
        memberRepository.save(optionalMember);
        return ResponseEntity.ok(member.getId());
    }

    /**
     * 로그인
     *
     * @param userEmail : 로그인 이메일
     * @param userPassword : 로그인 비밀번호
     * @return : 로그인 성공시 HttpStatus 200 반환
     */
    public ResponseEntity<HttpStatus> login(String userEmail, String userPassword) {
        Optional<Member> findMember = Optional.ofNullable(memberRepository.findMemberByUserEmail(userEmail));
        Member member = findMember.orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));
        if (member != null && Objects.equals(member.getUserPassword(), userPassword)) {
            memberRepository.loginStatusChangeToLogout(member.getUserEmail());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * @param member : 회원정보
     * @return : 회원정보 수정 성공시 HttpStatus 200 반환
     */
    public ResponseEntity<?> updateMemberPasswordByUserEmail(Member member) {
        Optional<Member> findMember = Optional.ofNullable(memberRepository.findMemberByUserEmail(member.getUserEmail()));
        Member optionalMember = findMember.orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));
        optionalMember.setUserPassword(member.getUserPassword());
        memberRepository.edit(optionalMember);
        return ResponseEntity.ok().build();
    }


    /**
     * @param member : 회원정보
     * @return : 회원정보 삭제 성공시 HttpStatus 200 반환
     */
    public ResponseEntity<?> deleteByUserEmail(Member member) {
        LoginStatus status = memberRepository.findLoginStatusByUserEmail(member.getUserEmail());
        if (status == LoginStatus.LOGOUT) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        memberRepository.deleteMemberByUserEmail(member);
        return ResponseEntity.ok().build();
    }

    /**
     * @param member : 회원정보
     * @return : 로그아웃 성공시 HttpStatus 200 반환
     */
    public ResponseEntity<?> userLogout(Member member) {
        LoginStatus status = memberRepository.findLoginStatusByUserEmail(member.getUserEmail());
        if (status == LoginStatus.LOGOUT) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        memberRepository.loginStatusChangeToLogout(member.getUserEmail());
        return null;
    }
}
