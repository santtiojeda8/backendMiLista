package com.minegocio.backend.controller;

import com.minegocio.backend.dto.userdto.UserRequest;
import com.minegocio.backend.dto.userdto.UserResponse;
import com.minegocio.backend.service.UserService;
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

    @GetMapping("/{id}")
    public UserResponse getUserById (@PathVariable UUID id){
        return userService.getUserByIdDTO(id);
    }

    @GetMapping("/")
    public List<UserResponse> getAllUsers(){
        return userService.getAllUsers();
    }


    @PutMapping("/{id}")
    public UserResponse updateUser (@RequestBody UserRequest user, @PathVariable UUID id){
        return userService.updateUser(user, id);
    }

    @PatchMapping("/{id}")
    public UserResponse softDeleteUser (@PathVariable UUID id){
        return userService.softDeleteUser(id);
    }

    @PutMapping("/{id}/activate")
    public UserResponse activateUser (@PathVariable UUID id){
        return userService.activateUser(id);
    }
}

