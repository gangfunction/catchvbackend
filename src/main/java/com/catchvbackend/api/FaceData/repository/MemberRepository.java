package com.catchvbackend.api.FaceData.repository;

import com.catchvbackend.api.FaceData.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public class MemberRepository extends JpaRepository<Member, Long> {
}
