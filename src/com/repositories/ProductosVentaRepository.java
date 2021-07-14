/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.repositories;

import com.POJOS.ProductosVenta;
import javax.persistence.EntityManager;

/**
 *
 * @author felix
 */
public class ProductosVentaRepository extends RepositoryBase<ProductosVenta> {
    
    public ProductosVentaRepository(EntityManager entityManager) {
        super(entityManager);
    }
    
}
