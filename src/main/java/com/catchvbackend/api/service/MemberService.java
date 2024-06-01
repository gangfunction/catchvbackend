package com.catchvbackend.api.service;

import com.catchvbackend.domain.LoginStatus;
import com.catchvbackend.exception.MemberAlreadyExistsException;
import com.catchvbackend.exception.MemberNotFoundException;
import com.catchvbackend.domain.Member;
import com.catchvbackend.api.repository.MemberRepository;
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
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     *
     * @param member : 회원가입 정보
     * @return : 회원가입 성공시 회원 id 반환
     */
    @Transactional
    public ResponseEntity<Long> join(Member member) {
        Optional<Member> findMember = memberRepository.findMemberByUserEmail(member.getUserEmail());
        if (findMember.isPresent()) {
            throw new MemberAlreadyExistsException("이미 존재하는 회원입니다.");
        }
        Member savedMember = memberRepository.save(member);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMember.getId());
    }

    /**
     * 로그인
     *
     * @param userEmail : 로그인 이메일
     * @param userPassword : 로그인 비밀번호
     * @return : 로그인 성공시 HttpStatus 200 반환
     */
    public ResponseEntity<HttpStatus> login(String userEmail, String userPassword) {
        Member member = memberRepository.findMemberByUserEmail(userEmail)
            .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));
        if (Objects.equals(member.getUserPassword(), userPassword)) {
            memberRepository.loginStatusChangeToLogout(member.getUserEmail());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * 회원정보 수정
     *
     * @param member : 회원정보
     * @return : 회원정보 수정 성공시 HttpStatus 200 반환
     */
    @Transactional
    public ResponseEntity<HttpStatus> updateMemberPasswordByUserEmail(Member member) {
        Member existingMember = memberRepository.findMemberByUserEmail(member.getUserEmail())
            .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));
        existingMember.setUserPassword(member.getUserPassword());
        return ResponseEntity.ok().build();
    }

    /**
     * 회원정보 삭제
     *
     * @param member : 회원정보
     * @return : 회원정보 삭제 성공시 HttpStatus 200 반환
     */
    @Transactional
    public ResponseEntity<HttpStatus> deleteByUserEmail(Member member) {
        LoginStatus status = memberRepository.findLoginStatusByUserEmail(member.getUserEmail());
        if (status == LoginStatus.LOGOUT) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        memberRepository.deleteMemberByUserEmail(member.getUserEmail());
        return ResponseEntity.ok().build();
    }

    /**
     * 로그아웃
     *
     * @param member : 회원정보
     * @return : 로그아웃 성공시 HttpStatus 200 반환
     */
    @Transactional
    public ResponseEntity<HttpStatus> userLogout(Member member) {
        LoginStatus status = memberRepository.findLoginStatusByUserEmail(member.getUserEmail());
        if (status == LoginStatus.LOGOUT) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        memberRepository.loginStatusChangeToLogout(member.getUserEmail());
        return ResponseEntity.ok().build();
    }
}