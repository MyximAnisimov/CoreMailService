package mailcoremicroservice.services;

import jakarta.transaction.Transactional;
import mailcoremicroservice.model.User;
import mailcoremicroservice.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Transactional
    public User findByEmail(String login){
        return userRepository.getByEmail(login);
    }

    @Transactional
    public User findByLoginAndPassword(String login, String password){
        return userRepository.getByEmailAndPassword(login, password);
    }
//    @Transactional
//    public String register(String login, String password){
//        User user = new User(login, password);
//        userRepository.save(user);
@Transactional
public String register(String login, String password){
    try {
        User user = new User(login, password);
        userRepository.save(user);
        return "Success";
    }catch (Exception e){
        return "Error";
    }

}

    @Transactional
    public String deleteByUser(User user){
        try {
            userRepository.delete(user);
            return "Success";
        }catch (Exception e){
            return "Error";
        }
    }
}

