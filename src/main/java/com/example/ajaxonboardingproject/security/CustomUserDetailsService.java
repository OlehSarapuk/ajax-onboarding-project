package com.example.ajaxonboardingproject.security;

import static org.springframework.security.core.userdetails.User.withUsername;

import com.example.ajaxonboardingproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.ajaxonboardingproject.model.User user = userService.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("Can't get user by email " + username));
        UserBuilder builder = withUsername(username);
        builder.password(user.getPassword());
        builder.roles(user.getRoles().stream()
                .map(role -> role.getName().toString())
                .toArray(String[]::new));
        return builder.build();
    }
}

