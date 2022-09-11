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
        String msg = "업로드 실패";
        log.info(startDate);
        try {
            jdbcTemplate.update(
                    sql,
                    0, faceData.getImage(), faceData.getName(), faceData.getSize(), userEmail, startDate);
            msg = "업로드 성공";
        } catch (Exception e){
            log.info(""+e);
        }
        log.info(msg);
    }


}


