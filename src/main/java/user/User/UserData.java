package user.User;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserData {
    private Long id;
    @NotEmpty
    private String userEmail;
    @NotEmpty
    private String userPassword;
}
