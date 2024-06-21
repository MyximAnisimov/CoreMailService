package mailcoremicroservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import mailcoremicroservice.events.UserRegisteredEvent;
import mailcoremicroservice.model.User;
import mailcoremicroservice.repositories.RoleRepository;
import mailcoremicroservice.requests.UserDTO;
import mailcoremicroservice.roles.Role;
import mailcoremicroservice.services.GoogleDriveServiceDetails;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private RoleRepository roleRepository;

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

        @PostMapping(path="/login", produces = MediaType.APPLICATION_JSON_VALUE)
        private ResponseEntity<String> login(@Valid @RequestBody UserDTO req){
            String login = req.getEmail();
            String password = HashUtil.digestPassword(req.getPassword());
            User user = userService.findByLoginAndPassword(login, password);
            if(user != null){
                String token = jwtUtil.generateToken(login, Collections.singletonList(user.getRole().getRole()));
                return ResponseEntity.ok(token);
            }else{
                return new ResponseEntity<>("Сочетания почты и пароля не существует", HttpStatus.NOT_FOUND);
            }
        }

        @PostMapping(path="/getPhotos", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
        private ResponseEntity<String> getPhotos(@RequestPart("files") List<MultipartFile> files){
            if(files.isEmpty()){
                return new ResponseEntity<String>("Вы не выбрали фотографии!", HttpStatus.BAD_REQUEST);
            }
            for (MultipartFile file : files) {
                if (!Objects.equals(file.getContentType(), MediaType.IMAGE_PNG_VALUE) &&
                        !Objects.equals(file.getContentType(), MediaType.IMAGE_JPEG_VALUE)) {
                    System.out.println("Wrong");
                    return ResponseEntity.badRequest().body("Неверный тип файла. Допустимы только PNG и JPEG.");
                }
            }
            System.out.println("Фотографии попали на сервер");
            return ResponseEntity.ok("Файл успешно загружен.");
        }

        @PutMapping(path = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
        private ResponseEntity<String> register(@Valid @RequestBody UserDTO req) throws JsonProcessingException {
            if(req.getEmail().isEmpty() || req.getPassword().isEmpty()){
                return new ResponseEntity<String>("Введите корректные данные для регистрации!", HttpStatus.BAD_REQUEST);
            }
            String email = req.getEmail();
                        String password = HashUtil.digestPassword(req.getPassword());
            User user = userService.findByEmail(email);
            Role userRole = roleRepository.findByRole("ROLE_USER");
            if(user == null){
                userService.register(email, password, userRole);

                UserRegisteredEvent event = new UserRegisteredEvent();
                event.email = email;
                event.message = "Ваш аккаунт зарегистрирован!";

                String json = new ObjectMapper().writeValueAsString(event);
                kafkaTemplate.send("register", json);
                return ResponseEntity.ok("Ваш аккаунт был успешно зарегстрирован!");
            }else{
                return new ResponseEntity<String>("Пользователь уже существует!", HttpStatus.BAD_REQUEST);
            }
        }

//        @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//        private ResponseEntity<String> deleteLogin(@RequestBody UserDTO req){
//            String login = req.getEmail();
//                        String password = HashUtil.digestPassword(req.getPassword());
////            String password = req.getPassword();
//            User user = userService.findByLoginAndPassword(login, password);
//            if(user != null){
//                userService.deleteByUser(user);
//                return new ResponseEntity<>("Success", HttpStatus.ACCEPTED);
//            }else {
//                return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
//            }
//
//        }
//        public void setRole(String role){
//            this.role.add(role);
//        }

}
