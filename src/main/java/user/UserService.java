package user;

import user.UserRepository.UserMember.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void getUsers(Long user){

    }
    public void getUser(Long userId){

    }
    public void registerUser(String username, String password){

    }
    public void updateUser(long userId, String username, String password){

    }
    public void deleteUser(long userId){

    }



}
