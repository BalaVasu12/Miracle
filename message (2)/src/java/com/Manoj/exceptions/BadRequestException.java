/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Manoj.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class BadRequestException extends BaseException {
    
    public BadRequestException() {
        super();
        responseStatus = 400;
    }
    
    public BadRequestException(String message) {
        super(message);
        responseStatus = 400;
    }
    
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
        responseStatus = 400;
    }
}
