/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Manoj.framework.utilities.messages;

import com.Manoj.framework.utilities.messages.AppMessage;

public class BooleanMessage implements AppMessage{
    
    boolean success;

    public BooleanMessage() {
        
    }

    public BooleanMessage(boolean success) {
        this.success = success;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
