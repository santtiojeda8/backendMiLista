package com.minegocio.backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    //Generamos la SecretKey o clave secreta que es la que vamos a usar para firmar el JWT
    public SecretKey generearKey (){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generarToken(String email) {
        return Jwts.builder()
                .subject(email)
                .signWith(generearKey())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration)) //Le damos una vida de 10 minutos
                .compact();
    }

    private Claims extraerClaims (String token){
        return Jwts.parser()
                .verifyWith(generearKey()) //Le pasamos la clave con la que firmamos los tokens
                .build() //Compactamos/armamos el lector, con la clave indicada.
                .parseSignedClaims(token) //Aquí recibe el token y lo verifica con el lector
                .getPayload(); //Aquí extrae los claims que viajan en el token.
    }

    public String extraerEmail(String token){
        return extraerClaims(token).getSubject();
    }

    public Boolean esValidoElToken(String token, String emailUser){
        return (extraerEmail(token).equals(emailUser) && ! extraerClaims(token).getExpiration().before(new Date()));
    }
}
