/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exceptions;

/**
 *
 * @author felix
 */
public class PersistenciaException extends Exception {
    
    public PersistenciaException(){
        
    }
    
    public PersistenciaException(String message) {
        
    }
    
    public PersistenciaException(Throwable t) {
        super(t);
    }
    
    public PersistenciaException(String message, Throwable t) {
        super(message, t);
    }
}
