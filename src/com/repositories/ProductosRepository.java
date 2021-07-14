/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.repositories;

import com.POJOS.Producto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author felix
 */
public class ProductosRepository extends RepositoryBase<Producto> {
    
    public ProductosRepository(EntityManager entityManager) {
        super(entityManager);
    }
    
    public List<Producto> buscarNombre(String nombre) {
        TypedQuery<Producto> query = entityManager.createNamedQuery("productosBuscarNombre", Producto.class);
        query.setParameter("nombre", "%" + nombre + "%");
        return query.getResultList();
    }
    
}
