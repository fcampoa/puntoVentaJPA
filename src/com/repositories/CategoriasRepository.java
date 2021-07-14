/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.repositories;

import com.POJOS.Categoria;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author felix
 */
public class CategoriasRepository extends RepositoryBase<Categoria> {
    
    public CategoriasRepository(EntityManager entityManager) {
        super(entityManager);
    }
    
    public List<Categoria> buscarNombre(String nombre) {
        TypedQuery<Categoria> query = entityManager.createNamedQuery("buscarNombre", Categoria.class);
        query.setParameter("nombre", "%" + nombre + "%");
        return query.getResultList();
    }
    
}
