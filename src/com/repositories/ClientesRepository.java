/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.repositories;

import com.POJOS.Cliente;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author felix
 */
public class ClientesRepository extends RepositoryBase<Cliente> {

    public ClientesRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public List<Cliente> buscarNombre(String nombre) {
        TypedQuery<Cliente> query = entityManager.createNamedQuery("clientesBuscarNombre", Cliente.class);
        query.setParameter("nombre", "%" + nombre + "%");
        return query.getResultList();
    }

    public List<Cliente> buscarRFC(String rfc) {
        TypedQuery<Cliente> query = entityManager.createNamedQuery("clientesBuscarRRC", Cliente.class);
        query.setParameter("rfc", "%" + rfc + "%");
        return query.getResultList();
    }
}
