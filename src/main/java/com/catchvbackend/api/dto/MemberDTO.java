package com.catchvbackend.api.dto;

import com.catchvbackend.domain.AccountRole;
import com.catchvbackend.domain.ImageResult;
import com.catchvbackend.domain.LoginStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

  private Long id;

  @NotEmpty
  private String userEmail;
  @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
  private String userPassword;
  @NotNull
  private LoginStatus loginStatus;

  private Set<AccountRole> roles;

  @JsonIgnore
  private List<ImageResult> serviceImageResults;

}
