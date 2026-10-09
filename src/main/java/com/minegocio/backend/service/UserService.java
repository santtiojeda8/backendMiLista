package com.minegocio.backend.service;

import com.minegocio.backend.dto.userdto.UserRequest;
import com.minegocio.backend.dto.userdto.UserResponse;
import com.minegocio.backend.entity.User;
import com.minegocio.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;


@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    // Metodo para obtener todos los usuarios
    public List<UserResponse> getAllUsers() {
        return userRepository.findByActiveTrue()
                .stream()
                .map(user -> new UserResponse(user.getName(), user.getLastname(), user.getEmail()))
                .toList();
    }

    // Metodo para buscar un usuario por su ID
    public UserResponse getUserByIdDTO (UUID id) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("No se encontro el usuario con ID: " + id));
        return new UserResponse(existingUser.getName(), existingUser.getLastname(),existingUser.getEmail());
    }

    //Este lo usamos internamente para validar usuarios.
    public User findUserById (UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("No se encontro el usuario con ID: " + id));
    }

    public UserResponse updateUser(UserRequest user, UUID id) {
        // 1. Validar que exista el usuario en la BD (traer el registro existente)
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("El usuario con ID " + id + " no existe."));

        // 2. Validar qué datos traen valores nuevos para no guardar null
        if (user.getName() != null) existingUser.setName(user.getName());

        if(user.getLastName() != null) existingUser.setLastname(user.getLastName() );

        // Validación de email
        if (user.getEmail() != null && !user.getEmail().equalsIgnoreCase(existingUser.getEmail())) {
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new RuntimeException("El email '" + user.getEmail() + "' ya está ocupado por otro usuario.");
            }
            existingUser.setEmail(user.getEmail());
        }

        if(user.getPassword() != null) existingUser.setPassword(user.getPassword());

        // 3. Hacer save al usuario existente actualizado
        userRepository.save(existingUser);

        return new UserResponse(existingUser.getName(), existingUser.getLastname(),existingUser.getEmail());
    }

    public UserResponse softDeleteUser(UUID id) {

        User existingEntity = userRepository.findById(id).orElseThrow(() -> new RuntimeException("El usuario con ID " + id + " no existe") );

        existingEntity.setActive(false);

        userRepository.save(existingEntity);

        return new UserResponse(existingEntity.getName(), existingEntity.getLastname(),existingEntity.getEmail());
    }

    public UserResponse activateUser (UUID id){

        User existingEntity = userRepository.findById(id).orElseThrow(() -> new RuntimeException("El usuario con ID" + id + " no existe") );

        existingEntity.setActive(true);

        userRepository.save(existingEntity);

        return new UserResponse(existingEntity.getName(), existingEntity.getLastname(),existingEntity.getEmail());
    }
}
