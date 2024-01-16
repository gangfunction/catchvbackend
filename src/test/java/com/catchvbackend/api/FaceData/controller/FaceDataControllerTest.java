package com.catchvbackend.api.FaceData.controller;

import com.catchvbackend.api.FaceData.repository.data.FaceDataCluster;
import com.catchvbackend.api.FaceData.repository.data.FaceDataClusterRepository;
import com.catchvbackend.api.FaceData.repository.data.ServiceResult;
import com.catchvbackend.api.FaceData.service.FaceDataService;
import com.catchvbackend.api.FaceData.service.FaceDataServiceDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class FaceDataControllerTest {

    @Autowired
    FaceDataService service;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    FaceDataClusterRepository repository;


    MockMvc mockMvc;
    @Autowired
    WebApplicationContext ac;



    @BeforeEach
    public void setup(){
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(ac)
//                .addFilter(new CharacterEncodingFilter("UTF-8", true))
//                .build();
    }

    @Test
    @DisplayName("빈 등록 확인작업")
    public void contextLoads(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        String[] beans = ac.getBeanDefinitionNames();
        for (String beanName : beans) {
            System.out.println(beanName);
        }
    }
    @Test
    @DisplayName("(컨트롤러)이미지요청 테스트")
    public void resquestImageTest() throws Exception {
        this.mockMvc
                .perform(post("/image/request"))
                .andExpect(status().isOk())
                .andDo(print());

    }
    @Test
    @DisplayName("결과 데이터 전송 테스트")
    public void resultJsonTest(){

    }
    @Test
    public void FaceData(){
        FaceDataCluster data = FaceDataCluster.builder()
                .serviceResult(ServiceResult.builder()
                        .videoCount(1)
                        .detectCount(1)
                        .urlList(new String[]{"kimbob"})
                        .userEmail("userEmail@naver.com")
                        .build())
                .build();
        data.setId(1);
        Mockito.when(this.repository.save(data)).thenReturn(data);
    }

    @Test
    public void creeateEvent_BAD_REQUEST() throws Exception {
        //given - facedataclusterdto to build
        FaceDataServiceDto serviceDto = FaceDataServiceDto.builder().build();
        this.mockMvc.perform(post("/image"))
                .andExpect(status().isBadRequest());
        //when

        //then
    }
}