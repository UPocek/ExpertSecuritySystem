package com.ftn.sbnz.service;

import java.util.Optional;

import com.ftn.sbnz.dtos.UserRegistartionDTO;
import com.ftn.sbnz.model.models.AbstractUser;

public interface IUserService {
    Optional<AbstractUser> getByUsername(String username);

    void register(UserRegistartionDTO user);
}
