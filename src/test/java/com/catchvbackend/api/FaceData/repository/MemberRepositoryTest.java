package com.catchvbackend.api.FaceData.repository;

import com.catchvbackend.api.FaceData.domain.LoginStatus;
import com.catchvbackend.api.FaceData.domain.Member;
import com.catchvbackend.api.FaceData.domain.Result;
import com.catchvbackend.entity.BaseEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class MemberRepositoryTest extends BaseEntity{

    @Autowired
    private TestEntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testFindByUserEmailAndLoginStatus() {
        // given
        Member member = new Member();
        member.setUserEmail("test@example.com");
        member.setUserPassword("12341234");
        member.setLoginStatus(LoginStatus.LOGIN);
        em.persist(member);
        em.flush();

        // when
        List<Member> foundMembers = memberRepository.findMembersByUserEmailAndLoginStatus("test@example.com", LoginStatus.LOGIN);

        // then
        assertThat(foundMembers).isNotEmpty();
        assertThat(foundMembers.get(0).getUserEmail()).isEqualTo("test@example.com");
    }

    @Test
    public void testFindMemberByEmail() {
        // given
        Member member = new Member();
        member.setUserEmail("test@example.com");
        member.setUserPassword("12341234");
        member.setLoginStatus(LoginStatus.LOGIN);
        em.persist(member);
        em.flush();

        // when
        Member foundMember = memberRepository.findMemberByUserEmail("test@example.com");

        // then
        assertThat(foundMember).isNotNull();
        assertThat(foundMember.getUserEmail()).isEqualTo("test@example.com");
    }

    @Test
    public void testFindByEmail() {
        // given
        Result result = new Result();
        result.setUserEmail("test@example.com");
        result.setVideoCount(30);
        em.persist(result);
        em.flush();

        // when
        List<Result> foundResults = memberRepository.findByEmail("test@example.com");

        // then
        assertThat(foundResults).isNotEmpty();
        assertThat(foundResults.get(0).getUserEmail()).isEqualTo("test@example.com");
    }

    @Test
    void findMembersByUserEmailAndLoginStatus() {

    }

    @Test
    void findMemberByUserEmail() {
    }

    @Test
    void findByEmail() {
    }

    @Test
    void deleteMemberByUserEmail() {
    }

    @Test
    void changeStatus() {
    }

    @Test
    void edit() {
    }

    @Test
    void findLoginStatusByEmail() {
    }

    //    private Predicate userEmailEq(String userEmailCond){
//        return userEmailCond != null ? member.userEmail.eq(userEmailCond) : null;
//    }
}