package com.catchvbackend.api.FaceData.repository;

import com.catchvbackend.api.FaceData.service.FaceDataServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RequestRepository {
    private final EntityManager em;
    private FaceDataServiceDto serviceDto;
    public void send(List<MultipartFile> files, String userEmail, LocalDateTime startDate, String raw_len){
        String url = "http://localhost:5001/image/api";
        HttpStatus httpStatus = HttpStatus.CREATED;
        ResponseEntity<HttpStatus> errorHttpStatus = serviceDto
                .sendServiceProcedure(files, url, userEmail, startDate, raw_len);
        if (errorHttpStatus != null) return;
        new ResponseEntity<>(httpStatus);
    }

    public void upload(Long id, byte[] list, String name, Long size, String userEmail, LocalDateTime startDate){
        String sql = "insert into facedata(id, image, name, size, uploader,startDate) values(?, ?, ?, ?, ?,?)";
        try {
            em.createNativeQuery(sql)
                    .setParameter(1, 0)
                    .setParameter(2, list)
                    .setParameter(3, name)
                    .setParameter(4, size)
                    .setParameter(5, userEmail)
                    .setParameter(6, startDate)
                    .executeUpdate();
        } catch (Exception e){
            //런타임에러이므로, 느슨하게 처리하는게 옳다고 판단했습니다.
            System.out.println("업로드 실패:"+e);
        }
    }
//    public void upload(byte[] list, String name, Long size, String userEmail, String startDate){
//        String sql = "insert into facedata(id, image, name, size, uploader,startDate) values(?, ?, ?, ?, ?,?)";
//        log.info(startDate);
//        try {
//            faceDataTemplate.update(
//                    sql,
//                    0, list, name, size, userEmail, startDate);
//            log.info("업로드 성공");
//        } catch (Exception e){
//            //런타임에러이므로, 느슨하게 처리하는게 옳다고 판단했습니다.
//            log.info("업로드 실패:"+e);
//        }
//    }

}
