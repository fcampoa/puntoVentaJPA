/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.repositories;

import com.POJOS.Proveedor;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author felix
 */
public class ProveedoresRepository extends RepositoryBase<Proveedor> {
    
    public ProveedoresRepository(EntityManager entityManager) {
        super(entityManager);
    }
    
    public List<Proveedor> buscarNombre(String nombre) {
        TypedQuery<Proveedor> query = entityManager.createNamedQuery("proveedoresBuscarNombre", Proveedor.class);
        query.setParameter("nombre", "%" + nombre + "%");
        return query.getResultList();
    }
    
    public List<Proveedor> buscarRFC(String rfc) {
        TypedQuery<Proveedor> query = entityManager.createNamedQuery("proveedoresBuscarRFC", Proveedor.class);
        query.setParameter("rfc", "%" + rfc + "%");
        return query.getResultList();
    }
}
