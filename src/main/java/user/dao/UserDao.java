package user.dao;

import user.User.UserData;

import java.util.Optional;

public interface UserDao {
    UserData register(String userEmail);
    UserData login(String UserEmail , String userPassword);
    Optional<UserData> findById(String userEmail);

    UserData delete(String userEmail);


}
