/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.GUI;

import com.repositories.CategoriasRepository;
import com.repositories.ClientesRepository;
import com.repositories.ProductosRepository;
import com.repositories.ProductosVentaRepository;
import com.repositories.ProveedoresRepository;
import com.repositories.VentasRepository;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author felix
 */
public class UnitOfWork {

    private EntityManager entityManager;

    private ClientesRepository _clientesRepository;

    private VentasRepository _ventasRepository;

    private ProductosVentaRepository _productosVentaRepository;

    private ProductosRepository _productosRepository;

    private ProveedoresRepository _proveedoresRepository;

    private CategoriasRepository _categoriasRepository;

    public UnitOfWork() {

        entityManager = Persistence.createEntityManagerFactory("puntoVentaPU").createEntityManager();

    }

    private EntityManager getEntityManager() {
        return (entityManager != null) ? entityManager : (entityManager = Persistence.createEntityManagerFactory("puntoVentaPU").createEntityManager());
    }

    public ClientesRepository clientesRepository() {
        return (_clientesRepository != null) ? _clientesRepository : (_clientesRepository = new ClientesRepository(getEntityManager()));
    }

    public VentasRepository ventasRepository() {
        return (_ventasRepository != null) ? _ventasRepository : (_ventasRepository = new VentasRepository(getEntityManager()));
    }

    public ProductosVentaRepository productosVentaRepository() {
        return (_productosVentaRepository != null) ? _productosVentaRepository : (_productosVentaRepository = new ProductosVentaRepository(getEntityManager()));
    }

    public ProductosRepository productosRepository() {
        return (_productosRepository != null) ? _productosRepository : (_productosRepository = new ProductosRepository(getEntityManager()));
    }

    public ProveedoresRepository proveedoresRepository() {
        return (_proveedoresRepository != null) ? _proveedoresRepository : (_proveedoresRepository = new ProveedoresRepository(getEntityManager()));
    }

    public CategoriasRepository categoriasRepository() {
        return (_categoriasRepository != null) ? _categoriasRepository : (_categoriasRepository = new CategoriasRepository(getEntityManager()));
    }
}
