package com.minegocio.backend.controller;

import com.minegocio.backend.dto.userdto.UserRequest;
import com.minegocio.backend.dto.userdto.UserResponse;
import com.minegocio.backend.entity.User;
import com.minegocio.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController( UserService userService){
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<UserResponse> getUserById (@AuthenticationPrincipal User logedUser){
        return ResponseEntity.ok(userService.getUserByIdDTO(logedUser.getIdUser()));
    }

    @GetMapping("/allUsers")
    public List<UserResponse> getAllUsers(){
        return userService.getAllUsers();
    }


    @PutMapping("/")
    public ResponseEntity<UserResponse> updateUser (@Valid @RequestBody UserRequest user, @AuthenticationPrincipal User logedUser) {
        return ResponseEntity.ok(userService.updateUser(user, logedUser.getIdUser()));
    }

    @PatchMapping("/")
    public ResponseEntity<UserResponse> softDeleteUser (@AuthenticationPrincipal User logedUser){
        return ResponseEntity.ok(userService.softDeleteUser(logedUser.getIdUser()));
    }

    @PutMapping("/activate")
    public ResponseEntity<UserResponse> activateUser (@AuthenticationPrincipal User logedUser){
        return ResponseEntity.ok( userService.activateUser(logedUser.getIdUser()) );
    }
}

