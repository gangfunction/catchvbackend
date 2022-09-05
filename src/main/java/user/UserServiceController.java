package user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@EnableAutoConfiguration
@RequestMapping("/user")
public class UserServiceController {

    private void defaultDBset(){
        this.jdbcTemplate.update("use users");
    }

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserServiceController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @PostMapping("/api")
    public void registerUser(@RequestBody String body){
        log.info("user email and password is"+ body);

    }


}
