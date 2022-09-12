package com.catchvbackend.service.SeviceRepository.dao;

import com.catchvbackend.service.SeviceRepository.Image.FaceData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Slf4j
@Component
@Repository
public class FaceDataDaoJDBC implements FaceDataDao {
    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(JdbcTemplate jdbcTemplate){
        FaceDataDaoJDBC.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void upload(FaceData faceData, String userEmail, String startDate){
        String sql = "insert into FaceData(id, image, name, size, uploader,startDate) values(?, ?, ?, ?, ?,?)";
        log.info(startDate);
        try {
            jdbcTemplate.update(
                    sql,
                    0, faceData.getImage(), faceData.getName(), faceData.getSize(), userEmail, startDate);
            log.info("업로드 성공");
        } catch (Exception e){
            log.info(""+e);
        }
        log.info("업로드 실패");
    }


    public void saveResult(int videoCount, int detectCount, String userEmail, String urlList) {
        String sql = "insert into ResultData(videoCount,detectCount,userEmail,urlList) values(?,?,?,?)";
        log.info(userEmail);
        try {
            jdbcTemplate.update(
                    sql, videoCount, detectCount, userEmail, urlList);
            log.info("결과 저장");
        }
        catch (Exception e){
            log.info(""+e);
        }


    }
}


