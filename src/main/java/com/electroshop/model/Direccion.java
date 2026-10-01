package com.electroshop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "direccion")
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La dirección es obligatoria")
    @Column(nullable = false, length = 150)
    private String direccion;

    @NotBlank(message = "El código postal es obligatorio")
    @Column(name = "codigo_postal", nullable = false, length = 10)
    private String codigoPostal;

    @NotBlank(message = "La ciudad es obligatoria")
    @Column(nullable = false, length = 50)
    private String ciudad;

    @NotBlank(message = "La provincia es obligatoria")
    @Column(nullable = false, length = 50)
    private String provincia;

    @NotBlank(message = "El país es obligatorio")
    @Column(nullable = false, length = 50)
    private String pais;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;


    // Constructor vacío
    public Direccion() {
    }


    // Constructor con todos los atributos
    public Direccion(Long id, String direccion, String codigoPostal,
                     String ciudad, String provincia, String pais,
                     Usuario usuario) {

        this.id = id;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.ciudad = ciudad;
        this.provincia = provincia;
        this.pais = pais;
        this.usuario = usuario;
    }


    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }


    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }


    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }


    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
