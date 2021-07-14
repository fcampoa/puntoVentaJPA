/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.POJOS;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author felix
 */
@Entity
@Table(name = "rel_productosventas")
public class ProductosVenta implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idrel_productosventas")
    private Integer idProductosVenta;
    
    @ManyToOne
    @JoinColumn(name = "idproducto", nullable = false)
    private Producto producto;
    
    @ManyToOne
    @JoinColumn(name = "idventa", nullable = false)
    private Venta venta;
    
    @Column(name = "precio", nullable = false)
    private float precio;
    
    @Column(name = "cantidad", nullable = false)
    private int cantidad;
    
    @Column(name = "montototal", nullable = false)
    private float montoTotal;

    public ProductosVenta() {
    }

    public ProductosVenta(Integer idProductosVenta, Producto producto, Venta venta, float precio, int cantidad, float montoTotal) {
        this.idProductosVenta = idProductosVenta;
        this.producto = producto;
        this.venta = venta;
        this.precio = precio;
        this.cantidad = cantidad;
        this.montoTotal = montoTotal;
    }

    public ProductosVenta(Producto producto, Venta venta, float precio, int cantidad, float montoTotal) {
        this.producto = producto;
        this.venta = venta;
        this.precio = precio;
        this.cantidad = cantidad;
        this.montoTotal = montoTotal;
    }

    public ProductosVenta(Integer idProductosVenta) {
        this.idProductosVenta = idProductosVenta;
    }

    public Integer getIdProductosVenta() {
        return idProductosVenta;
    }

    public void setIdProductosVenta(Integer idProductosVenta) {
        this.idProductosVenta = idProductosVenta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(float montoTotal) {
        this.montoTotal = montoTotal;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final ProductosVenta other = (ProductosVenta) obj;
        if (!Objects.equals(this.idProductosVenta, other.idProductosVenta)) {
            return false;
        }
        return true;
    }    
}
