package com.catchvbackend.api.mainService.repository.dao;

import com.catchvbackend.api.mainService.repository.FaceData;
import com.catchvbackend.api.mainService.repository.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class FaceDataDaoImpl implements FaceDataDao {
    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(JdbcTemplate jdbcTemplate){
        FaceDataDaoImpl.jdbcTemplate = jdbcTemplate;
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
            log.info(""+e);
        }


    }

    public List<ResultData> checkResult(String userEmail) {
        String sql = "select * from resultdata where userEmail=?";
        return jdbcTemplate.query(sql,userRowMapper(),userEmail);
    }
    private static RowMapper<ResultData> userRowMapper() {
        return (rs, rowNum) -> {
            ResultData resultData = new ResultData();
            resultData.setVideoCount(rs.getInt("videoCount"));
            resultData.setDetectCount(rs.getInt("detectCount"));
            resultData.setUserEmail(rs.getString("userEmail"));
            resultData.setUrlList(rs.getString("urlList"));
            return resultData;
        };
    }

}


