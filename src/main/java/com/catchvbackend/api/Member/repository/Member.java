package com.catchvbackend.api.Member.repository;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String userEmail;

    private String userPassword;

    private int loginStatus;

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    private Set<AccountRole> roles;

}
