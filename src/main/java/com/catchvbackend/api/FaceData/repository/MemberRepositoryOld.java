package com.catchvbackend.api.FaceData.repository;

import com.catchvbackend.api.FaceData.domain.LoginStatus;
import com.catchvbackend.api.FaceData.domain.Member;
import com.catchvbackend.api.FaceData.domain.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberRepositoryOld {
    private final EntityManager em;

    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }
    public Member findOne(Long id){
        return em.find(Member.class,id);
    }
    public List<Member> findAll(){
        return em.createQuery("select m from Member m",Member.class).getResultList();
    }
    public Member findMemberByEmail(String email){
        return em.createQuery("select m from Member m where m.userEmail = :userEmail",Member.class)
                .setParameter("userEmail",email)
                .getSingleResult();
    }
    public List<Result> findByEmail(String email){
        return em.createQuery("select m from Result m where m.userEmail = :userEmail",Result.class)
                .setParameter("userEmail",email)
                .getResultList();
    }

    public Long changeStatus(String userEmail){
        Member user = findMemberByEmail(userEmail);
        user.setLoginStatus(LoginStatus.LOGIN);
        return 1L;
    }

    public Long delete(String email) {
        Member user = findMemberByEmail(email);
        em.remove(user);
        return 1L;
    }

    public Long edit(Member user){
        Member user1 = findMemberByEmail(user.getUserEmail());
        user1.setUserPassword(user.getUserPassword());
        return 1L;
    }


    private static RowMapper<Member> userRowMapper() {
        return (rs, rowNum) -> {
            Member user = new Member();
            user.setId(rs.getLong("id"));
            user.setUserEmail(rs.getString("useremail"));
            user.setUserPassword(rs.getString("userpassword"));
            return user;
        };
    }
}
