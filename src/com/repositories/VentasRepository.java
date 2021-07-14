/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.repositories;

import com.POJOS.Cliente;
import com.POJOS.Venta;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author felix
 */
public class VentasRepository extends RepositoryBase<Venta> {
    
    public VentasRepository(EntityManager entityManager) {
        super(entityManager);
    }
    
    public List<Venta> buscarRango(Calendar desde, Calendar hasta, Cliente cliente) {
        String jpql = "select v from Venta v where v.fecha >= :desde and v.fecha <= :hasta";
        if (cliente.getIdCliente() != null) {
            jpql += " and v.cliente.idCliente = :idCliente";
        }
        TypedQuery<Venta> query = entityManager.createQuery(jpql, Venta.class);
        query.setParameter("desde", desde);
        query.setParameter("hasta", hasta);
        if (cliente.getIdCliente() != null) {
            query.setParameter("idCliente", cliente.getIdCliente());
        }
        return query.getResultList();
    }
    
}
