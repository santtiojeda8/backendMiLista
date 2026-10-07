package com.minegocio.backend.config;

import com.minegocio.backend.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // Inyectamos las dos herramientas necesarias para que el guardián haga su trabajo
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    // Constructor para que Spring Boot inyecte las dependencias de forma segura
    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // ETAPA 1: REVISAR LA CABECERA HTTP EN BUSCA DEL PASE (TOKEN)
        // Extraemos el contenido de la cabecera "Authorization" enviada por el cliente (ej: Postman/Frontend)
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String email;

        // Control de entrada: Evaluamos si el cliente NO envió cabecera OR si no empieza con el formato "Bearer "
        if(authHeader == null || !authHeader.startsWith("Bearer ")){

            // Al no haber token que analizar (petición anónima/pública), le cedemos el control al siguiente filtro
            filterChain.doFilter(request,response);

            // Cortamos la ejecución de este filtro de inmediato para evitar que intente procesar las líneas de abajo
            return;
        }

        /*
         * AISLAR EL JWT PURO
         * Si el código llegó aquí, superó el "if", lo que significa que la petición SÍ trae un token Bearer.
         * Usamos substring(7) para saltarnos los primeros 7 caracteres ("Bearer ") y guardar solo la cadena del JWT.
         * */
        jwt = authHeader.substring(7);

        // ETAPA 2: EXTRAER LA IDENTIDAD Y REVISAR EL CONTEXTO ACTUAL DEL EDIFICIO
        // Le pedimos al JwtService que descifre el token y extraiga el "subject" (el email del dueño)
        email = jwtService.extraerEmail(jwt);

        // Validamos una doble condición:
        // 1. Que hayamos logrado extraer un email válido.
        // 2. Que el usuario NO esté previamente autenticado en el contexto de seguridad actual de Spring.
        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){

            // Invocamos a nuestro Bean userDetailsService para ir a buscar al usuario real a la Base de Datos.
            // Si el repositorio no lo encuentra, aquí se disparará una excepción interrumpiendo la petición.
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

            // VALIDAR LA AUTENTICIDAD DEL PASE DIGITAL
            // Llamamos al método matemático de JwtService para verificar: ¿El email coincide? Y ¿El token NO ha expirado?
            if(jwtService.esValidoElToken(jwt, userDetails.getUsername())){

                // ETAPA 3: CREAR LA CREDENCIAL OFICIAL Y DAR EL ACCESO
                // Al ser el token 100% válido, fabricamos el "sello de aprobación" (UsernamePasswordAuthenticationToken)
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,                  // 1. El usuario completo extraído de la DB
                        null,                         // 2. Credenciales físicas en null (no usamos contraseña, el JWT ya es la prueba)
                        userDetails.getAuthorities()  // 3. Cargamos la lista de roles/permisos del usuario
                );

                // Le inyectamos al sello detalles técnicos del origen de la petición (IP, navegador, etc.)
                authToken.setDetails(new org.springframework.security.web.authentication.WebAuthenticationDetailsSource().buildDetails(request));

                // ¡PASAPORTE APROBADO! Guardamos la credencial en el casillero de memoria central de Spring para esta petición.
                // A partir de esta línea, cualquier Controller sabrá exactamente quién es el usuario y lo dejará operar.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // CIERRE DEL FILTRO: Una vez que el guardián terminó de inspeccionar y aplicar permisos (o no),
        // deja que la petición siga su curso normal hacia el controlador final.
        filterChain.doFilter(request, response);
    }
}
