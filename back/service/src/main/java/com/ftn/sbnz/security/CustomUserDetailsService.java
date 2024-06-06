package com.ftn.sbnz.security;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.models.AdminUser;
import com.ftn.sbnz.model.models.AbstractUser;
import com.ftn.sbnz.repository.IUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AbstractUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with this username: " + username));
        if (user instanceof AdminUser) {
            return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                    .password(user.getPassword()).roles("ADMIN").build();
        }
        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword()).roles("USER").build();
    }
}
