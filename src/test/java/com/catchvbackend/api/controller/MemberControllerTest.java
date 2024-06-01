package com.catchvbackend.api.controller;

import com.catchvbackend.api.dto.MemberDTO;
import com.catchvbackend.api.service.MemberService;
import com.catchvbackend.domain.Member;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MemberControllerTest {

  @InjectMocks
  private MemberController memberController;

  @Mock
  private MemberService memberService;

  @Mock
  private ModelMapper modelMapper;

  public MemberControllerTest() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testRegisterUser() {
    MemberDTO memberDTO = new MemberDTO();
    // Set memberDTO properties

    when(memberService.join(modelMapper.map(memberDTO, Member.class))).thenReturn(ResponseEntity.ok(1L));

    ResponseEntity<?> response = memberController.registerUser(memberDTO);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(1L, response.getBody());
  }

  @Test
  void testLoginUser() {
    MemberDTO memberDTO = new MemberDTO();

    when(memberService.login(memberDTO.getUserEmail(), memberDTO.getUserPassword())).thenReturn(ResponseEntity.ok().build());

    ResponseEntity<?> response = memberController.loginUser(memberDTO);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

}