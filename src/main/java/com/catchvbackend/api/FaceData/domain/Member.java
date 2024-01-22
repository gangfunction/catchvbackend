package com.catchvbackend.api.FaceData.domain;

import com.catchvbackend.api.FaceData.repository.AccountRole;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    private String userEmail;
    @NotEmpty
    private String userPassword;
    @NotEmpty
    private LoginStatus loginStatus;

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    private Set<AccountRole> roles;

    @JsonIgnore
    @OneToMany(mappedBy = "member" , cascade = CascadeType.ALL)
    private List<Result> serviceResults = new ArrayList<>();

}
