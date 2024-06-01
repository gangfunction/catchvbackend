package com.catchvbackend.api.repository;

import com.catchvbackend.domain.ImageResponse;
import com.catchvbackend.domain.LoginStatus;
import com.catchvbackend.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findMembersByUserEmailAndLoginStatus(String userEmail, LoginStatus loginStatus);

    Optional<Member> findMemberByUserEmail(String email);

    @Query("select m from ImageResponse m where m.userEmail = ?1")
    List<ImageResponse> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("delete from Member m where m.userEmail = ?1")
    void deleteMemberByUserEmail(String userEmail);

    @Modifying
    @Transactional
    @Query("update Member m set m.loginStatus = 'LOGOUT' where m.userEmail = ?1")
    void loginStatusChangeToLogout(String userEmail);

    @Modifying
    @Transactional
    @Query("update Member m set m.userPassword = ?2 where m.userEmail = ?1")
    void edit(String userEmail, String userPassword);

    LoginStatus findLoginStatusByUserEmail(String userEmail);
}