package com.electroshop.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "carrito")
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "carrito")
    private List<LineaCarrito> lineasCarrito;


    // Constructor vacío
    public Carrito() {
    }


    // Constructor con todos los atributos
    public Carrito(Long id, Usuario usuario, LocalDateTime fechaCreacion,
                   List<LineaCarrito> lineasCarrito) {

        this.id = id;
        this.usuario = usuario;
        this.fechaCreacion = fechaCreacion;
        this.lineasCarrito = lineasCarrito;
    }


    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }


    public List<LineaCarrito> getLineasCarrito() {
        return lineasCarrito;
    }

    public void setLineasCarrito(List<LineaCarrito> lineasCarrito) {
        this.lineasCarrito = lineasCarrito;
    }
}
