package com.eduardo.springbootvirtualstore.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.eduardo.springbootvirtualstore.dto.EmailDTO;
import com.eduardo.springbootvirtualstore.security.JWTUtil;
import com.eduardo.springbootvirtualstore.security.UserSS;
import com.eduardo.springbootvirtualstore.services.AuthService;
import com.eduardo.springbootvirtualstore.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService service;

    @PostMapping(value = "/refresh_token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response){
        UserSS user = UserService.authenticated();
        String token = jwtUtil.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/forgot")
    public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto){
        service.sendNewPassword(objDto.getEmail());
        return ResponseEntity.noContent().build();
    }
}