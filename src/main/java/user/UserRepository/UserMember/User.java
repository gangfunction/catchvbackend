package user.UserRepository.UserMember;

import lombok.Data;

@Data
public class User {
    private Long id;

    private String userEmail;

    private String userPassword;
}
