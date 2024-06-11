package com.ftn.sbnz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ftn.sbnz.dtos.UserRegistartionDTO;
import com.ftn.sbnz.model.models.AdminUser;
import com.ftn.sbnz.model.models.RegularUser;
import com.ftn.sbnz.model.models.AbstractUser;
import com.ftn.sbnz.repository.IRegularUserRepository;
import com.ftn.sbnz.repository.IUserRepository;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IRegularUserRepository regularUserRepository;

    @Override
    public Optional<AbstractUser> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void register(UserRegistartionDTO userToRegisterDTO) {
        if (userRepository.existsByUsername(userToRegisterDTO.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already taken");
        }
        String encodedPassword = passwordEncoder.encode(userToRegisterDTO.getPassword());

        if (userToRegisterDTO.getType().equals("regular")) {
            regularUserRepository.save(new RegularUser(userToRegisterDTO.getName(), userToRegisterDTO.getUsername(),
                    encodedPassword));
        } else {
            userRepository.save(new AdminUser(userToRegisterDTO.getName(), userToRegisterDTO.getUsername(),
                    encodedPassword));
        }

    }
}
