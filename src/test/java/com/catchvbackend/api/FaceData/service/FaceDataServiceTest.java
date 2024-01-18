package com.catchvbackend.api.FaceData.service;

import com.catchvbackend.api.FaceData.repository.FaceDataRepository;
import com.catchvbackend.api.FaceData.repository.FaceDataRepositoryDto;
import org.assertj.core.api.Assertions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("FaceData Service 기능 테스트")
class FaceDataServiceTest {
    @InjectMocks
    private FaceDataService service;

    @Mock
    private FaceDataRepository repository;
    @Mock
    private FaceDataRepositoryDto repositoryDto;
    @Mock
    private FaceDataServiceDto serviceDto;

    @Nested
    @DisplayName("성공하는 테스트")
    class CorrectCase{
        String dbConnectionUrl = "http://localhost:5000/api";
        List<MultipartFile> requestFileList = List.of(
                new MockMultipartFile("file", "test1.jpg", "image/jpeg", "test1".getBytes()),
                new MockMultipartFile("file", "test2.jpg", "image/jpeg", "test2".getBytes()),
                new MockMultipartFile("file", "test3.jpg", "image/jpeg", "test3".getBytes())
        );
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        JSONArray jsonArray = new JSONArray();
        ArrayList<String> urlList = new ArrayList<>(Arrays.asList("www.naver.com", "www.daum.net"));
        String message = "success";
        Iterator<?> i;
        HashMap<String, String> dict = new HashMap<>();
        JSONObject object = new JSONObject();
        byte[] image = new byte[0];
        String imageName = "test";
        long imageSize = 0;
        String userEmail = "leeegang@naver.com";
        String startDate = "2021-08-01";
        String rawLen = "0";
        FaceDataRequestModel faceDataRequestModel = new FaceDataRequestModel(requestFileList, userEmail, startDate, rawLen, dbConnectionUrl);


        @BeforeEach
        void mapSetup(){
            map.add("userEmail", userEmail);
            map.add("startDate", startDate);
            map.add("rawLen", rawLen);
            map.add("image", image);
            map.add("imageName", imageName);
            map.add("imageSize", imageSize);
        }
        @BeforeEach
        void setFaceDataRequestModel(){
            this.faceDataRequestModel=new FaceDataRequestModel(requestFileList, this.userEmail, this.startDate, this.rawLen, this.dbConnectionUrl);
        }

        //void 메서드를 테스트할 때는 verify를 사용한다.
        @Test
        @DisplayName("sendService 테스트 성공")
        void sendService() {
            //given

            //when

            //then
            assertThatThrownBy(()->{
                FaceDataService.sendService(dbConnectionUrl,map);
            }).isInstanceOf(HttpClientErrorException.class);


        }

        @Test
        @DisplayName("sendServiceProcedure 테스트 성공")
        void sendServiceProcedure() {
            //given
            FaceDataRequestModel model = this.faceDataRequestModel;
            //when
            ResponseEntity<HttpStatus> httpStatusResponseEntity = FaceDataService.sendServiceProcedure(model);
            //then
            assertThat(Objects.requireNonNull(httpStatusResponseEntity).getStatusCode()).isEqualTo(HttpStatus.OK);
        }

        @Test
        @DisplayName("requestJsonProcessing 테스트 성공")
        void resultJsonProcessing() {
        }

        @Test
        @DisplayName("requestImageEmailExtraction 테스트 성공")
        void requestImageEmailExtraction() {
        }

        @Test
        @DisplayName("addWaitingList 테스트 성공")
        void addToWaitingList() {
        }
    }




}