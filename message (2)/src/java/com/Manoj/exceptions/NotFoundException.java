/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Manoj.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends BaseException {
    
    public NotFoundException() {
        super();
        responseStatus = 404;
    }
    
    public NotFoundException(String message) {
        super(message);
        responseStatus = 404;
    }
    
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
        responseStatus = 404;
    }
    
    
}
