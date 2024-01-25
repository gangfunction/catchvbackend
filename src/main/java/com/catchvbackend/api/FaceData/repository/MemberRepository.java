package com.catchvbackend.api.FaceData.repository;

import com.catchvbackend.api.FaceData.domain.LoginStatus;
import com.catchvbackend.api.FaceData.domain.Member;
import com.catchvbackend.api.FaceData.domain.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    /*
    querymethod를 사용해보기 : 메소드 이름으로 쿼리를 사용하기
     */
    @Query("select m from Member m where m.userEmail = ?1 and m.loginStatus = ?2")
    List<Member> findByUserEmailAndAndLoginStatus(String userEmail, LoginStatus loginStatus);
    @Query("select m from Member m where m.userEmail = ?1")
    Member findMemberByEmail(String email);
    @Query("select m from Result m where m.userEmail = ?1")
    List<Result> findByEmail(String email);
    @Query("delete from Member m where m.userEmail = ?1")
    void deleteByUserEmail(String userEmail);

    @Query("update Member m set m.loginStatus = 'LOGOUT' where m.userEmail = ?1")
    void changeStatus(String userEmail);

    @Query("update Member m set m.userPassword = ?2 where m.userEmail = ?1")
    void edit(Member user);
}
