/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Manoj.exceptions;

import org.springframework.http.HttpStatus;

public class BaseException extends Exception{
    protected int responseStatus;

    public int getResponseStatus() {
        return responseStatus;
    }
    
    public BaseException() {
        super();
        responseStatus = 500;
    }
    
    public BaseException(String message) {
        super(message);
        responseStatus = 500;
    }
    
    public BaseException(String message, Throwable cause) {
        super(message, cause);
        responseStatus = 500;
    }
}
