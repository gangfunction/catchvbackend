package com.catchvbackend.api.FaceData.controller;

import com.catchvbackend.api.FaceData.domain.Result;
import com.catchvbackend.api.FaceData.service.FaceDataServiceDto;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.catchvbackend.api.FaceData.service.CustomRestTemplate.REST_TEMPLATE;


@Slf4j
@RestController
@RequestMapping(value = "/image")
public class FaceDataController {
    /**
     * 문제는 Repository를 직접 의존성 주입했었다는 것.
     * 컨트롤러 패키지에 Dto를 추가했었던 첫 개선점은 별 문제가 되지않지만, controller단에서
     * 로직에 관한 처리를 하는것이 바람직하지 않다고 판단했다.
     */
    private FaceDataServiceDto serviceDto;
    private final ModelMapper modelMapper;

    public FaceDataController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostMapping(value = "/request")
    public List<Result> requestImage(@RequestBody String userEmail) throws JSONException {
        return serviceDto.checkResult(userEmail);
    }

    @PostMapping(value = "/result")
    public void resultJson(@RequestBody String requestEmail) {
        serviceDto.resultJsonProcessing(requestEmail);
    }

    @PostMapping(value = "/api")
    public void uploadImage(@RequestBody FaceDataServiceDto serviceDto) {
        FaceDataServiceDto mappedDto = modelMapper.map(serviceDto, FaceDataServiceDto.class);
        mappedDto.uploadEvaluationLogic();
    }

    @GetMapping(value = "/responseCsv")
    public void responseCsv() {
        REST_TEMPLATE.getForEntity("http://localhost:5001/image/toCsv", String.class);
    }

    @PostMapping(value = "/downCsv")
    public void downCsvFile(@RequestBody String files) {
        File file = new File(Arrays.toString(files.getBytes()));
    }
}