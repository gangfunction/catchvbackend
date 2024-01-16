package com.catchvbackend.api.FaceData.controller;

import com.catchvbackend.api.FaceData.repository.data.FaceDataCluster;
import com.catchvbackend.api.FaceData.repository.data.FaceDataClusterRepository;
import com.catchvbackend.api.FaceData.service.FaceDataService;
import com.catchvbackend.api.FaceData.service.FaceDataServiceDto;
import com.catchvbackend.api.FaceData.service.QueueStatus;
import com.catchvbackend.api.FaceData.service.ResultFaceData;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static com.catchvbackend.api.FaceData.service.FaceDataService.REST_TEMPLATE;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@Slf4j
@RestController
@RequestMapping(value = "/image")
public class FaceDataController {
    /**
     * 문제는 Repository를 직접 의존성 주입했었다는 것.
     * 컨트롤러 패키지에 Dto를 추가했었던 첫 개선점은 별 문제가 되지않지만, controller단에서
     * 로직에 관한 처리를 하는것이 바람직하지 않다고 판단했다.
     */
    private final FaceDataClusterRepository clusterRepository;
    private final FaceDataServiceDto serviceDto;
    private final ModelMapper modelMapper;
    public FaceDataController(FaceDataClusterRepository clusterRepository, FaceDataServiceDto dataServiceDto, ModelMapper modelMapper) {
        this.clusterRepository = clusterRepository;
        this.serviceDto = dataServiceDto;
        this.modelMapper = modelMapper;
    }
    //createImage
    @PostMapping
    public ResponseEntity createImage(@RequestBody FaceDataServiceDto serviceDto) throws IOException {
        FaceDataCluster cluster = modelMapper.map(serviceDto, FaceDataCluster.class);
        FaceDataCluster newCluster = this.clusterRepository.save(cluster);
        URI createdUri = linkTo(FaceDataController.class).slash(newCluster.getId()).toUri();
        return ResponseEntity.created(createdUri).body(cluster);
    }

    @PostMapping(value="/request")
    public List<ResultFaceData> requestImage(@RequestBody String message) throws JSONException {
        return serviceDto.checkResult(FaceDataService.requestImageEmailExtraction(message));
    }

    @PostMapping(value="/result")
    public void resultJson(@RequestBody String resultData) {
        serviceDto.resultJsonProcessing(serviceDto, resultData);
    }

    @PostMapping(value = "/api")
    public void uploadImage(@RequestBody FaceDataServiceDto serviceDto, QueueStatus status) throws IOException {
        FaceDataServiceDto.uploadEvaluationLogic(serviceDto, status);
    }

    @GetMapping(value = "/responseCsv")
    public void responseCsv(){
        REST_TEMPLATE.getForEntity("http://localhost:5001/image/toCsv", String.class);
    }

    @PostMapping(value = "/downCsv")
    public void downCsvFile(@RequestBody String files){
        File file = new File(Arrays.toString(files.getBytes()));
    }
}