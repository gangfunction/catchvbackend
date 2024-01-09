package com.catchvbackend.api.Member.repository;

import com.catchvbackend.api.Member.controller.MemberController;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(MemberController.class)
class loginTest {
    @InjectMocks
    private MemberController userServiceController;
    @Mock
    private Member user;

    private MockMvc mockMvc;

    @Test
    void loginFailureTest() throws Exception {
        //given
        MultiValueMap<String, String> userInfo = new LinkedMultiValueMap<>();
        String email = "gangfunction@gmail.com";
        String password = "!Ll6625478";

        userInfo.add("userEmail", email);
        userInfo.add("userPassword", password);

        //when
        mockMvc.perform(post("/user/api")
               .params(userInfo))
               .andExpect(MockMvcResultMatchers.status()
                       //then
                       .isOk());
    }
}