package com.minegocio.backend.controller;

import com.minegocio.backend.dto.AuthResponseDto;
import com.minegocio.backend.dto.LoginRequestDto;
import com.minegocio.backend.dto.userdto.UserRequest;
import com.minegocio.backend.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> loginUser (@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(authService.loginUser(loginRequestDto));
    }

    @PostMapping("/sign")
    public ResponseEntity<AuthResponseDto> signUser (@RequestBody UserRequest userRequest){
        return ResponseEntity.ok(authService.signUser(userRequest));
    }

}
