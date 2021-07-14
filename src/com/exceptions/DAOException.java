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
public class DAOException extends Exception {

    public DAOException() {
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable thrwbl) {
        super(message, thrwbl);
    }

    public DAOException(Throwable thrwbl) {
        super(thrwbl);
    }
    
    
    
}
