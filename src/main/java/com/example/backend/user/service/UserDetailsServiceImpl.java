package com.example.backend.user.service;

import com.example.backend.user.entity.User;
import com.example.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUserName(username);
        org.springframework.security.core.userdetails.User.UserBuilder builder = null;
        if (user.isPresent()) {
            User currentuser = user.get();
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.password(currentuser.getPassword());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
        return builder.build();
    }
}