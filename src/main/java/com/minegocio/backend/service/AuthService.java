package com.minegocio.backend.service;

import com.minegocio.backend.dto.AuthResponseDto;
import com.minegocio.backend.dto.LoginRequestDto;
import com.minegocio.backend.dto.userdto.UserRequest;
import com.minegocio.backend.dto.userdto.UserResponse;
import com.minegocio.backend.entity.User;
import com.minegocio.backend.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    // Este metodo recibe el RequestDto (un JSON) que viene del front, y devuelve el ResponseDto, que sería el string que contiene el token.
    public AuthResponseDto loginUser(LoginRequestDto loginRequestDto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
        );

        String tokenGenerado = jwtService.generarToken(loginRequestDto.getEmail());

        return new AuthResponseDto(tokenGenerado);
    }

    public AuthResponseDto signUser(UserRequest userRequest){
        User s = new User();
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("El email '" + userRequest.getEmail() + "' ya está registrado.");
        }

        s.setName(userRequest.getName());
        s.setLastname(userRequest.getLastName());
        s.setEmail(userRequest.getEmail());
        s.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        userRepository.save(s);

        String tokenGenerado = jwtService.generarToken(s.getEmail());

        return new AuthResponseDto(tokenGenerado);
    }
}
