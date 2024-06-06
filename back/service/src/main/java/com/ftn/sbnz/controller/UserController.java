package com.ftn.sbnz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.ftn.sbnz.dtos.LoginUserDTO;
import com.ftn.sbnz.dtos.UserCredentialsDTO;
import com.ftn.sbnz.dtos.UserRegistartionDTO;
import com.ftn.sbnz.model.models.AbstractUser;
import com.ftn.sbnz.security.jwt.JwtTokenUtil;
import com.ftn.sbnz.service.IUserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

        @Autowired
        private IUserService userService;
        @Autowired
        private JwtTokenUtil jwtTokenUtil;
        @Autowired
        private AuthenticationManager authenticationManager;

        @PostMapping("/registration")
        public ResponseEntity<String> register(@RequestBody @Valid UserRegistartionDTO user) {
                System.out.println(user.getName());
                userService.register(user);
                return new ResponseEntity<>("User registered", HttpStatus.CREATED);
        }

        @PostMapping("/login")
        public LoginUserDTO login(@RequestBody @Valid UserCredentialsDTO userCredentials) {
                UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(
                                userCredentials.getUsername(),
                                userCredentials.getPassword());
                Authentication auth = authenticationManager.authenticate(authReq);

                SecurityContext sc = SecurityContextHolder.getContext();
                sc.setAuthentication(auth);
                AbstractUser user = userService.getByUsername(userCredentials.getUsername())
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

                String token = jwtTokenUtil.generateToken(user.getUsername(),
                                sc.getAuthentication().getAuthorities().toArray()[0].toString(), user.getId(),
                                user.getName());
                String refreshToken = jwtTokenUtil.generateRefreshToken(user.getUsername(),
                                sc.getAuthentication().getAuthorities().toArray()[0].toString(), user.getId(),
                                user.getName());

                LoginUserDTO loginUserDTO = new LoginUserDTO();
                loginUserDTO.setAccessToken(token);
                loginUserDTO.setRefreshToken(refreshToken);
                return loginUserDTO;
        }

        @PostMapping(value = "/refreshToken")
        public LoginUserDTO refreshToken(@Valid @RequestBody LoginUserDTO dto) {
                if (dto.getRefreshToken() == null) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Refresh token missing");
                }
                if (jwtTokenUtil.isTokenExpired(dto.getRefreshToken()))
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                                        "Your refresh token has expired, please log in again");

                String newJwt = jwtTokenUtil.generateToken(jwtTokenUtil.getUsernameFromToken(dto.getRefreshToken()),
                                jwtTokenUtil.getRoleFromToken(dto.getRefreshToken()),
                                jwtTokenUtil.getUserIdFromToken(dto.getRefreshToken()),
                                jwtTokenUtil.getNameFromToken(dto.getRefreshToken()));
                dto.setAccessToken(newJwt);
                String newRefreshToken = jwtTokenUtil.generateRefreshToken(
                                jwtTokenUtil.getUsernameFromToken(dto.getRefreshToken()),
                                jwtTokenUtil.getRoleFromToken(dto.getRefreshToken()),
                                jwtTokenUtil.getUserIdFromToken(dto.getRefreshToken()),
                                jwtTokenUtil.getNameFromToken(dto.getRefreshToken()));
                dto.setRefreshToken(newRefreshToken);
                return dto;
        }
}
