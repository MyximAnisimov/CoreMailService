package mailcoremicroservice.controller;

//import org.example.mailcoremicroservice.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mailcoremicroservice.events.UserRegisteredEvent;
import mailcoremicroservice.model.User;
import mailcoremicroservice.requests.UserDTO;
import mailcoremicroservice.services.UserService;
import mailcoremicroservice.utils.HashUtil;
import mailcoremicroservice.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api/user")
public class UserController {

//@GetMapping
//    public ResponseEntity<String> sayHello(){
//    return ResponseEntity.ok("Hello");
//}

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        kafkaTemplate.send("register", message);
    }

    private List<String> role = new ArrayList<>();
        private final AuthenticationManager authManager = new AuthenticationManager() {

            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                return null;
            }
        };
        private final JwtUtils jwtUtil;
        private final UserService userService;

        public UserController( JwtUtils jwtUtil, UserService userService) {
            this.jwtUtil = jwtUtil;
            this.userService = userService;
        }

        @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
        private ResponseEntity<String> login(@RequestBody UserDTO req){
            String login = req.getEmail();
            String password = HashUtil.digestPassword(req.getPassword());
//            String password = req.getPassword();
            User user = userService.findByLoginAndPassword(login, password);
            if(user != null){
                authManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
                String token = jwtUtil.generateToken(login, new ArrayList<String>(){{add("USER");}});
                return ResponseEntity.ok("You have entered your account");
            }else{
                return new ResponseEntity<>("Сочетания почты и пароля не существует", HttpStatus.NOT_FOUND);
            }
        }

        @PutMapping(path = "/register",produces = MediaType.APPLICATION_JSON_VALUE)
        private ResponseEntity<String> register(@RequestBody UserDTO req) throws JsonProcessingException {
            String login = req.getEmail();
                        String password = HashUtil.digestPassword(req.getPassword());
//            String password = req.getPassword();
            System.out.println(req.getEmail());
            User user = userService.findByEmail(login);
//            setRole("MODERATOR");
            if(user == null){
                userService.register(login, password);

                UserRegisteredEvent event = new UserRegisteredEvent();
                event.email = login;
                event.message = "Хуй";

                String json = new ObjectMapper().writeValueAsString(event);

                kafkaTemplate.send("register", json);
//                kafkaTemplate.send("Your account has been registered!");
//                sendMessage("Your, account has been registered!");
                return ResponseEntity.ok("User has been added successfully");
            }else{
                return new ResponseEntity<String>("User exists with the same login", HttpStatus.BAD_REQUEST);
            }
        }

        @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
        private ResponseEntity<String> deleteLogin(@RequestBody UserDTO req){
            String login = req.getEmail();
                        String password = HashUtil.digestPassword(req.getPassword());
//            String password = req.getPassword();
            User user = userService.findByLoginAndPassword(login, password);
            if(user != null){
                userService.deleteByUser(user);
                return new ResponseEntity<>("Success", HttpStatus.ACCEPTED);
            }else {
                return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
            }

        }
        public void setRole(String role){
            this.role.add(role);
        }

}
