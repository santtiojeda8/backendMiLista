package com.minegocio.backend.config;

import com.minegocio.backend.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
                .orElseThrow( () -> new UsernameNotFoundException("Ususario no encontrado con email" + email));
    }

    // Al marcarlo con @Bean, registramos este componente en el almacén central de Spring.
    // Servirá para que el AuthService encripte contraseñas en el registro y las compare de forma segura en el login.
    @Bean
    public PasswordEncoder passwordEncoder(){
        // Retornamos la implementación oficial del algoritmo BCrypt, que genera hashes unidireccionales seguros.
        return new BCryptPasswordEncoder();
    }

    // Este metodo toma el lienzo de configuración (HttpSecurity) y recibe automáticamente tu filtro guardián (jwtAuthenticationFilter).
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtAuthenticationFilter jwtAuthenticationFilter) {
        return httpSecurity
                // 1. DESACTIVAR PROTECCIÓN CSRF
                // Deshabilitamos Cross-Site Request Forgery porque está pensado para aplicaciones basadas en Cookies automáticas.
                // Como nuestra API utilizará tokens JWT en las cabeceras, es inmune por naturaleza a este ataque.
                .csrf(csrf -> csrf.disable())

                // 2. CONFIGURAR AUTORIZACIONES DE RUTA
                // Aquí definimos qué partes del edificio son públicas y cuáles son privadas.
                .authorizeHttpRequests(auth -> auth
                        // Indicamos que cualquier petición dirigida a URLs que empiecen con "api/auth/" (como /login o /registro)
                        // sea completamente pública y accesible para usuarios anónimos sin token.
                        .requestMatchers("api/auth/**").permitAll()

                        // Ordenamos que CUALQUIER OTRA petición a nuestra API requiera obligatoriamente
                        // que el usuario esté debidamente autenticado (es decir, que traiga un token válido).
                        .anyRequest().authenticated()
                )

                // 3. APAGAR LA MEMORIA DEL SERVIDOR (ESTRATEGIA STATELESS)
                // Le indicamos a Spring Security que no intente crear ni guardar "Sesiones HTTP" en el servidor.
                // Cada petición HTTP entrante será tratada como un evento único e independiente que debe validarse con su propio JWT.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 4. POSICIONAR AL GUARDIÁN EN LA ADUANA
                // Le ordenamos al motor de Spring que enganche tu filtro personalizado (jwtAuthenticationFilter)
                // en el pasillo de entrada, JUSTO ANTES del filtro encargado del login tradicional por defecto.
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                // FINAL: ENSAMBLAR EL MANUAL
                // El metodo .build() toma todas estas reglas en cadena, las compila y genera el objeto de seguridad definitivo.
                .build();
    }

    /*
    * El siguiente Bean lo vamos a usar para exponer la configuracion de Login dentro de nuestra API, así podemos usarlo en el AuthService
    * Este metodo recibe el AuthenticationConfiguration que se encarga de agrupar toda la infraestructura de seguridad de la app (como el PasswordEnconder y el UserDetailsService)
    */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration){
        return authenticationConfiguration.getAuthenticationManager();
    }


}