package mailcoremicroservice.services;

import mailcoremicroservice.model.User;
import mailcoremicroservice.utils.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceDetails implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username){
        User user = userService.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("This user not found");
        }
        else return new UserPrincipal(user);
    }
}
