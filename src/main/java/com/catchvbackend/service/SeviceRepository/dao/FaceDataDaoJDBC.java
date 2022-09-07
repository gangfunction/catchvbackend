package com.catchvbackend.service.SeviceRepository.dao;

import com.catchvbackend.service.SeviceRepository.Image.FaceData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FaceDataDaoJDBC implements FaceDataDao {
    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(JdbcTemplate jdbcTemplate){
        FaceDataDaoJDBC.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void upload(FaceData faceData){
        String sql = "insert into FaceData(id, image, name, size) values(?, ?, ?, ?)";
        String msg = "업로드 실패";
        try {
            jdbcTemplate.update(
                    sql,
                    0, faceData.getImage(), faceData.getName(), faceData.getSize());
            msg = "업로드 성공";
        } catch (Exception e){
            log.info(""+e);
        }
        log.info(msg);
    }


}


