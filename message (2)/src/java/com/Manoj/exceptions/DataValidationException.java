/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Manoj.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class DataValidationException extends BadRequestException {
    
    public DataValidationException() {
        super();
        responseStatus = 400;
    }
    
    public DataValidationException(String message) {
        super(message);
        responseStatus = 400;
    }
    
    public DataValidationException(String message, Throwable cause) {
        super(message, cause);
        responseStatus = 400;
    }
}
