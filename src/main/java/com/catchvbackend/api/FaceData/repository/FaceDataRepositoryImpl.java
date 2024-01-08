package com.catchvbackend.api.FaceData.repository;

import com.catchvbackend.api.FaceData.service.FaceDataService;
import com.catchvbackend.api.FaceData.service.ResultFaceData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Repository
public class FaceDataRepositoryImpl implements FaceDataRepository {
    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(JdbcTemplate jdbcTemplate){
        FaceDataRepositoryImpl.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void upload(FaceData faceData, String userEmail, String startDate){
        String sql = "insert into facedata(id, image, name, size, uploader,startDate) values(?, ?, ?, ?, ?,?)";
        log.info(startDate);
        try {
            jdbcTemplate.update(
                    sql,
                    0, faceData.getImage(), faceData.getName(), faceData.getSize(), userEmail, startDate);
            log.info("업로드 성공");
        } catch (Exception e){
            //런타임에러이므로, 느슨하게 처리하는게 옳다고 판단했습니다.
            log.info("업로드 실패:"+e);
        }
    }


    public void saveResult(int videoCount, int detectCount, String userEmail, String urlList) {
        String sql = "insert into resultdata(videoCount,detectCount,userEmail,urlList) values(?,?,?,?)";
        log.info(userEmail);
        try {
            jdbcTemplate.update(
                    sql, videoCount, detectCount, userEmail, urlList);
            log.info("결과 저장");
        }
        catch (Exception e){
            //런타임에러이므로, 느슨하게 처리하는게 옳다고 판단했습니다.
            log.info(""+e);
        }


    }

    public List<ResultFaceData> checkResult(String userEmail) {
        String sql = "select * from resultdata where userEmail=?";
        return jdbcTemplate.query(sql,userRowMapper(),userEmail);
    }
    private static RowMapper<ResultFaceData> userRowMapper() {
        return (rs, rowNum) -> {
            ResultFaceData resultFaceData = new ResultFaceData(rs.getInt("videoCount"),
                    rs.getInt("detectCount"),
                    rs.getString("userEmail"),
                    rs.getString("urlList"));
            return resultFaceData;
        };
    }

    public ResponseEntity<HttpStatus> send(List<MultipartFile> files, String userEmail, String startDate, String raw_len){
        String url = "http://localhost:5001/image/api";
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        HttpStatus httpStatus = HttpStatus.CREATED;
        ResponseEntity<HttpStatus> errorHttpStatus = FaceDataService.sendServiceProcedure(files, userEmail, startDate, raw_len, url, map);
        if (errorHttpStatus != null) return errorHttpStatus;
        return new ResponseEntity<>(httpStatus);
    }
}

