package user.UserRepository.UserMember;

import user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    public void getUsers(
            @RequestParam Long userId) {
        this.userService.getUsers(userId);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void getUsers(
            @PathVariable long userId
    ) {
        this.userService.getUsers(userId);
    }

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(
            @RequestBody User user, @PathVariable String userId) {
        this.userService.registerUser(user.getUserEmail(), user.getUserPassword());
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(
            @PathVariable long userId,
            @RequestBody User user
    ) {
        this.userService.updateUser(userId, user.getUserEmail(), user.getUserPassword());
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(
            @PathVariable long userId
    ) {
        this.userService.deleteUser(userId);
    }

}
