package com.catchvbackend.api.FaceData.service;

import com.catchvbackend.api.service.MemberService;
import com.catchvbackend.model.Member;
import com.catchvbackend.api.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testJoin() {
        //given
        Member member = new Member();
        member.setId(1L);
        member.setUserEmail("test@example.com");
        //when
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        ResponseEntity<?> memberId = memberService.join(member);
        //then
        assertNotNull(memberId);
        assertEquals(1L, memberId);
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    void testLogin_Success() {
        //given
        Member member = new Member();
        member.setUserEmail("test@example.com");
        member.setUserPassword("password");

        //when
        when(memberRepository.findMemberByUserEmail("test@example.com")).thenReturn(member);
        doNothing().when(memberRepository).loginStatusChangeToLogout("test@example.com");

        //then
        ResponseEntity<HttpStatus> response = memberService.login("test@example.com", "password");
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }
    @Test
    void testLogin_Failure() {
        //given
        Member member = new Member();
        member.setUserEmail("test@example.com");
        member.setUserPassword("password");

        //when
        when(memberRepository.findMemberByUserEmail("test@example.com")).thenReturn(member);

        //then
        ResponseEntity<HttpStatus> response = memberService.login("test@example.com", "wrongpassword");
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
    }



}