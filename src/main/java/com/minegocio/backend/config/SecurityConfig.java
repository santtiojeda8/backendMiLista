package com.minegocio.backend.config;

import com.minegocio.backend.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserRepository userRepository;

    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*
    *Creamos este metodo/Bean que vamos a usar posteriormente en el JwtAuthenticationsFilter para que
    * al extraer un email de un token, lo busquemos en la DB y validemos que exista.
    */
    @Bean
    public UserDetailsService userDetailsService(){
        //Aqui retornamos el usuario completo si se encuentra en la DB
        return email -> userRepository.findByEmail(email)
                .orElseThrow( () -> new UsernameNotFoundException("Ususario no encontrado con el email" + email));
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    


}
