package com.example.troknite.troknite.service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.troknite.troknite.domain.Users;
import com.example.troknite.troknite.repos.UsersRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UsersRepository userRepository;

    public CustomUserDetailsService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
          Users user = userRepository.findByEmail(usernameOrEmail)
                 .orElseThrow(() ->
                         new UsernameNotFoundException("User not found with username or email: "+ usernameOrEmail));

        Set<GrantedAuthority> authorities = user
                .getRoles()
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                authorities);
    }
}