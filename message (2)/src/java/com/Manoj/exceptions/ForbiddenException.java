/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Manoj.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends BaseException {
    public ForbiddenException() {
        super();
        responseStatus = 403;
    }
    
    public ForbiddenException(String message) {
        super(message);
        responseStatus = 403;
    }
    
    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
        responseStatus = 403;
    }
}
