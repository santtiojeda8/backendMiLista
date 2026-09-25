package com.minegocio.backend.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_supplier", nullable = false, updatable = false)
    private UUID idSupplier;

    @Column(name = "name", length = 55, nullable = false)
    private String name;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "id_user", nullable = false)
    private User user;

    public Supplier() {
    }

    public UUID getIdSupplier() {
        return idSupplier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
