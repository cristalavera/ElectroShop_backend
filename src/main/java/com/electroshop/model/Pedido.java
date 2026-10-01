package com.electroshop.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @NotNull(message = "El usuario es obligatorio")
    private Usuario usuario;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDateTime fecha;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    @NotNull(message = "El total es obligatorio")
    private BigDecimal total;

    @NotBlank(message = "La dirección de envío es obligatoria")
    private String direccionEnvio;

    @OneToMany(mappedBy = "pedido")
    private List<LineaPedido> lineasPedido;

    @OneToOne(mappedBy = "pedido")
    private Pago pago;


    // Constructor vacío
    public Pedido() {
    }


    // Constructor con todos los atributos
    public Pedido(Long id,
                  Usuario usuario,
                  LocalDateTime fecha,
                  String estado,
                  BigDecimal total,
                  String direccionEnvio,
                  List<LineaPedido> lineasPedido,
                  Pago pago) {

        this.id = id;
        this.usuario = usuario;
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
        this.direccionEnvio = direccionEnvio;
        this.lineasPedido = lineasPedido;
        this.pago = pago;
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


    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }


    public String getDireccionEnvio() {
        return direccionEnvio;
    }

    public void setDireccionEnvio(String direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }


    public List<LineaPedido> getLineasPedido() {
        return lineasPedido;
    }

    public void setLineasPedido(List<LineaPedido> lineasPedido) {
        this.lineasPedido = lineasPedido;
    }


    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }
}
