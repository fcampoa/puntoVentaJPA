/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pruebas;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author felix
 */
public class prueba1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        EntityManager em = Persistence.createEntityManagerFactory("puntoVentaPU").createEntityManager();
    }
    
}
