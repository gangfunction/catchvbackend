package com.catchvbackend.api.FaceData.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class RequestRepository {
    private final EntityManager em;

    public void upload(byte[] list, String name, Long size, String userEmail, LocalDateTime startDate){
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
