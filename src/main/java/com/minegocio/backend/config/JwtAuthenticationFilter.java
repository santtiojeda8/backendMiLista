package com.minegocio.backend.config;

import com.minegocio.backend.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Extraemos la cabecera de la petición
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String email;

        //Validamos si no hay token o, si no tiene el formato Bearer
        if(authHeader == null || !authHeader.startsWith("Bearer")){

            //En caso de que no ocurra ninguna de las 2, hacemos que pase al siguiente filtro
            filterChain.doFilter(request,response);

            //Cortamos el metodo aquí para que no se siga ejecutando el código
            return;
        }

        /*
        * Si paso el "if", significa que si trae un token válido en formato Bearer
        * Ahora debemos recortar la palabra "Bearer" (que tiene 7 caracteres incluyendo el espacio) para que quede únicamente las letras del JWT.
        * */
        jwt = authHeader.substring(7);

        //Aqui extraemos el email del token usando el metodo que creamos en el JwtService
        email = jwtService.extraerEmail(jwt);

        //Aquí validamos si existe/trae un email en el token y si el usuario aún no se ha autenticado anteriormente.
        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){

            //Buscamos si el usuario es real en la DB usando el servicio obligatorio
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

            //Aca validamos si el token no expiró y si pertenece al usuario que esta haciendo la petición, usando el metodo creado en el JwtService.
            if(jwtService.esValidoElToken(jwt, userDetails.getUsername())){

            }

        }


        // Al final del metodo (fuera de los ifs), dejamos que continúe la petición hacia los controladores
        filterChain.doFilter(request, response);
    }
}
