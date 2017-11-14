/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Manoj.framework.utilities.messages;

import com.Manoj.exceptions.BaseException;
import com.Manoj.framework.utilities.messages.AppMessage;

public class ExceptionMessage implements AppMessage {
    int responseStatus;
    String message;
    String localizedMessage;
    boolean error = true;

    public ExceptionMessage() {
        
    }

    public ExceptionMessage(BaseException message) {
        useException(message);
    }

    public void useException(BaseException message) {
        responseStatus = message.getResponseStatus();
        this.message = message.getMessage();
        this.localizedMessage = message.getLocalizedMessage();
    }
    
    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLocalizedMessage() {
        return localizedMessage;
    }

    public void setLocalizedMessage(String localizedMessage) {
        this.localizedMessage = localizedMessage;
    }
    
    public boolean getError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
    
}
