package com.electroshop.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Column(nullable = false, length = 255)
    private String password;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false, length = 50)
    private String nombre;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(length = 20)
    private String telefono;

    @Column(length = 20)
    private String dni;

    @NotBlank(message = "El rol es obligatorio")
    @Column(nullable = false, length = 20)
    private String rol;

    @OneToMany(mappedBy = "usuario")
    private List<Pedido> pedidos;

    @OneToOne(mappedBy = "usuario")
    private Carrito carrito;

    @OneToMany(mappedBy = "usuario")
    private List<Direccion> direcciones;


    // Constructor vacío
    public Usuario() {
    }


    // Constructor con todos los atributos
    public Usuario(Long id, String email, String password,
                   String nombre, String apellidos,
                   String telefono, String dni, String rol,
                   List<Pedido> pedidos, Carrito carrito,
                   List<Direccion> direcciones) {

        this.id = id;
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.dni = dni;
        this.rol = rol;
        this.pedidos = pedidos;
        this.carrito = carrito;
        this.direcciones = direcciones;
    }


    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }


    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }


    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }


    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }


    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }


    public List<Direccion> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(List<Direccion> direcciones) {
        this.direcciones = direcciones;
    }
}