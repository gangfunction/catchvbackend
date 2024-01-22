package com.catchvbackend.api.FaceData.repository;

import com.catchvbackend.api.FaceData.domain.Result;
import com.catchvbackend.api.FaceData.domain.face.FaceData;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ResultRepository {
    private final EntityManager em;
    public void saveResult(Result result) {
        String sql = "insert into result(videoCount,detectCount,userEmail,urlList) values(?,?,?,?)";
        try {
            em.createNativeQuery(sql)
                    .setParameter(1, result.getVideoCount())
                    .setParameter(2, result.getDetectCount())
                    .setParameter(3, result.getUserEmail())
                    .setParameter(4, result.getDetectedUrl())
                    .executeUpdate();
        }
        catch (Exception e){
            //런타임에러이므로, 느슨하게 처리하는게 옳다고 판단했습니다.
            System.out.println(""+e);
        }
    }
    public List<FaceData> findByEmail(String userEmail){
        return em.createQuery("select o from FaceData o join o.userEmail m " +
                        "where o.status :status" +
                        " and m.userEmail like :userEmail",FaceData.class)
                .setParameter("userEmail",userEmail)
                .getResultList();
    }
    public List<Order> findAllByString(ResultURLSearch resultUrl) {

        String jpql = "select o from Request o join o.member m";
        boolean isFirstCondition = true;

        //주문 상태 검색
        if (resultUrl.getRequestStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }

        //회원 이름 검색
        if (StringUtils.hasText(resultUrl.getUserEmail())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }

        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
                .setMaxResults(1000);

        if (resultUrl.getRequestStatus() != null) {
            query = query.setParameter("status", resultUrl.getRequestStatus());
        }
        if (StringUtils.hasText(resultUrl.getUserEmail())) {
            query = query.setParameter("name", resultUrl.getUserEmail());
        }

        return query.getResultList();
    }

    /**
     * JPA Criteria
     */
    public List<Result> findAllByCriteria(ResultURLSearch resultUrl) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Result> cq = cb.createQuery(Result.class);
        Root<Result> o = cq.from(Result.class);
        Join<Object, Object> m = o.join("member", JoinType.INNER);

        List<Predicate> criteria = new ArrayList<>();

        //주문 상태 검색
        if (resultUrl.getRequestStatus() != null) {
            Predicate status = cb.equal(o.get("status"), resultUrl.getRequestStatus());
            criteria.add(status);
        }
        //회원 이름 검색
        if (StringUtils.hasText(resultUrl.getUserEmail())) {
            Predicate name =
                    cb.like(m.<String>get("userEmail"), "%" + resultUrl.getUserEmail() + "%");
            criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Result> query = em.createQuery(cq).setMaxResults(1000);
        return query.getResultList();
    }
        public List<Result> checkResult(String userEmail) {
        String sql = "select * from result where userEmail=?";
        return em.createNativeQuery(sql).getResultList();
    }


}
