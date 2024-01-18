package com.catchvbackend.api.Member.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberResositoryTest {
    @Autowired
    MemberResository memberResository;
    @Test
    @Transactional
    void saveMember() {
        //given
        Member member = new Member();
        member.setUserEmail("gangfunction.oopy.io");
        //when
        Long saveId = memberResository.save(member);
        Member findMember = memberResository.findOne(saveId);

        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUserEmail()).isEqualTo(member.getUserEmail());

    }
}