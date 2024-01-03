package com.catchvbackend.api.mainService.repository.dao;

import com.catchvbackend.api.mainService.repository.MainServiceRepositoryDto;
import com.catchvbackend.api.mainService.repository.ResultData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FaceDataDaoImplTest  {
    private static JdbcTemplate jdbcTemplate;
    byte[] bytes;
    String name;
    long size;

    public FaceDataDaoImplTest(byte[] image, String name, long size) {
        this.bytes=image;
        this.name=name;
        this.size=size;
    }


    @Autowired
    public void setDataSource(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void upload(MainServiceRepositoryDto faceData, String userEmail, String startDate){
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

    public List<ResultData> checkResult(String userEmail) {
        String sql = "select * from resultdata where userEmail=?";
        return jdbcTemplate.query(sql,userRowMapper(),userEmail);
    }
    private static RowMapper<ResultData> userRowMapper() {
        return (rs, rowNum) -> {
            ResultData resultData = new ResultData(rs.getInt("videoCount"),
                    rs.getInt("detectCount"),
                    rs.getString("userEmail"),
                    rs.getString("urlList"));
            return resultData;
        };
    }

}