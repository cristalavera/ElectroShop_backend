package com.electroshop.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false, unique = true)
    @NotNull(message = "El pedido es obligatorio")
    private Pedido pedido;

    @NotBlank(message = "El método de pago es obligatorio")
    private String metodo;

    @NotBlank(message = "El estado del pago es obligatorio")
    private String estado;

    @NotNull(message = "El importe es obligatorio")
    private BigDecimal importe;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDateTime fecha;


    // Constructor vacío
    public Pago() {
    }


    // Constructor con todos los atributos
    public Pago(Long id,
                Pedido pedido,
                String metodo,
                String estado,
                BigDecimal importe,
                LocalDateTime fecha) {

        this.id = id;
        this.pedido = pedido;
        this.metodo = metodo;
        this.estado = estado;
        this.importe = importe;
        this.fecha = fecha;
    }


    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }


    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }


    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}