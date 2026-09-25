package com.minegocio.backend.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name= "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_user", nullable = false, updatable = false)
    private UUID idUser;

    @Column(name = "name", length = 55, nullable = false)
    private String name;

    @Column(name = "lastname", length = 55, nullable = false)
    private String lastname;

    @Column(name = "email", length = 55, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 155, nullable = false)
    private String password;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    public User(){}

    public UUID getIdUser() {
        return idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
