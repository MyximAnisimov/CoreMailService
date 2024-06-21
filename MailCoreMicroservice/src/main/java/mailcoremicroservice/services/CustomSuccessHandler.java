//package mailcoremicroservice.services;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.Builder;
//import mailcoremicroservice.repositories.UserRepository;
//import mailcoremicroservice.roles.Role;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.DefaultRedirectStrategy;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//
//import java.io.IOException;
//
//@Component
//public class CustomSuccessHandler implements AuthenticationSuccessHandler {
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    UserService userService;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
//        String redirectUrl = null;
//        if(authentication.getPrincipal() instanceof DefaultOAuth2User){
//            DefaultOAuth2User userDetails = (DefaultOAuth2User) authentication.getPrincipal();
//            String username = userDetails.getAttribute("email") != null ? userDetails.getAttribute("email") : userDetails.getAttribute("login")+"@gmail.com";
//            if(userRepository.getByEmail(username) == null){
//                userService.register(userDetails.getAttribute("email") != null ? userDetails.getAttribute("email") : userDetails.getAttribute("login")+"@gmail.com", "Password", new Role("ROLE_USER"));
//
//            }
//        } redirectUrl = "/app";
//        new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
//    }
//}
