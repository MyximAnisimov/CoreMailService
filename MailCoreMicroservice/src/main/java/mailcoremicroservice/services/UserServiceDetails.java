package mailcoremicroservice.services;

import mailcoremicroservice.model.User;
import mailcoremicroservice.repositories.RoleRepository;
//import mailcoremicroservice.roles.Role;
import mailcoremicroservice.roles.Role;
import mailcoremicroservice.utils.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceDetails implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(username).orElseThrow(() ->
//                new UsernameNotFoundException("User not found: " + username));
//
//        int userId = user.getId();
//        // Получите роль по role_id
//        Role role = roleRepository.findById(userId); // Используйте свой метод получения роли по id
//        String roleName = role.getName(); // Добавьте метод получения имени роли
//
//        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + roleName));
//
//        return new User(user.getUsername(), user.getPassword(), authorities);
//    }

    @Override
    public UserDetails loadUserByUsername(String username){
        User user = userService.findByEmail(username);
//        int userId = user.getId();
//        // Получите роль по role_id
//        Role role = roleRepository.findById(userId);
//        String roleName = role.getRole();
//        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(roleName));
//
////        Optional<Role> roles = roleRepository.findById(user.getId()); // Добавьте логику для получения списка ролей из базы данных
////        List<GrantedAuthority> authorities = roles.stream()
////                .map(role -> new SimpleGrantedAuthority(role.getRole()))
////                .collect(Collectors.toList());

        if(user == null){
            throw new UsernameNotFoundException("This user not found");
        }
        else return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new UserPrincipal(user).getAuthorities());
    }
}
