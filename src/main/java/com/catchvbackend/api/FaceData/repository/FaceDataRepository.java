package com.catchvbackend.api.FaceData.repository;

import com.catchvbackend.api.FaceData.service.FaceDataService;
import com.catchvbackend.api.FaceData.service.ResultFaceData;
import com.catchvbackend.api.FaceData.service.FaceDataRequestModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
@Repository
public class FaceDataRepository {
    private final JdbcTemplate faceDataTemplate;

    public FaceDataRepository(DataSource dataSource){
        this.faceDataTemplate= new JdbcTemplate(dataSource);
    }

    public void upload(byte[] list, String name, Long size, String userEmail, String startDate){
        String sql = "insert into facedata(id, image, name, size, uploader,startDate) values(?, ?, ?, ?, ?,?)";
        log.info(startDate);
        try {
            faceDataTemplate.update(
                    sql,
                    0, list, name, size, userEmail, startDate);
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
            faceDataTemplate.update(
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
        return faceDataTemplate.query(sql,userRowMapper(),userEmail);
    }
    private static RowMapper<ResultFaceData> userRowMapper() {
        return (rs, rowNum) -> new ResultFaceData(rs.getInt("videoCount"),
                rs.getInt("detectCount"),
                rs.getString("userEmail"),
                rs.getString("urlList"));
    }

    public void send(List<MultipartFile> files, String userEmail, String startDate, String raw_len){
        String url = "http://localhost:5001/image/api";
        HttpStatus httpStatus = HttpStatus.CREATED;
        ResponseEntity<HttpStatus> errorHttpStatus = FaceDataService.sendServiceProcedure(new FaceDataRequestModel(files, userEmail, startDate, raw_len, url));
        if (errorHttpStatus != null) return;
        new ResponseEntity<>(httpStatus);
    }
}


