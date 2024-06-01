package com.catchvbackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
@RequiredArgsConstructor
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    private String userEmail;
    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    private String userPassword;
    @NotNull
    private LoginStatus loginStatus;

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    private Set<AccountRole> roles;

    @JsonIgnore
    @OneToMany(mappedBy = "member" , cascade = CascadeType.ALL)
    private final List<ImageResult> serviceImageResults = new ArrayList<>();

}
