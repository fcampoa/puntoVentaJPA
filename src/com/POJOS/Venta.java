/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.POJOS;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author felix
 */
@Entity
@Table(name = "ventas")
public class Venta implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idventa")
    private Integer idVenta;
    
    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar fecha;
    
    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;
    
    @Column(name = "descuento", nullable = false)
    private float descuento;
    
    @Column(name = "montofinal", nullable = false)
    private  float montoFinal;
    
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    List<ProductosVenta> listaProductos;
    

    public Venta() {
    }

    public Venta(Integer idVenta, Calendar fecha, Cliente cliente, float descuento, float montoFinal) {
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.cliente = cliente;
        this.descuento = descuento;
        this.montoFinal = montoFinal;
    }

    public Venta(Calendar fecha, float descuento, float montoFinal) {
        this.fecha = fecha;
        this.descuento = descuento;
        this.montoFinal = montoFinal;
    }

    public Venta(Integer idVenta, Calendar fecha, float descuento, float montoFinal) {
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.descuento = descuento;
        this.montoFinal = montoFinal;
    }

    public Venta(Integer idVenta) {
        this.idVenta = idVenta;
    }    

    public Integer getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public float getMontoFinal() {
        return montoFinal;
    }

    public void setMontoFinal(float montoFinal) {
        this.montoFinal = montoFinal;
    }

    public List<ProductosVenta> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<ProductosVenta> listaProductos) {
        this.listaProductos = listaProductos;
    }    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.idVenta);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Venta other = (Venta) obj;
        if (!Objects.equals(this.idVenta, other.idVenta)) {
            return false;
        }
        return true;
    }
    
    
    
    
}
