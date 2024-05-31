package com.catchvbackend.api.repository;

import com.catchvbackend.domain.LoginStatus;
import com.catchvbackend.model.Member;
import com.catchvbackend.domain.ImageResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findMembersByUserEmailAndLoginStatus(String userEmail, LoginStatus loginStatus);
    Member findMemberByUserEmail(String email);

    @Query("select m from ImageResult m where m.userEmail = ?1")
    List<ImageResult> findByEmail(String email);
    @Query("delete from Member m where m.userEmail = ?1")
    void deleteMemberByUserEmail(Member member);
    @Query("update Member m set m.loginStatus = 'LOGOUT' where m.userEmail = ?1")
    void loginStatusChangeToLogout(String userEmail);

    @Query("update Member m set m.userPassword = ?2 where m.userEmail = ?1")
    void edit(Member user);

    LoginStatus findLoginStatusByUserEmail(String userEmail);
}
