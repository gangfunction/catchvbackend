package com.catchvbackend.api.FaceData.service;


import com.catchvbackend.api.FaceData.domain.Member;
import com.catchvbackend.api.FaceData.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public void validateDuplicateMember(Member member) {
        //EXCEPTION
        Member findMember = memberRepository.findMemberByEmail(member.getUserEmail());
        Optional<Member> optionalMember = Optional.ofNullable(findMember);
        if (optionalMember.isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }




    public ResponseEntity<HttpStatus> login(String userEmail, String userPassword) {
        Member user = memberRepository.findMemberByEmail(userEmail);
        if (Objects.equals(user.getUserPassword(), userPassword)) {
            memberRepository.changeStatus(user.getUserEmail());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

    }


}
