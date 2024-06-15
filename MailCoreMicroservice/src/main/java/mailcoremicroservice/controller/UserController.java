package mailcoremicroservice.controller;

//import org.example.mailcoremicroservice.services.UserService;
import mailcoremicroservice.model.User;
import mailcoremicroservice.requests.UserDTO;
import mailcoremicroservice.services.UserService;
import mailcoremicroservice.utils.HashUtil;
import mailcoremicroservice.utils.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api/user")
public class UserController {

//@GetMapping
//    public ResponseEntity<String> sayHello(){
//    return ResponseEntity.ok("Hello");
//}
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

        @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
        private ResponseEntity<String> login(@RequestBody UserDTO req){
            String login = req.getEmail();
            String password = HashUtil.digestPassword(req.getPassword());
//            String password = req.getPassword();
            User user = userService.findByLoginAndPassword(login, password);
            if(user != null){
                authManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
                String token = jwtUtil.generateToken(login, new ArrayList<String>(){{add("USER");}});
                return ResponseEntity.ok(token);
            }else{
                return new ResponseEntity<>("Сочетания почты и пароля не существует", HttpStatus.NOT_FOUND);
            }
        }

        @PutMapping(path = "/register",produces = MediaType.APPLICATION_JSON_VALUE)
        private ResponseEntity<String> register(@RequestBody UserDTO req){
            String login = req.getEmail();
                        String password = HashUtil.digestPassword(req.getPassword());
//            String password = req.getPassword();
            System.out.println(req.getEmail());
            User user = userService.findByEmail(login);
            if(user == null){
                userService.register(login, password);
                return ResponseEntity.ok("User has been created");
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

}
