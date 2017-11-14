/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.source.controller;

import com.Manoj.exceptions.BadRequestException;
import com.Manoj.exceptions.BaseException;
import com.Manoj.exceptions.ForbiddenException;
import com.Manoj.framework.AppController;
import com.Manoj.framework.utilities.messages.ExceptionMessage;
import com.Manoj.framework.utilities.messages.SimpleErrorMessage;
import java.io.IOException;
import org.springframework.security.access.AccessDeniedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ApplicationExceptionHandler extends AppController {
    @ExceptionHandler(BaseException.class)
    public ExceptionMessage handleBadRequestException(BaseException ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
        StackTraceElement element = ex.getStackTrace()[0];
        if(element != null)
            Logger.getLogger(element.getClassName()).error(ex.getMessage(), ex);
        else
            Logger.getLogger("Unknown class").error(ex.getMessage(), ex);
        response.setStatus(ex.getResponseStatus());
        return new ExceptionMessage(ex);
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public ExceptionMessage handleBadRequestException(AccessDeniedException ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
        StackTraceElement element = ex.getStackTrace()[0];
        if(element != null)
            Logger.getLogger(element.getClassName()).error(ex.getMessage(), ex);
        else
            Logger.getLogger("Unknown class").error(ex.getMessage(), ex);
        response.setStatus(403);
        
        ExceptionMessage exc = new ExceptionMessage();
        exc.setLocalizedMessage("Access denied to this action");
        exc.setMessage("Access denied to this action");
        exc.setResponseStatus(403);
        
        return exc;
    }
}
